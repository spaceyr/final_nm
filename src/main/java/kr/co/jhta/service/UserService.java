package kr.co.jhta.service;


import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import kr.co.jhta.dao.UsersMapper;
import kr.co.jhta.dto.UsersDTO;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService { //시큐리티에서 유저 정보를 쓰기 위한 인터페이스

	
	private final UsersMapper userMapper; //매퍼 받아와서
	private final JavaMailSender javaMailSender;
	

	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		//여기서 받은 유저 패스워드와 비교하여 로그인 인증
		System.out.println("id : " + id);
        UsersDTO usersDTO = userMapper.getUsersAccount(id);
        System.out.println("usersDTO : " + usersDTO);
        if (usersDTO == null){
            throw new UsernameNotFoundException("User not authorized.");
        }
       
        
        return usersDTO;
    }
	
	//소셜로그인
	public UsersDTO getMemberByEmail(String email,String nickname) {
		return userMapper.getMemberFindByEmail(email,nickname);
	}
	
	
	
	
	  @Transactional//회원가입쪽 
	  public void joinUser(UsersDTO usersDTO) {
	  BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	  usersDTO.setPw(passwordEncoder.encode(usersDTO.getPassword()));
	  System.out.println(usersDTO.getPassword());
	  //usersDTO.setGrade("ROLE_USER");
	  userMapper.saveUsers(usersDTO); 
	  }
	 
	  	//
	  	public boolean isIdCheck2(String name,String phone) {
	  		return userMapper.isIdCheck2(name,phone);
	  	}
	  
	  
		//아이디 중복확인
		public boolean isIdCheck(String id) {
			return userMapper.isIdCheck(id);
		}
		
		//닉네임 중복확인
		public boolean isNickCheck(String nickname) {
			return userMapper.isNickCheck(nickname);
		}
		
		//연락처 인증
		public boolean isPhoneCheck(String phone) {
			return userMapper.isPhoneCheck(phone);
		}
		
		//아이디 찾기
		public String findId(String name, String phone) {
			return userMapper.findById(name, phone);
		}
	
		//회원 비밀번호 아이디 디비 검사
		public UsersDTO searchPwd(UsersDTO dto) {
			return userMapper.searchPw(dto);
		}
		
		//임시 비밀번호 발급
		public void updatePwd(UsersDTO dto) {
			userMapper.updatePw(dto);
		}
		
		/*
		 * // host프로필수정 public void hostmodifyOne(String nickname, String email, String
		 * phone, String field, String profileimage, String id) {
		 * userMapper.hostmodifyOne(nickname, email, phone, field, field, id); }
		 */
	
				public void certifiedPhoneNumber(String userPhoneNumber, int randomNumber) {
					String api_key = "NCSMRDR2O1QX2UIQ";
				    String api_secret = "WUEFMB67JTF8LVHICEKLTQ3LCCRNNXAO";
				    Message coolsms = new Message(api_key, api_secret);

				    // 4 params(to, from, type, text) are mandatory. must be filled
				    HashMap<String, String> params = new HashMap<String, String>();
				    params.put("to", userPhoneNumber);    // 수신전화번호
				    params.put("from", "01028077034");    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
				    params.put("type", "SMS");
				    params.put("text", "[TEST] 인증번호는" + "["+randomNumber+"]" + "입니다."); // 문자 내용 입력
				    params.put("app_version", "test app 1.2"); // application name and version

				    try {
				        JSONObject obj = (JSONObject) coolsms.send(params);
				        System.out.println(obj.toString());
				      } catch (CoolsmsException e) {
				        System.out.println(e.getMessage());
				        System.out.println(e.getCode());
				      }
				}

				
	
				//임시 정보갖기
				public UsersDTO getSocialInfo(String nickname) {
					return userMapper.getSocial(nickname);
				}
	

}
