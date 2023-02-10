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
	
	//아이디 찾기
	String findById(String name, String phone);
	
	//임시 비밀번호를 위한 아이디 이메일 db검사
	UsersDTO searchPw(UsersDTO dto);
	
	//업데이트
	void updatePw(UsersDTO dto);

	//소셜로그인 이메일확인
	UsersDTO getMemberFindByEmail(String email,String nickname);

	
	/*
	 * void hostmodifyOne(String nickname, String email, String phone, String field,
	 * String profileimage,String id);
	 */

	boolean isIdCheck2(String name, String phone);

	UsersDTO getSocial(String nickname);
}
