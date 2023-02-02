package kr.co.jhta.controller;


import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.jhta.dto.UsersDTO;
import kr.co.jhta.service.EmailService;
import kr.co.jhta.service.UserService;
import kr.co.jhta.util.MailUtil;
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
    public String accessDenied(Model model) {
    	model.addAttribute("fail","아이디와 비밀번호를 확인해주세요.");
        return "/login";
    }

    

    //로그인
    @RequestMapping("/user_access")
    public String userAccess(Authentication authentication, HttpServletRequest request,Model model) {
    	log.info("test");
    	HttpSession session = request.getSession();
        //Authentication 객체를 통해 유저 정보를 가져올 수 있다.
        UsersDTO usersDTO = (UsersDTO) authentication.getPrincipal();  //userDetail 객체를 가져옴
        System.out.println("로그인부분 usersDTO : "+usersDTO);
        
        model.addAttribute("usersDTO",usersDTO);
        session.setAttribute("usersDTO", usersDTO);
        return "main";
    }
    
	/*
	 * //로그아웃
	 * 
	 * @PostMapping("/logout") public String logout(HttpServletRequest request) {
	 * HttpSession session = request.getSession(); session.invalidate();
	 * 
	 * return "redirect:/login"; }
	 */
	
    
  //아이디찾기 페이지 이동
  	@GetMapping("/findId")
  	public String findId() {
  		return "mn_findId";
  	}
  	
  	
  	//아이디 찾기 작동
  	@RequestMapping(value = "/user/findId", method = RequestMethod.POST)
	@ResponseBody
	public String findId(@RequestParam("name") String name,@RequestParam("phone") String phone) {
		System.out.println("name : "+name);
		System.out.println("phone : "+phone);
		if(userService.findId(name,phone) == null) {
			return null; 
		}else {
			return userService.findId(name, phone);
		}
		
	}
  	
  	//비밀번호찾기 페이지 이동
  	@GetMapping("/findPw")
  	public String findPw() {
  		return "mn_findPw";
  	}
  	
  	//비밀번호 찾기 작동
  	@RequestMapping(value = "/finduserpwd",produces = {MediaType.APPLICATION_JSON_VALUE})
  	@ResponseBody
	public String findPw(UsersDTO dto) throws Exception {
		BCryptPasswordEncoder encoder= new BCryptPasswordEncoder();
		String result=null;
		
		//회원정보 불러오기
		UsersDTO dto1 = userService.searchPwd(dto);
		System.out.println(dto1);
		
		//가입된 이메일이 존재한다면 이메일 전송
		if(dto1!=null) {
			
			//임시 비밀번호 생성(UUID이용)
			String tempPw=UUID.randomUUID().toString().replace("-", "");//-를 제거
			tempPw = tempPw.substring(0,10);//tempPw를 앞에서부터 10자리 잘라줌
			
			dto1.setPw(tempPw);//임시 비밀번호 담기
	
			MailUtil mail=new MailUtil(); //메일 전송하기
			mail.sendEmail(dto1);
			
			userService.updatePwd(dto1);
			
			String securePw = encoder.encode(dto1.getPw());//회원 비밀번호를 암호화하면 vo객체에 다시 저장
			dto1.setPw(securePw);
				
			result="true";
			

		}else {
			result="false";
		}
		return result;
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
