package kr.co.jhta.controller;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.jhta.dto.ReviewDTO;
import kr.co.jhta.dto.ReviewProductDTO;
import kr.co.jhta.dto.StarContentDTO;
import kr.co.jhta.dto.UsersDTO;
import kr.co.jhta.dto.util.PageUtil;
import kr.co.jhta.service.ReviewService;
import kr.co.jhta.service.UserService;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	ReviewService rs;
	

	
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

	
	@GetMapping("/review")//주소창 이름
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

//리뷰 작성
	@GetMapping("/write")
	public String writeForm() {
		return "board/writeForm";
	}
	
	@PostMapping("/write")
	public String writeOk(@ModelAttribute("dto2") StarContentDTO dto2) {
	System.out.println(dto2.getRating());
		rs.addStar(dto2);
		log.info(">>>>>>>>>>" + dto2);
		return "redirect:/board/review";
	}

// 공지사항
	@GetMapping("/notice")
	public String notice() {
		return "board/notice";
	}
}
