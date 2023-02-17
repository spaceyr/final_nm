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
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.jhta.dto.ProductDTO;
import kr.co.jhta.dto.ReviewDTO;
import kr.co.jhta.dto.StarContentDTO;
import kr.co.jhta.dto.UsersDTO;

import kr.co.jhta.service.ProductDetailService;
import kr.co.jhta.service.ReviewService;
import kr.co.jhta.util.PageUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BoardController {

	@Autowired
	ReviewService rs;

	@Autowired
	ProductDetailService ps;

	@GetMapping("/review") // 주소창 이름
	public String list(Model model, @ModelAttribute("dto3") ReviewDTO dto3,
			@RequestParam(name = "cp", defaultValue = "1") int currentPage, Authentication authentication,
			HttpServletRequest request) {

		// 로그인객체전달
		HttpSession session = request.getSession();
//		UsersDTO usersDTO = (UsersDTO) authentication.getPrincipal();
		UsersDTO usersDTO = (UsersDTO) session.getAttribute("usersDTO");
		model.addAttribute("usersDTO", usersDTO);
		session.setAttribute("usersDTO", usersDTO);
		
		System.out.println("리뷰페이지 GetMapping usersDTO : "+usersDTO);
		//리뷰 전체 조회
		
		int totalNumber = rs.getTotal();

		int recordPerPage = 10;
		Map<String, Object> map = PageUtil.getPageData(totalNumber, recordPerPage, currentPage);
		int startNo = (int) map.get("startNo");
		int endNo = (int) map.get("endNo");

		List<ReviewDTO> list3 = rs.getRd(startNo, endNo);
		model.addAttribute("list3", list3);
		model.addAttribute("map", map);
		return "board/review";
		
	}

//리뷰 작성시 p_no값 넘겨받음
	@GetMapping("board/write")
	public String writeForm(@RequestParam("p_no") int p_no, Model model,
			Authentication authentication, HttpServletRequest request) {
		//로그인객체전달
		HttpSession session = request.getSession();
		//UsersDTO usersDTO = (UsersDTO) authentication.getPrincipal();
		UsersDTO usersDTO = (UsersDTO) session.getAttribute("usersDTO");
		
		model.addAttribute("usersDTO",usersDTO);
		session.setAttribute("usersDTO", usersDTO);
					
		List<ProductDTO> list = ps.selectOne(p_no);
		model.addAttribute("list", list);

		System.out.println("list_리뷰작성:" + list);
		return "board/writeForm";
	}

//리뷰 작성완료시 db에 저장
	@PostMapping("board/write")
	public String writeOk(@ModelAttribute("dto2") StarContentDTO dto2,
			Authentication authentication,
			HttpServletRequest request, 
			Model model
			) {
		//로그인객체전달
		HttpSession session = request.getSession();
		//UsersDTO usersDTO = (UsersDTO) authentication.getPrincipal();
		UsersDTO usersDTO = (UsersDTO) session.getAttribute("usersDTO");
		
		model.addAttribute("usersDTO",usersDTO);
		session.setAttribute("usersDTO", usersDTO);
		
		System.out.println(dto2.getRating());
		
		rs.addStar(dto2);
		log.info(">>>>>>>>>>" + dto2);
		return "/main";
	}

//공지사항
	@GetMapping("board/notice")
	public String notice() {
		return "board/notice";
	}

//후기삭제
		@GetMapping("review/delete")
		public String deleteReview(@RequestParam("r_no") int r_no) {
			rs.deleteReview(r_no);
			return "redirect:/mypage";
		}
//	호스트별 리뷰 조회 
	 @GetMapping("/product/review") 
	 public String host(Model model, @RequestParam("nickname")String nickname) { 
		 List<ReviewDTO> host = rs.selectHost(nickname);
		 model.addAttribute("host",host); 
		 return "review"; 
	 }
	 

}
