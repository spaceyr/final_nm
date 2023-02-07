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
	
	public int p_no;
	public String nickname;
	public String message;
}
