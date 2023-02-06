package kr.co.jhta.util;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;

import kr.co.jhta.dto.UsersDTO;
import kr.co.jhta.service.UserService;

public class MailUtil {
	
	@Autowired
	UserService service;
	
	public void sendEmail(UsersDTO dto) throws Exception{
		
		//Mail Server 설정
		String charSet="utf-8";
		String hostSMTP="smtp.gmail.com";
		String hostSMTPid="histudyway@gmail.com";//관리자 이메일 아이디
		String hostSMTPpw="voiicpbrpjplltxk"; //관리자 이메일 비밀번호
		
		//보내는 사람 
		String fromEmail="histudyway@gmail.com"; //보내는 사람 이메일 
		String fromName="newmeet"; //보내는 사람 이름
		
		String subject="NewMeet에서 발송하는 임시 비밀번호입니다"; //메일 제목
		String msg="";
		
		msg +="<div style='margin:20px;'>";
		msg +="<br>";
		msg +="<h3 style='color:#5b90f0;'>";
		msg +=dto.getId() + "님의 임시 비밀번호입니다. <br>로그인 후 비밀번호를 변경해 주세요</h3>";
		msg +="<br>";
		msg +="<p><strong>임시 비밀번호:";
		msg +=dto.getPw() + "</string></p></div>";
		 
		//email전송
		String mailRecipient=dto.getEmail();//받는 사람 이메일 주소
		try {
			//객체 선언
			HtmlEmail mail = new HtmlEmail();
			mail.setDebug(true);
			mail.setCharset(charSet);
			mail.setSSLOnConnect(true);
			mail.setHostName(hostSMTP);
			mail.setSmtpPort(587); 
			mail.setAuthentication(hostSMTPid, hostSMTPpw);
			mail.setStartTLSEnabled(true);
			mail.addTo(mailRecipient,charSet);
			mail.setFrom(fromEmail, fromName, charSet);
			mail.setSubject(subject);
			mail.setHtmlMsg(msg);
			mail.send();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void findPw(HttpServletResponse response,UsersDTO dto) {
		response.setContentType("text/html;charset=utf-8");
		
	}

}
