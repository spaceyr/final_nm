package kr.co.jhta.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.jhta.dto.ChartDTO;
import kr.co.jhta.dto.PayDTO;
import kr.co.jhta.dto.ProductDTO;
import kr.co.jhta.dto.Rejected_messageDTO;
import kr.co.jhta.dto.ReviewDTO;

/*인터페이스 임플만들필요없이 인터페이스로 바로맵퍼로연결*/
@Repository
@Mapper
public interface ProductDAO {
	//전체리스트
	List<ProductDTO> getAll();
	//검수안된것만 가져오기
	List<ProductDTO> selectInsepection();
	//검수반려된것만 가져오기
	List<ProductDTO> rejectInsepection();
	//반려메시지 불러오기
	public List<Rejected_messageDTO> selectRejectmessage(String nickname);
	
	
	List<ProductDTO> selectOneMj(String keyword,String from_date,String to_date,String inspection);
	
	List<ProductDTO> selectInspection(int inspection);
	
	void insertOne(ProductDTO dto);
	
	List<ProductDTO> selectOne(int cateno);
	
	List<ProductDTO> selectList(String contents);

	
	//	상품개수조회
	public Integer countProduct(String nickname);
	//상품 조회수조회
	public Integer countProductReview(String nickname);
	//상품 신고수조회
	public Integer countProductReport(String nickname);
	//상품 평점조회
	public float countProductLike(String nickname);

	//찜한상품만가져오기
	List<ProductDTO> selectOneJjim(String nickname);


	List<ProductDTO> searchList(String title);
	//검수확인 수정
	void inspectionmodifyOne(Rejected_messageDTO dto);
	//검수반려 수정
	void rejectmodifyOne(Rejected_messageDTO dto);
	//검수반려시 반려테이블에추가
	void rejectinsertOne(Rejected_messageDTO dto);

	//판매상품리스트가져오기
	List<PayDTO> salesList(String nickname);
	//원별판매상량가져오기
	List<ChartDTO> salesListmonth(String nickname);
	
	void hostmodifyOne(String nickname, String email, String phone, String field, String profileimage,String id);
	
	// 전체리스트
	List<ReviewDTO> showAllReview();
	
	//검색리뷰
	List<ReviewDTO> selectOneReview(String contents,String writer);

}
