package kr.co.jhta.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class ReviewProductDTO {

	private int r_no;//리뷰번호
	private int p_no;//상품번호

	
//로그인시 닉네임 가져와야함???
	private String nickname;//닉네임

	private String contents;//내용
	private String rating;//평점
	private Date regdate;//등록날짜
	private String title;//상품명
	private int hits;//도움
	

}
