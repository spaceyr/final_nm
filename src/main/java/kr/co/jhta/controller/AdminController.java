package kr.co.jhta.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import kr.co.jhta.dto.Rejected_messageDTO;
import kr.co.jhta.dto.ReviewDTO;
import kr.co.jhta.dto.UsersDTO;
import kr.co.jhta.service.PayService;
import kr.co.jhta.service.ProductService;
import kr.co.jhta.service.ReviewService;
import kr.co.jhta.service.UserService;

@Controller
public class AdminController {
	
	@Autowired
	ProductService service;
	
	@Autowired
	UserService userservice;
	
	
	UsersDTO usersDTO;
	
	@Autowired
	PayService payservice;
	
	@Autowired
	ReviewService rs;
	
	 LocalDate now = LocalDate.now(); 
	 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
	 String formatedNow =  now.format(formatter);
	 
	
	
	
	@GetMapping("/host")
	public String host(Authentication authentication,HttpServletRequest request,Model model){
		//로그인객체전달
		if(authentication != null) {
		HttpSession session = request.getSession();
		UsersDTO usersDTO = (UsersDTO) authentication.getPrincipal();
		System.out.println(authentication);
			
		
		model.addAttribute("usersDTO",usersDTO);
        session.setAttribute("usersDTO", usersDTO);
        
        //상품개수 전달
        int productCount = service.countProduct(usersDTO.getNickname());
        model.addAttribute("productCount",productCount);
        
        //상품조회수 전달
        int countProductReview = service.countProductReview(usersDTO.getNickname());
        model.addAttribute("countProductReview",countProductReview);
        
        //상품신고수 전달
        int countProductReport = service.countProductReport(usersDTO.getNickname());
        model.addAttribute("countProductReport",countProductReport);
        
        //상품평점 전달
        float countProductLike = service.countProductLike(usersDTO.getNickname());
        model.addAttribute("countProductLike",countProductLike);
        
        //반려메시지 전달
        List<Rejected_messageDTO> list = service.selectRejectmessage(usersDTO.getNickname());
		
        model.addAttribute("list",list);
        
		return "host";
		}else {
			return "login";
		}
		
	}
	
	
	@GetMapping("/dashboard")
    public String home(){
        return "dashboard";
    }
	
	@GetMapping("/newmeetForm")
	public String newmeetForm(Authentication authentication,HttpServletRequest request,Model model){
		// 로그인객체전달
		HttpSession session = request.getSession();
		UsersDTO usersDTO = (UsersDTO) authentication.getPrincipal();
		System.out.println(authentication);

		model.addAttribute("usersDTO", usersDTO);
		session.setAttribute("usersDTO", usersDTO);
		
		return "newmeetForm";
	}
	
	@PostMapping("/newmeetForm")
	public String newmeetFormOk(@ModelAttribute("dto")ProductDTO dto){
				
		String regdate = formatedNow;
		
		dto.setRegdate(regdate);
		
		service.insertOne(dto);
		
		return "newmeetForm";
	}
	
	@GetMapping("/editProfile")
	public String editProfile(){
		return "editProfile";
	}
	
	@PostMapping("/editProfile")
	public String editProfileOk(@RequestParam("nickname")String nickname,
								@RequestParam("email")String email,
								@RequestParam("phone")String phone,
								@RequestParam("field")String field,
								@RequestParam("profileimage")String profileimage,
								@RequestParam("id")String id){
		
		userservice.hostmodifyOne(nickname, email, phone, field, profileimage,id);
		
		return "editProfile";
	}
	
	@GetMapping("/hostProductList")
	public String hostProductList(Model model){
		
		//List<ProductDTO> list = service.selectAll();
		
		//model.addAttribute("list",list);
		
		return "hostProductList";
	}
	
	@PostMapping("/hostProductList")
	public String hostProductListOk(@RequestParam(value = "keyword",required = false)String keyword,
			@RequestParam(value = "from_date",required = false)String from_date,
			@RequestParam(value = "to_date",required = false)String to_date,
			@RequestParam(value = "inspection",required = false)String inspection, Model model){
		
		List<ProductDTO> list = service.selectOneMj(keyword,from_date, to_date, inspection);
		
		model.addAttribute("list", list);
		
		model.addAttribute("keyword", keyword);
		model.addAttribute("from_date", from_date);
		model.addAttribute("to_date", to_date);
		
		return "hostProductList";
	}
	
