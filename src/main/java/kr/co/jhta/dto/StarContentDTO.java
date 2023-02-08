package kr.co.jhta.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StarContentDTO {

	private int r_no;
	private String contents;
	private Date regdate;
	private int rating;
	private int p_no;
	private String nickname;
	private String writer;


}
