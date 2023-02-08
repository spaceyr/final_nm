package kr.co.jhta.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDTO {

	private int r_no;
	private int p_no;

	private String nickname;
	private String writer;

	private String contents;
	private String rating;
	private Date regdate;

	private String title;
}
