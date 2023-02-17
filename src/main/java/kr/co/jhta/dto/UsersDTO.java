package kr.co.jhta.dto;

import java.util.Collection;
import java.util.Collections;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsersDTO implements UserDetails{ //시큐리티에서 사용자 정보를 담기 위한 인터페이스
	
	@NotBlank(message = "닉네임을 입력해주세요")
	@Pattern(regexp = "/^[\\w\\Wㄱ-ㅎㅏ-ㅣ가-힣]{2,20}$/", message = "사용할 수 없는 닉네임입니다.")
	private String nickname;
	
	@NotBlank(message = "아이디를 입력해주세요")
	@Pattern(regexp = "/^[a-z]+[a-z0-9]{5,19}$/g",message = "아이디는 영문으로 시작해 6~20자의 영문자 또는 숫자이어야 합니다.")
	private String id;
	
	@NotBlank(message = "비밀번호를 입력해주세요")
	private String pw;
	
	
	private String gender;
	private String email;		
	private int coupon;
	
	@NotBlank(message = "연락처를 입력해주세요")
	private String phone;
	
	private String grade;			
	private String addrs;		
	private String post; 	
	private String field;
	private String profileimage;
	
	@NotBlank(message = "이름을 입력해주세요")
	private String name;
	private int p_no;
	
	@Override//등급 권한.
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority(this.grade));
	}
	@Override
	public String getPassword() {
		return this.pw;
	}
	@Override
	public String getUsername() {
		return this.id;
	}
	
	public String getUserName() {
		return this.name;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	}