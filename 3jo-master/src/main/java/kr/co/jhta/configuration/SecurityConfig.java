package kr.co.jhta.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import kr.co.jhta.service.UserService;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private final UserService userService;
    
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers( "/login", "/singUp", "/access_denied","/resources/**").permitAll() // 로그인 권한은 누구나, resources파일도 모든권한
                // USER, ADMIN 접근 허용
                //.antMatchers("/user_access").authenticated()
               .antMatchers("/user_access").hasRole("USER")
//                .antMatchers("/user_access").hasRole("ADMIN")
               // GUEST USER MEMBER ADMIN
                .and()
            .formLogin() //form 로그인기능 작동할때
                .loginPage("/login")
                .loginProcessingUrl("/login_proc")//로그인페이지 form에서 요청들어오면
                .defaultSuccessUrl("/user_access")
                .failureUrl("/access_denied") // 인증에 실패했을 때 보여주는 화면 url, 로그인 form으로 파라미터값 error=true로 보낸다.
                .and()
            .csrf().disable();//토큰번호disable		//로그인 창 
//정리필요       logout()
//		.permitAll()
//		// .logoutUrl("/logout") // 로그아웃 URL (기본 값 : /logout)
//		// .logoutSuccessUrl("/login?logout") // 로그아웃 성공 URL (기본 값 : "/login?logout")
//		.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // 주소창에 요청해도 포스트로 인식하여 로그아웃
//		.deleteCookies("JSESSIONID") // 로그아웃 시 JSESSIONID 제거
//		.invalidateHttpSession(true) // 로그아웃 시 세션 종료
//		.clearAuthentication(true); // 로그아웃 시 권한 제거
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
