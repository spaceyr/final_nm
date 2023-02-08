package kr.co.jhta.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.jhta.dto.ProductDTO;
import kr.co.jhta.dto.ReviewDTO;
import kr.co.jhta.dto.ReviewProductDTO;
import kr.co.jhta.dto.StarContentDTO;
import kr.co.jhta.dto.StartEnd;


@Mapper
@Repository
public interface ReviewDAO {

	List<ReviewProductDTO> getAllR();

	void addS(StarContentDTO dto2);

	List<ReviewDTO> showR(StartEnd se);

	int getTotal();

	List<ProductDTO> getOne(int p_no);

//호스트별 리뷰 조회
	List<ReviewDTO> selectHost(String nickname);

//후기내역전달
	List<ReviewDTO> getReview(String nickname);

	void deleteReview(int r_no);

}
