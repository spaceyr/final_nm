package kr.co.jhta.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.jhta.dto.ReviewDTO;
import kr.co.jhta.dto.ReviewProductDTO;
import kr.co.jhta.dto.StarContentDTO;
import kr.co.jhta.dto.StartEnd;


@Mapper
@Repository
public interface ReviewDAO {

	List<ReviewProductDTO> getAllR();
//	List<ReviewProductDTO> getAll();
//	void insertReviewOne(StarContentDTO dto);
//	StarContentDTO showStar(StarContentDTO dto2);
//	List<StarContentDTO> showStarCon();

	void addS(StarContentDTO dto2);

	List<ReviewDTO> showR(StartEnd se);

	int getTotal();

}
