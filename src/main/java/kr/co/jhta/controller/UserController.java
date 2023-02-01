package kr.co.jhta.controller;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.jhta.dto.UsersDTO;
import kr.co.jhta.service.EmailService;
import kr.co.jhta.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {
	
	
	private final EmailService emailService; 
	private final UserService userService;
	
	@GetMapping("/")
    public String root() {
        return "redirect:/login";
    }

      
     
     //로그인 페이지
    @GetMapping("/login")  
    public String login(){
        return "login";
    }

    //회원가입 페이지 1단계
    @GetMapping("/auth1")
    public String auth1() {
        return "mn_auth1";
    }
    
    
    //회원가입 mn_auth1단계 인증코드
   	@PostMapping("login/mailConfirm")
    @ResponseBody
    String mailConfirm(@RequestParam("email") String email) throws Exception {

       String code = emailService.sendSimpleMessage(email);
       System.out.println("인증코드 : " + code);
       return code;
    }
    
   	//인증코드 적고 회원가입단계 페이지로
   	@GetMapping("/login/signup")
    String signup(@RequestParam("userEmail") String email,Model model){
       
       System.out.println("이메일 : " +email);
       model.addAttribute("email",email);
       
       return "signup";
    }
   	
   	//회원가입 진행
    @PostMapping("/login/signupOk")
    public String signupOk(@ModelAttribute("usersDTO")UsersDTO usersDTO) {
        userService.joinUser(usersDTO);
        return "redirect:/login";
    }
    
   	//아이디 중복확인
   	@PostMapping("/user/idcheck")
   	@ResponseBody
   	public boolean isIdCheck(@RequestParam(value = "userid") String userid) {
   	 
   		log.info("아이디중복체크 클릭시 요청받은 id 값: {}", userid);
   		
   		boolean result = userService.isIdCheck(userid);
   		log.info("아이디중복체크 여부: {}", result);
   		
   		return result;
   	}
   	
    //닉네임 중복확인
   	@PostMapping("/user/nickcheck")
   	@ResponseBody
   	public boolean isNickCheck(@RequestParam(value = "usernick") String usernick) {
   	 
   		log.info("닉네임중복체크 클릭시 요청받은 nick 값: {}", usernick);
   		
   		boolean result = userService.isNickCheck(usernick);
   		log.info("아이디중복체크 여부: {}", result);
   		
   		return result;
   	}
   	
   	//연락처 중복확인
   	@PostMapping("/user/phonecheck")
   	@ResponseBody
   	public boolean isPhoneCheck(@RequestParam(value = "userphone") String userphone) {
   	 
   		log.info("연락처 인증 클릭시 요청받은 userphone 값: {}", userphone);
   		
   		boolean result = userService.isPhoneCheck(userphone);
   		log.info("연락처중복체크 여부: {}", result);
   		
   		return result;
   	}
   	
   	
  

    //로그인 실패
    @GetMapping("/access_denied")
    public String accessDenied() {
    	
        return "redirect:/login";
    }

    

    //로그인 성공
    @RequestMapping("/user_access")
    public String userAccess(Model model, Authentication authentication) {
    	log.info("test");
        //Authentication 객체를 통해 유저 정보를 가져올 수 있다.
        UsersDTO usersDTO = (UsersDTO) authentication.getPrincipal();  //userDetail 객체를 가져옴
        model.addAttribute("info", usersDTO.getId() +"의 "+ usersDTO.getUserName()+ "님");  //유저 아이디
        return "user_access";
    }
	
    
  //아이디찾기 페이지 이동
  	@GetMapping("/findId")
  	public String findId() {
  		return "mn_findId";
  	}
  	
  	//비밀번호찾기 페이지 이동
  	@GetMapping("/findPw")
  	public String findPw() {
  		return "mn_findPw";
  	}
  	
  	
  	
  	
  	//이용약관 글
  	@GetMapping("/tac")
  	public String tac() {
  		return "termsAndConditions";
  	}
  	
  	//개인정보 처리글
  	@GetMapping("/privacy")
  	public String privacy() {
  		return "privacy";
  	}
  	
  	//위치정보 서비스 글
  	@GetMapping("/locationService")
  	public String locationService() {
  		return "locationService";
  	}
    
    
}
