package kr.co.jhta.configuration;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class SecurityConfigTest {

	@Autowired
	private PasswordEncoder  passwordEncoder;
	
	
	@Test
	void test() {
		
		String password="aaa";
		
		String enPw = passwordEncoder.encode(password);
		
		boolean matchResult = passwordEncoder.matches(password, enPw);
		System.out.println(enPw);
		assertTrue(matchResult);
	}

}
