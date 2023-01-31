package kr.co.jhta.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
	private int p_no;
	private String title;
	private String contents;
	private String regdate;
	private String image;
	private String detail_image;
	private int price;
	private String location;
	private int person;
	private String period;
	private int report;
	private int hits;
	private int cateno;
	private float like;
	private int level;
	private String nickname;
	private int inspection;
	
	
}
