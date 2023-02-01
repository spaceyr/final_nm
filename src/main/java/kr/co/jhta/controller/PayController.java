package kr.co.jhta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.jhta.dto.ProductDTO;
import kr.co.jhta.service.PayService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/pay")
public class PayController {
	
	@Autowired
	 PayService ps;

// 상품상세보기 -> '참여하기'버튼 누르면 모달창 띄움
	@GetMapping("/product")// 주소창
	public String product(@RequestParam("p_no") int p_no, Model model) {
		ProductDTO dto = ps.selectOne(p_no);
		model.addAttribute("dto",dto);
		return "pay/productDetail";//보여지는 페이지 이동
	}
	
// '결제완료' 버튼 누르면 결제 완료 페이지로 넘어감
	@GetMapping("/order")
	public String payForm() {
		return "pay/payForm";
	}
	
	
} 
