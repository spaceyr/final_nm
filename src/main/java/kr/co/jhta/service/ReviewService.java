package kr.co.jhta.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.jhta.dao.ReviewDAO;
import kr.co.jhta.dto.ProductDTO;
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

//리뷰작성시 상품번호 넘겨받기
	public List<ProductDTO> getOne(int p_no) {
		
		return dao.getOne(p_no);
	}

//호스트별 리뷰 조회	
	public List<ReviewDTO> selectHost(String nickname) {	
		return dao.selectHost(nickname);
	}

// 후기내역전달
	public List<ReviewDTO> getReview(String nickname) {
		return dao.getReview(nickname);
	}

	public void deleteReview(int r_no) {
		dao.deleteReview(r_no);
		
	}


}
