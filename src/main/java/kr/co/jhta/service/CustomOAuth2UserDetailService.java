package kr.co.jhta.service;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import kr.co.jhta.dto.UsersDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomOAuth2UserDetailService extends DefaultOAuth2UserService{
															//소셜로그인
	@Autowired
	UserService userService;
	
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		log.info(">>>>>>>>>>>>>>>>>>>> userRequest : " + userRequest);
		
		String clientName = userRequest.getClientRegistration().getClientName();
		
		log.info(" client name : " + clientName);
		
		OAuth2User oAuth2User =  super.loadUser(userRequest);
		
		Map<String, Object> map = oAuth2User.getAttributes();
		
		Set<String> s =  map.keySet();
		
		Iterator<String> it =   s.iterator();
		
		while(it.hasNext()) {
			String name = it.next();
			log.info(name + " : " + map.get(name));
		}
		//////////////////////////////////////////////////////////////////
		String email = "";
		String name = "";
		String nickname = "";
		String gender = "";
		String mobile = "";
		
		if(clientName.equals("Naver")) {
			Map<String, Object> map2 = (Map<String, Object>) oAuth2User.getAttributes().get("response");
			log.info("map2 : "+map2);
			email = (String) map2.get("email");
			name = (String) map2.get("name");
			nickname = (String) map2.get("nickname");
			gender = (String) map2.get("gender");
			mobile = (String) map2.get("mobile");
			log.info("naver");
		}else if(clientName.equals("Kakao")) {
			Map<String,Object> map3 = (Map<String,Object>) oAuth2User.getAttributes().get("kakao_account");
			Map<String,Object> map4 = (Map<String,Object>) map3.get("profile");
			email = (String) map3.get("email");
			nickname = (String) map4.get("nickname");
			log.info("email : "+email + " , nickname : "+nickname);
		}
		 
		saveMember(email,name,nickname,mobile,gender); 
		
		return super.loadUser(userRequest); 
	}

	private void saveMember(String email, String name, String nickname,String mobile, String gender) {
		// 기존에 등록되어 있는 회원인지 확인후
		UsersDTO dto = userService.getMemberByEmail(email,nickname);
		log.info("멤버 : " + dto);
		log.info(">>>>>>>>>>>>>"+nickname);
		if(dto==null) {
		// 존재하지 않으면 db 에 추가
			dto = UsersDTO.builder()
						.nickname(nickname)
						.id(email)
						.pw("1111")
						.email(email)
						.name(name)
						.phone(mobile)
						.grade("ROLE_USER")
						.coupon(3000)
						.gender(gender)
						.build();
			
			
			/*
			 * private String nickname; private String id; private String pw; private String
			 * gender; private String email; private int coupon; private String phone;
			 * private String grade; private String addrs; private String post; private
			 * String field; private String name; private String p_no;
			 */
			userService.joinUser(dto);
		
		}
		log.info("멤버2 : " + dto);
	}

}