	@GetMapping("/reviewManage")
	public String reviewManage(){
		
		return "reviewManage";
	}
	
	@GetMapping("/hostWithd")
	public String hostWithd(){
		return "hostWithd";
	}
	
	@GetMapping("/manage")
	public String manage(Authentication authentication,HttpServletRequest request,Model model){
		// 로그인객체전달
		HttpSession session = request.getSession();
		UsersDTO usersDTO = (UsersDTO) authentication.getPrincipal();

		model.addAttribute("usersDTO", usersDTO);
		session.setAttribute("usersDTO", usersDTO);

		

		
		
		return "manage";
	}
	
	@GetMapping("/newmeetReject")
	public String newmeetReject(Authentication authentication,HttpServletRequest request,Model model){
		// 로그인객체전달
		HttpSession session = request.getSession();
		UsersDTO usersDTO = (UsersDTO) authentication.getPrincipal();
		
		model.addAttribute("usersDTO", usersDTO);
		session.setAttribute("usersDTO", usersDTO);
		
		
		List<ProductDTO> list = service.rejectInsepection();
		
		model.addAttribute("list", list);
		
		return "newmeetReject";
	}
	
	@GetMapping("/newmeetInspection")
	public String newmeetInspection(Authentication authentication,HttpServletRequest request,Model model){
		// 로그인객체전달
		HttpSession session = request.getSession();
		UsersDTO usersDTO = (UsersDTO) authentication.getPrincipal();

		model.addAttribute("usersDTO", usersDTO);
		session.setAttribute("usersDTO", usersDTO);
		
		
		List<ProductDTO> list = service.selectInsepection();
		
		model.addAttribute("list", list);
		
		return "newmeetInspection";
	}
	
	@PostMapping("/newmeetInspection")
	public String newmeetInspectionOk(@RequestParam("inspection")int inspection,
									  @ModelAttribute("dto")Rejected_messageDTO dto,
										Authentication authentication,HttpServletRequest request,Model model){
		// 로그인객체전달
		HttpSession session = request.getSession();
		UsersDTO usersDTO = (UsersDTO) authentication.getPrincipal();
		
		model.addAttribute("usersDTO", usersDTO);
		session.setAttribute("usersDTO", usersDTO);
		
		/* 검수안된거 객체전달 */
		List<ProductDTO> list = service.selectInsepection();
		
		model.addAttribute("list", list);
		
		if(inspection == 0) {
			service.inspectionmodifyOne(dto);
		}else if(inspection == -1) {
			service.rejectmodifyOne(dto);
			service.rejectinsertOne(dto);
		}
		
		
		return "redirect:/newmeetInspection";
	}
	
	@GetMapping("/mypage")
	public String mypage(Model model, @ModelAttribute("dto2") PayDTO dto2, @ModelAttribute("pdto") PayDTO pdto,
			Authentication authentication, HttpServletRequest request, @ModelAttribute("rdto") ReviewDTO rdto) {
		
		//로그인객체전달
		if (authentication != null) {
		HttpSession session = request.getSession();
		UsersDTO usersDTO = (UsersDTO) authentication.getPrincipal();
				
		model.addAttribute("usersDTO",usersDTO);
		session.setAttribute("usersDTO", usersDTO);
		
		//결제내역
		List<PayDTO> list2 = payservice.getPayAll(usersDTO.getNickname());
		model.addAttribute("list2",list2);
		System.out.println("list2:"+list2);
		
		//후기내역전달
		List<ReviewDTO> list3 = rs.getReview(usersDTO.getNickname());
		model.addAttribute("list3",list3);
		
		//찜한상품만 가져오기
		List<ProductDTO> list = service.selectOneJjim(usersDTO.getNickname());
		model.addAttribute("list", list);
		return "mypage";
	
	} else {
		return "/login";
		}
	}
}