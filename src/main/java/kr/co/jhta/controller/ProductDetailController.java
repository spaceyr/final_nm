package kr.co.jhta.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.jhta.dto.PayDTO;
import kr.co.jhta.dto.ProductDTO;
import kr.co.jhta.dto.ReviewDTO;
import kr.co.jhta.dto.UsersDTO;
import kr.co.jhta.service.PayService;
import kr.co.jhta.service.ProductDetailService;
import kr.co.jhta.service.ProductService;
import kr.co.jhta.service.ReviewService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller

public class ProductDetailController {

	@Autowired
	ProductService service;

	@Autowired
	PayService payservice;

	@Autowired
	ProductDetailService ps;
	
	@Autowired
	ReviewService rs;

// 상품상세페이지
	@GetMapping("product/productDetail")
	public String product(@RequestParam("p_no") int p_no, Model model) {
		List<ProductDTO> list = ps.selectOne(p_no);
		model.addAttribute("list", list);
		return "mn_productDetail";
	}

// 상품상세보기에서 참여하기 누르면 결제페이지로 이동
	@GetMapping("product/pay") // 주소창
	public String pay(@RequestParam("p_no") int p_no, Model model,
			Authentication authentication, HttpServletRequest request) {
		
		//로그인객체전달
			HttpSession session = request.getSession();
			//UsersDTO usersDTO = (UsersDTO) authentication.getPrincipal();
			UsersDTO usersDTO = (UsersDTO) session.getAttribute("usersDTO");
			System.out.println("usersDTO : "+usersDTO);
			
			if(usersDTO != null) {
			model.addAttribute("usersDTO", usersDTO);
			session.setAttribute("usersDTO", usersDTO);
		
			List<ProductDTO> list = ps.selectOne(p_no);

		model.addAttribute("list", list);
		return "pay/payDetail";// 보여지는 페이지 이동
			}else {
				model.addAttribute("fail","로그인이 필요합니다.");
				return "/login/login";
			}
	}


//상품 환불
	@GetMapping("product/pay/delete")
	public String deleteOk(@RequestParam("pay_no") int pay_no) {
		payservice.deletePay(pay_no);
		return "redirect:/main";
	}

// 결제페이지에서 결제완료 누르면 마이페이지로 이동 -> 결제내역 확인하기
	@PostMapping("/mypage")
	public String payOk(Model model, @ModelAttribute("dto2") PayDTO dto2, @ModelAttribute("pdto") PayDTO pdto,
			Authentication authentication, HttpServletRequest request, @ModelAttribute("rdto") ReviewDTO rdto) {
		
		System.out.println("dto2 : "+dto2);
		System.out.println("pdto : "+pdto);
		System.out.println("rdto : "+rdto);
		
		//로그인객체전달
		if (authentication != null) {
			HttpSession session = request.getSession();
//			UsersDTO usersDTO = (UsersDTO) authentication.getPrincipal();
			UsersDTO usersDTO = (UsersDTO) session.getAttribute("usersDTO");
			model.addAttribute("usersDTO", usersDTO);
			session.setAttribute("usersDTO", usersDTO);

		
		//찜전달
			List<ProductDTO> list = service.selectOneJjim(usersDTO.getNickname());
			model.addAttribute("list", list);

		//결제내역전달
			List<PayDTO> list2 = payservice.getPayAll(usersDTO.getNickname());
			model.addAttribute("list2", list2);
			System.out.println("list2:" + list2);
			
			payservice.payAddOne(dto2);
			log.info(">>>>>>>>>>"+dto2);
		
		//후기내역전달
			List<ReviewDTO> list3 = rs.getReview(usersDTO.getNickname());
			model.addAttribute("list3",list3);
			
			return "/mypage";

		} else {
			return "/login/login";
		}

	}
}
