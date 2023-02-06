package kr.co.jhta.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Rejected_messageDTO {
	
	private int p_no;
	private String nickname;
	private String message;
}
