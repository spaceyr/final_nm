package kr.co.jhta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.jhta.dto.ProductDTO;
import kr.co.jhta.service.ProductDetailService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/product")
public class ProductDetailController {
	
	@Autowired
	 ProductDetailService ps;
	
	
		@GetMapping("/productDetail")
		public String product(@RequestParam("p_no")int p_no, Model model) {
			
			List<ProductDTO> list = ps.selectOne(p_no);
			
			model.addAttribute("list",list);
			return "mn_productDetail";
		}
		
		@GetMapping("/pay")// 주소창
		public String pay(@RequestParam("p_no")int p_no, Model model) {
			List<ProductDTO> list = ps.selectOne(p_no);
			
			model.addAttribute("list",list);
			return "pay/payDetail";//보여지는 페이지 이동
		}
}
