package kr.co.jhta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.jhta.dto.PayDTO;
import kr.co.jhta.dto.ProductDTO;
import kr.co.jhta.service.PayService;
import kr.co.jhta.service.ProductDetailService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/pay")
public class PayController {
	
	@Autowired
	 ProductDetailService ps;
	
	@Autowired
	PayService payservice;

	@GetMapping("/product")// 주소창
	public String product(@RequestParam("p_no") int p_no, Model model) {
		List<ProductDTO> dto = ps.selectOne(p_no);
		model.addAttribute("dto",dto);
		return "pay/productDetail";
	}
	
//'결제완료' 이동
	@GetMapping("/order")
	public String payForm() {
		
		return "pay/payForm";
	}
//결제완료 페이지에서 '마이페이지' 이동 -> 결제내역 확인하기
	
	
	
	
	
	
} 
