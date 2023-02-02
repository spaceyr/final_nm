package kr.co.jhta.controller;

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
	
	// 상품상세보기 -> '참여하기'버튼 누르면 모달창 띄움
		@GetMapping("/productDetail")// 주소창
		public String product() {
			//@RequestParam("p_no") int p_no, Model model
//			ProductDTO dto = ps.selectOne(p_no);
//			model.addAttribute("dto",dto);
			return "mn_productDetail";//보여지는 페이지 이동
		}
}
