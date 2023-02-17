package kr.co.jhta.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

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

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import kr.co.jhta.dto.ChartDTO;
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
        Integer productCount = service.countProduct(usersDTO.getNickname());
        model.addAttribute("productCount",productCount);
        
        //상품조회수 전달
        Integer countProductReview = service.countProductReview(usersDTO.getNickname());
        model.addAttribute("countProductReview",countProductReview);
        
        //상품신고수 전달
        Integer countProductReport = service.countProductReport(usersDTO.getNickname());
        model.addAttribute("countProductReport",countProductReport);
        
        //상품평점 전달
        //float countProductLike = service.countProductLike(usersDTO.getNickname());
        //model.addAttribute("countProductLike",countProductLike);
        
        //반려메시지 전달
        List<Rejected_messageDTO> list = service.selectRejectmessage(usersDTO.getNickname());
		
        model.addAttribute("list",list);
        
		return "host";
		}else {
			return "login";
		}
		
	}
	
	
	@GetMapping("/allSales")
    public String allSales(Authentication authentication,HttpServletRequest request,Model model){
		//로그인객체전달
				if(authentication != null) {
				HttpSession session = request.getSession();
				UsersDTO usersDTO = (UsersDTO) authentication.getPrincipal();
				System.out.println(authentication);
					
				
				model.addAttribute("usersDTO",usersDTO);
		        session.setAttribute("usersDTO", usersDTO);
		        
		        
		        
		        //반려메시지 전달
		        List<Rejected_messageDTO> list = service.selectRejectmessage(usersDTO.getNickname());
				
		        model.addAttribute("list",list);
		        
		        //판매리스트 전달
		        List<PayDTO> list2 = service.salesList(usersDTO.getNickname());
		        
		        model.addAttribute("list2",list2);
		        
				return "allSales";
				}else {
					return "login";
				}
		
    }
	
	@GetMapping("/monthlySales")
	public String monthlySales(Authentication authentication,HttpServletRequest request,Model model){
		//로그인객체전달
		if(authentication != null) {
			HttpSession session = request.getSession();
			UsersDTO usersDTO = (UsersDTO) authentication.getPrincipal();
			System.out.println(authentication);
			
			
			model.addAttribute("usersDTO",usersDTO);
			session.setAttribute("usersDTO", usersDTO);
			
			
			
			//반려메시지 전달
			List<Rejected_messageDTO> list = service.selectRejectmessage(usersDTO.getNickname());
			
			model.addAttribute("list",list);
			
			//json 으로
			List<ChartDTO> logNameList = service.salesListmonth(usersDTO.getNickname());

			Gson gson = new Gson();
			JsonArray jArray = new JsonArray();
					
			Iterator<ChartDTO> it = logNameList.iterator();
			while(it.hasNext()) {
				ChartDTO curVO = it.next();
				JsonObject object = new JsonObject();
				String m = curVO.getM();
				int price = curVO.getPrice();
				
			    object.addProperty("m", m);
				object.addProperty("price", price);
				jArray.add(object);
			}
					
			String json = gson.toJson(jArray);
			model.addAttribute("json", json);
			
			return "monthlySales";
		}else {
			return "login";
		}
		
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
	public String editProfile(Authentication authentication,HttpServletRequest request,Model model){
		
		HttpSession session = request.getSession();
		UsersDTO usersDTO = (UsersDTO) authentication.getPrincipal();
		System.out.println(authentication);
			
		
		model.addAttribute("usersDTO",usersDTO);
        session.setAttribute("usersDTO", usersDTO);
		
		return "editProfile";
	}
	
	@PostMapping("/editProfile")
	public String editProfileOk(@RequestParam("nickname")String nickname,
								@RequestParam("email")String email,
								@RequestParam("phone")String phone,
								@RequestParam("field")String field,
								@RequestParam("profileimage")String profileimage,
								@RequestParam("id")String id){
		
		service.hostmodifyOne(nickname, email, phone, field, profileimage, id);
		
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
	public String reviewManage(Model model){
		
		/*
		 * List<ReviewDTO> list = service.showAllReview();
		 * 
		 * model.addAttribute("list",list);
		 */
		
		return "reviewManage";
	}
	
	@PostMapping("/reviewManage")
	public String reviewManageOk(@RequestParam(value = "contents",required = false)String contents,
			@RequestParam(value = "writer",required = false)String writer, Model model){
		
		List<ReviewDTO> list = service.selectOneReview(contents, writer);
		
		model.addAttribute("list", list);	
		
		return "reviewManage";
	}
	
	@GetMapping("/hostWithd")
	public String hostWithd(){
		return "hostWithd";
	}
	
	@GetMapping("/chartjs")
	public String chartjs(Authentication authentication,HttpServletRequest request,Model model){
		// 로그인객체전달
		HttpSession session = request.getSession();
		UsersDTO usersDTO = (UsersDTO) authentication.getPrincipal();
		System.out.println(authentication);	

		model.addAttribute("usersDTO", usersDTO);
		session.setAttribute("usersDTO", usersDTO);
		
		
		//json 으로
		List<ChartDTO> logNameList = service.salesListmonth(usersDTO.getNickname());

		Gson gson = new Gson();
		JsonArray jArray = new JsonArray();
				
		Iterator<ChartDTO> it = logNameList.iterator();
		while(it.hasNext()) {
			ChartDTO curVO = it.next();
			JsonObject object = new JsonObject();
			String m = curVO.getM();
			int price = curVO.getPrice();
			
		    object.addProperty("m", m);
			object.addProperty("price", price);
			jArray.add(object);
		}
				
		String json = gson.toJson(jArray);
		model.addAttribute("json", json);
		
		return "chartjs";
	}
	
	@GetMapping("/charttest")
	public String charttest(Locale locale,Authentication authentication,HttpServletRequest request,Model model){
		// 로그인객체전달
		HttpSession session = request.getSession();
		UsersDTO usersDTO = (UsersDTO) authentication.getPrincipal();
		System.out.println(authentication);	
		
		model.addAttribute("usersDTO", usersDTO);
		session.setAttribute("usersDTO", usersDTO);
		
		Gson gson = new Gson();
		
		List<ChartDTO> list = service.salesListmonth(usersDTO.getNickname());
		
		
		
		
		return "charttest";
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
	
	@GetMapping("/noticeForm")
	public String noticeForm(Authentication authentication,HttpServletRequest request,Model model){
		// 로그인객체전달
		HttpSession session = request.getSession();
		UsersDTO usersDTO = (UsersDTO) authentication.getPrincipal();
		
		model.addAttribute("usersDTO", usersDTO);
		session.setAttribute("usersDTO", usersDTO);

		return "noticeForm";
	}
	
	@GetMapping("/adForm")
	public String adForm(Authentication authentication,HttpServletRequest request,Model model){
		// 로그인객체전달
		HttpSession session = request.getSession();
		UsersDTO usersDTO = (UsersDTO) authentication.getPrincipal();
		
		model.addAttribute("usersDTO", usersDTO);
		session.setAttribute("usersDTO", usersDTO);
		
		return "adForm";
	}
	
	@GetMapping("/newmeetReject")
	public String newmeetReject(Authentication authentication,HttpServletRequest request,Model model){
		// 로그인객체전달
		HttpSession session = request.getSession();
		UsersDTO usersDTO = (UsersDTO) authentication.getPrincipal();
		
		model.addAttribute("usersDTO", usersDTO);
		session.setAttribute("usersDTO", usersDTO);
		
		
		List<ProductDTO> list2 = service.rejectInsepection();
		
		model.addAttribute("list2", list2);
		
		//반려메시지 전달
        List<Rejected_messageDTO> list = service.selectRejectmessage(usersDTO.getNickname());
		
        model.addAttribute("list",list);
		
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
		//UsersDTO usersDTO = (UsersDTO) authentication.getPrincipal();
		UsersDTO usersDTO = (UsersDTO) session.getAttribute("usersDTO");
		
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
		model.addAttribute("fail","로그인이 필요합니다.");
		return "/login/login";
		}
	}
	
	@PostMapping("/mypageOk")
	public String mypageOk(Model model, @ModelAttribute("dto2") PayDTO dto2, @ModelAttribute("pdto") PayDTO pdto,
			Authentication authentication, HttpServletRequest request, @ModelAttribute("rdto") ReviewDTO rdto,
							@RequestParam("nickname")String nickname,
							@RequestParam("email")String email,
							@RequestParam("phone")String phone,
							@RequestParam("field")String field,
							@RequestParam("profileimage")String profileimage,
							@RequestParam("id")String id) {
		
		//로그인객체전달
		if (authentication != null) {
			HttpSession session = request.getSession();
			//UsersDTO usersDTO = (UsersDTO) authentication.getPrincipal();
			UsersDTO usersDTO = (UsersDTO) session.getAttribute("usersDTO");
			
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
			
			//프로필수정
			service.hostmodifyOne(nickname, email, phone, field, profileimage, id);
			
			return "mypage";
			
		} else {
			return "/login/login";
		}
	}
}