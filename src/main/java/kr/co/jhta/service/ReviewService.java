package kr.co.jhta.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.jhta.dao.ReviewDAO;
import kr.co.jhta.dto.ReviewDTO;
import kr.co.jhta.dto.ReviewProductDTO;
import kr.co.jhta.dto.StarContentDTO;
import kr.co.jhta.dto.StartEnd;


@Service
public class ReviewService {

	@Autowired
	ReviewDAO dao;

	public List<ReviewProductDTO> getAllReview() {
		
		return dao.getAllR();
	}

	public void addStar(StarContentDTO dto2) {
		dao.addS(dto2);
		
	}

	public List<ReviewDTO> getRd(int startNo, int endNo) {
		StartEnd se = new StartEnd(startNo, endNo);
		return dao.showR(se);
	}

	public int getTotal() {
		return dao.getTotal();
	}
	
//	public List<ReviewProductDTO> getAllReview() {
//		return dao.getAll();
//	}
//
//
//	public void addReviewOne(StarContentDTO dto) {
//		dao.insertReviewOne(dto);
//	}
//
//
//	public StarContentDTO showSC(StarContentDTO dto2) {
//		
//		return dao.showStar(dto2);
//	}
//
//
//	public List<StarContentDTO> showPra() {
//		
//		return dao.showStarCon();
//	}
//


}
