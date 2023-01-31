package kr.co.jhta.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;


import kr.co.jhta.dto.UsersDTO;

@Mapper
public interface UsersMapper {

	// 로그인
	UsersDTO getUsersAccount(String id);

	// 회원가입
	void saveUsers(UsersDTO usersDTO);
	
	//아이디 중복확인
	boolean isIdCheck(String id);
	
	//닉네임 중복확인
	boolean isNickCheck(String nickname);
	
	//연락처 인증(중복확인)
	boolean isPhoneCheck(String phone);
	 

}
