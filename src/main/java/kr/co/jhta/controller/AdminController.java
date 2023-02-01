package kr.co.jhta.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.jhta.dto.ProductDTO;
import kr.co.jhta.service.ProductService;

@Controller
public class AdminController {
	
	@Autowired
	ProductService service;
	
	
	 LocalDate now = LocalDate.now(); 
	 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
	 String formatedNow =  now.format(formatter);
	 
	
	
	
	@GetMapping("/host")
	public String host(){
		return "/host";
	}
	
	
	@GetMapping("/dashboard")
    public String home(){
        return "dashboard";
    }
	
	@GetMapping("/newmeetForm")
	public String newmeetForm(){
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
		
		List<ProductDTO> list = service.selectOne(keyword,from_date, to_date, inspection);
		
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
	public String manage(){
		return "manage";
	}
	
	@GetMapping("/newmeetInspection")
	public String newmeetInspection(Model model){
		
		List<ProductDTO> list = service.selectAll();
		
		model.addAttribute("list", list);
		
		return "newmeetInspection";
	}
}
