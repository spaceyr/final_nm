package kr.co.jhta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.jhta.dto.PayDTO;
import kr.co.jhta.dto.ProductDTO;
import kr.co.jhta.service.PayService;
import kr.co.jhta.service.ProductDetailService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller

public class ProductDetailController {
	
	@Autowired
	PayService payservice;
	
	@Autowired
	 ProductDetailService ps;
	
	
		@GetMapping("product/productDetail")
		public String product(@RequestParam("p_no")int p_no, Model model) {
			
			List<ProductDTO> list = ps.selectOne(p_no);

			
			model.addAttribute("list",list);
			return "mn_productDetail";
		}

// 상품상세보기에서 참여하기 누르면 결제페이지로 이동
		@GetMapping("product/pay")// 주소창
		public String pay(@RequestParam("p_no")int p_no, Model model) {
			List<ProductDTO> list = ps.selectOne(p_no);
			
			model.addAttribute("list",list);
			return "pay/payDetail";//보여지는 페이지 이동
		}
	
// 결제페이지에서 결제완료 누르면 마이페이지로 이동
		@PostMapping("/mypage") 
		public String payOk(@ModelAttribute("dto2") PayDTO dto2) {
			
			System.out.println(dto2.getNickname());
			
			payservice.payAddOne(dto2);
			return "/mypage";
		}
}
