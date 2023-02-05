package kr.co.jhta.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.jhta.dto.ProductDTO;
import kr.co.jhta.dto.ReviewDTO;
import kr.co.jhta.dto.StarContentDTO;
import kr.co.jhta.dto.util.PageUtil;
import kr.co.jhta.service.ProductDetailService;
import kr.co.jhta.service.ReviewService;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller

public class BoardController {

	@Autowired
	ReviewService rs;
	
	@Autowired
	ProductDetailService ps;
	

	
//	@GetMapping("/review")
//	public void review(Model model,@ModelAttribute("dto2") StarContentDTO dto2 ) {
//	
//		List<ReviewProductDTO> list = rs.getAllReview();
////인서트문 직접 넣은값 화면에 출력
//		model.addAttribute("list", list);
//		System.out.println("list: "+list);
//
//
//
////저장한 내용 파라미터값 화면 출력
//		StarContentDTO sc = rs.showSC(dto2);
//		model.addAttribute("sc", sc);
//		System.out.println(">>>>>>review.html");
//	}
//	

	
	@GetMapping("board/review")//주소창 이름
	public String list(Model model, @ModelAttribute("dto3") ReviewDTO dto3,
			@RequestParam(name = "cp", defaultValue = "1") int currentPage) {

//		List<ReviewProductDTO> list = rs.getAllReview();
//		model.addAttribute("list",list);	
//		System.out.println("list: "+list );
		
//리뷰 전체 조회
		int totalNumber = rs.getTotal();
		
		int recordPerPage = 10;
		Map <String,Object> map = PageUtil.getPageData(totalNumber, recordPerPage, currentPage);
		int startNo = (int)map.get("startNo");
		int endNo = (int)map.get("endNo");
//									닉네임, 상품명 테이블 조인해야함.
		List<ReviewDTO> list3 = rs.getRd(startNo, endNo);
		model.addAttribute("list3",list3);
		model.addAttribute("map", map);
		return "board/review";
	}

//리뷰 작성시 p_no값 넘겨받음
	@GetMapping("board/write")
	public String writeForm(@RequestParam("p_no") int p_no, Model model) {
		List<ProductDTO> list = ps.selectOne(p_no);
		model.addAttribute("list",list);
		
		System.out.println("list_리뷰작성:" +list);
		return "board/writeForm";
	}
	
	@PostMapping("board/write")
	public String writeOk(@ModelAttribute("dto2") StarContentDTO dto2) {
	System.out.println(dto2.getRating());
		rs.addStar(dto2);
		log.info(">>>>>>>>>>" + dto2);
		return "/main";
	}

// 공지사항
	@GetMapping("board/notice")
	public String notice() {
		return "board/notice";
	}
	
//호스트 리뷰 조회
//	@GetMapping("product/host") {
//		public String host(Model model, @ModelAttribute("dto3") ReviewDTO dto3) {
//			
//			List <ReviewDTO> hlist = 
//			return "product/host";
//		}
//		
//	}

}