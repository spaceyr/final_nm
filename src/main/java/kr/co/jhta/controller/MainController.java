package kr.co.jhta.controller;

import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.jhta.dto.ProductDTO;
import kr.co.jhta.dto.Product_CategoryDTO;
import kr.co.jhta.service.ProductService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MainController {
	
	
	@Autowired
	ProductService service;

	@GetMapping("/test")
	@ResponseBody
    public String hello(){
        return "Hello Spring Boot";
    
	}
	@GetMapping("/main")
	public String main() {
		return "main";
	}
	
	@GetMapping("/")
	public String main2() {
		return "main";
	}
	

	// 운동 리스트 페이지 
	@GetMapping("/main/sportslist")
	public String sportslist(@RequestParam("cateno")int cateno, Model model) {
		
		List<ProductDTO> list = service.selectOne(cateno);
		log.info("list>>>>>>" + list);
		System.out.println(cateno);
		
		model.addAttribute("list", list);
		return "main/sportslist";
	}
	// 여행 리스트 페이지  
		@GetMapping("/main/triplist")
		public String triplist(@RequestParam("cateno")int cateno, Model model) {
			
			List<ProductDTO> list = service.selectOne(cateno);
			log.info("list>>>>>>" + list);
			System.out.println(cateno);
			
			model.addAttribute("list", list);
			return "main/triplist";
		}
		// 자기계발 리스트 페이지  
		@GetMapping("/main/myselflist")
		public String myselflist(@RequestParam("cateno")int cateno, Model model) {
			
			List<ProductDTO> list = service.selectOne(cateno);
			log.info("list>>>>>>" + list);
			System.out.println(cateno);
			
			model.addAttribute("list", list);
			return "main/myselflist";
		}
		// 문화생활 리스트 페이지  
		@GetMapping("/main/artlist")
		public String artlist(@RequestParam("cateno")int cateno, Model model) {
			
			List<ProductDTO> list = service.selectOne(cateno);
			log.info("list>>>>>>" + list);
			System.out.println(cateno);
			
			model.addAttribute("list", list);
			return "main/artlist";
		}
		// 모임 리스트 페이지  
		@GetMapping("/main/grouplist")
		public String grouplist(@RequestParam("cateno")int cateno, Model model) {
			
			List<ProductDTO> list = service.selectOne(cateno);
			log.info("list>>>>>>" + list);
			System.out.println(cateno);
			
			model.addAttribute("list", list);
			return "main/grouplist";
		}
		
		// 상세 리스트 페이지
		@GetMapping("/main/productListDetail")
		public String productDetailList(@RequestParam("contents")String contents, Model model) {
			
			List<ProductDTO> list2 = service.selectList(contents);
			
			log.info("list2>>>>>>" + list2);
			model.addAttribute("list2",list2);
			return "main/productListDetail";
		}
		
		// 검색 페이지
		@GetMapping("/main/searchOk")
		public String searchOk(@RequestParam("title")String title, Model model) {
			
			List<ProductDTO> list3 = service.searchList(title);
			log.info("list3>>>>>>>>>>" + list3);
			model.addAttribute("list3",list3);
			return "main/searchOk";
		} 
		
	
}
