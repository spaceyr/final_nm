package kr.co.jhta.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

//메인페이지랑 연결하면 삭제해도됨
	@GetMapping("/")
	public String main() {
		return "home";
	}
}
