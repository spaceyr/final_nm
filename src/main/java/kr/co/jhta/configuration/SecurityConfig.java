package kr.co.jhta.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import kr.co.jhta.service.CustomOAuth2UserDetailService;
import kr.co.jhta.service.UserService;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private final UserService userService;
	
	private final CustomOAuth2UserDetailService userDetailService;
	
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/resources/**","/oauth2/authorization/**").permitAll()
                // USER, ADMIN 접근 허용
                //.antMatchers("/user_access").authenticated()
               .antMatchers("/user_access").hasAnyRole("USER","MEMBER","ADMIN")
                .antMatchers("/manage").hasRole("ADMIN")
                .antMatchers("/host").hasAnyRole("MEMBER","ADMIN")
               .antMatchers("/login").anonymous()
               // GUEST USER MEMBER ADMIN
                .and()
                .csrf().disable()//토큰번호disable
            .logout() // 로그아웃 기능 작동함
                .logoutUrl("/logout") // 로그아웃 처리 URL, default: /logout, 원칙적으로 post 방식만 지원
                .logoutSuccessUrl("/login")// 로그아웃 성공 후 이동페이지
                .deleteCookies("JSESSIONID") // 로그아웃 후 쿠키 삭제
                .invalidateHttpSession(true) //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ확인해보기
                .clearAuthentication(true)
            .and()	
            .formLogin() //form 로그인기능 작동할때
                .loginPage("/login")
                .loginProcessingUrl("/login_proc")//로그인페이지 form에서 요청들어오면
                .defaultSuccessUrl("/user_access")
                .failureUrl("/access_denied") // 인증에 실패했을 때 보여주는 화면 url, 로그인 form으로 파라미터값 error=true로 보낸다.
               // .failureHandler(customeFailureHandler)
                .and()
                .oauth2Login().loginPage("/login")//csrf()올리고 여기부터 3줄 추가
                .defaultSuccessUrl("/user_access")
                .userInfoEndpoint()
    			.userService(userDetailService) ///소셜로그인할때 회원가입까지
        .and();
        
    		http.sessionManagement()
    		.maximumSessions(100)
    		.maxSessionsPreventsLogin(true);
    }

    /**
     * 로그인 인증 처리 메소드
     * @param auth
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

			auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
