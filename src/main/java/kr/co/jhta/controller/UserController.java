package kr.co.jhta.controller;


import java.util.Map;
import java.util.UUID;
import java.util.jar.Attributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
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

import com.nimbusds.oauth2.sdk.Response;

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
	
	
      
     
     //로그인 페이지
    @GetMapping("/login")  
    public String login(){
        return "/login/login";
    }

    //회원가입 페이지 1단계
    @GetMapping("/auth1")
    public String auth1() {
        return "/login/mn_auth1";
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
       
       return "/login/signup";
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
//   	@PostMapping("/user/phonecheck")
//   	@ResponseBody
//   	public boolean isPhoneCheck(@RequestParam(value = "userphone") String userphone) {
//   	 
//   		log.info("연락처 인증 클릭시 요청받은 userphone 값: {}", userphone);
//   		
//   		boolean result = userService.isPhoneCheck(userphone);
//   		log.info("연락처중복체크 여부: {}", result);
//   		
//   		return result;
//   	}
   	
  
  	@RequestMapping(value = "/phoneCheck", method = RequestMethod.GET)
  	@ResponseBody
  	public String sendSMS(@RequestParam("phone") String userPhoneNumber) { // 휴대폰 문자보내기
  		int randomNumber = (int)((Math.random()* (9999 - 1000 + 1)) + 1000);//난수 생성

  		userService.certifiedPhoneNumber(userPhoneNumber,randomNumber);
  		
  		return Integer.toString(randomNumber);
  	}
  

    //로그인 실패
    @GetMapping("/access_denied")
    public String accessDenied(Model model) {
    	model.addAttribute("fail","아이디와 비밀번호를 확인해주세요.");
        return "/login/login";
    }

    

    //로그인
    @RequestMapping("/user_access")
    public String userAccess(Authentication authentication, HttpServletRequest request) {
    	log.info("test");
    	HttpSession session = request.getSession();
        //Authentication 객체를 통해 유저 정보를 가져올 수 있다. 일반로그인쪽
//(시큐리티 정보예시)	UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	
    	Object obj = (Object)authentication.getPrincipal();
    	System.out.println("authentication : "+authentication);
        if(obj instanceof UsersDTO) {
        	
        UsersDTO usersDTO = (UsersDTO) authentication.getPrincipal();  //userDetail 객체를 가져옴
        System.out.println("로그인부분 usersDTO : "+usersDTO);
        
        
        session.setAttribute("usersDTO", usersDTO);
        return "main";
        }else {
        	//소셜로그인쪽.
        	DefaultOAuth2User usersDTO1 = (DefaultOAuth2User) authentication.getPrincipal();
        	System.out.println("authentication2 : "+authentication);
            System.out.println("로그인부분2 usersDTO1 : "+usersDTO1);
            
           
            Map <String, Object> response = (Map <String, Object>) usersDTO1.getAttribute("response");
            System.out.println("response : "+response);
            
            if(response!=null) {
            	System.out.println("response1 : "+response.get("nickname"));
            	String nickname = (String)response.get("nickname");
            	System.out.println("nickname : "+nickname);
            
            UsersDTO usersDTO = userService.getSocialInfo(nickname);
            
            
            
            
            //usersDTO.setNickname((String)(response.get("nickname")));//로그인과 회원가입이 다름.
//            //usersDTO.setCoupon(3000);
            //usersDTO.setEmail((String)(response.get("email")));  

            System.out.println("usersDTO : "+usersDTO);
            System.out.println("usersDTO.getNickname : "+usersDTO.getNickname());
//            System.out.println("usersDTO.getCoupon : "+usersDTO.getCoupon());
            session.setAttribute("usersDTO", usersDTO);
            return "main";
            }else {
            	
            	Map <String, Object> kakao = (Map <String, Object>) usersDTO1.getAttribute("properties");
            	System.out.println("kakao : "+kakao);
            	System.out.println("kakaonickname : "+kakao.get("nickname"));
            	String nickname = (String)kakao.get("nickname");
            	System.out.println("nickname : "+nickname);
            	
            	UsersDTO usersDTO = userService.getSocialInfo(nickname);
            	System.out.println("kakao usersDTO : "+usersDTO);
            	
            	//usersDTO.setNickname((String)(kakao.get("nickname")));
            	//usersDTO.setCoupon(3000);
        		session.setAttribute("usersDTO", usersDTO);
        		return "main";
            }
        }
        
    }
    
	
	
    
  //아이디찾기 페이지 이동
  	@GetMapping("/findId")
  	public String findId() {
  		return "/login/mn_findId";
  	}
  	
  	
  	//아이디 찾기 작동
  	@RequestMapping(value = "/user/findId", method = RequestMethod.POST)
	@ResponseBody
	public String findId(@RequestParam("name") String name,@RequestParam("phone") String phone) {
		System.out.println("name : "+name);
		System.out.println("phone : "+phone);
		
//		if(userService.findId(name,phone) == null) {
		if(userService.isIdCheck2(name,phone) == true) {
			boolean result1 = userService.isIdCheck2(name,phone);
			System.out.println("result1 : "+result1);
			return "null"; 
		}else {
			return userService.findId(name, phone);
		}
		
	}
  	
  	//비밀번호찾기 페이지 이동
  	@GetMapping("/findPw")
  	public String findPw() {
  		return "/login/mn_findPw";
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
			
			
			String securePw = encoder.encode(dto1.getPw());//회원 비밀번호를 암호화하면 vo객체에 다시 저장
			dto1.setPw(securePw);
				
			userService.updatePwd(dto1);

			result="true";
			

		}else {
			result="false";
		}
		return result;
	}
  	
  	
  	//이용약관 글
  	@GetMapping("/tac")
  	public String tac() {
  		return "/main/termsAndConditions";
  	}
  	
  	//개인정보 처리글
  	@GetMapping("/privacy")
  	public String privacy() {
  		return "/main/privacy";
  	}
  	
  	//위치정보 서비스 글
  	@GetMapping("/locationService")
  	public String locationService() {
  		return "/main/locationService";
  	}
    
}
