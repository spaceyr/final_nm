package kr.co.jhta.service;


import java.util.Map;

import javax.transaction.Transactional;

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

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

	
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
	public UsersDTO getMemberByEmail(String email) {
		return userMapper.getMemberFindByEmail(email);
	}
	
	
	
	
	  @Transactional//회원가입쪽 
	  public void joinUser(UsersDTO usersDTO) {
	  BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	  usersDTO.setPw(passwordEncoder.encode(usersDTO.getPassword()));
	  System.out.println(usersDTO.getPassword());
	  //usersDTO.setGrade("ROLE_USER");
	  userMapper.saveUsers(usersDTO); 
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
		
		//host프로필수정
				public void hostmodifyOne(String nickname, String email, String phone, String field, String profileimage,String id) {
					userMapper.hostmodifyOne(nickname,email,phone,field,field,id);
				}
	
	
	
	
	

}
