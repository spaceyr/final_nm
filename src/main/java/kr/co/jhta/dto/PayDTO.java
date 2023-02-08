package kr.co.jhta.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class PayDTO {

	private String nickname;
	private String title;
	private int p_no;
	private Date pay_date;
	private String pay_method;
	private int pay_no;
	private int pay_status;
	private int price;
	private String customer;
}
