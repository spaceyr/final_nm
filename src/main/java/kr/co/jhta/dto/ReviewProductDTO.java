package kr.co.jhta.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class ReviewProductDTO {

	private int p_no;

	private int r_no;
//로그인시 닉네임 가져와야함
	private String nickname;

	private String title;
	private String contents;
	private String rating;
	private Date regdate;
	private String image;
	

}
