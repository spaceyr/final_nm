package kr.co.jhta.service;


import javax.transaction.Transactional;

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
	
	
	
	
	
	
	
	

}
