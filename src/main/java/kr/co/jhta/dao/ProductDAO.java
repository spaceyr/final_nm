package kr.co.jhta.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.jhta.dto.ProductDTO;
import kr.co.jhta.dto.Rejected_messageDTO;

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
	
	
	List<ProductDTO> selectOneMj(String keyword,String from_date,String to_date,String inspection);
	
	List<ProductDTO> selectInspection(int inspection);
	
	void insertOne(ProductDTO dto);
	
	List<ProductDTO> selectOne(int cateno);
	
	List<ProductDTO> selectList(String contents);

	
	//	상품개수조회
	public int countProduct(String nickname);
	//상품 조회수조회
	public int countProductReview(String nickname);
	//상품 신고수조회
	public int countProductReport(String nickname);
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


}
