package kr.co.jhta.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.jhta.dto.ProductDTO;

/*인터페이스 임플만들필요없이 인터페이스로 바로맵퍼로연결*/
@Repository
@Mapper
public interface ProductDAO {
	
	List<ProductDTO> getAll();
	
	
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
}
