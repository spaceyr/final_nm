package kr.co.jhta.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.jhta.dao.ProductDAO;
import kr.co.jhta.dto.ChartDTO;
import kr.co.jhta.dto.PayDTO;
import kr.co.jhta.dto.ProductDTO;
import kr.co.jhta.dto.Rejected_messageDTO;
import kr.co.jhta.dto.ReviewDTO;


@Service
public class ProductService {
	
	int count;
	
	@Autowired
	private ProductDAO dao;
	
	public List<ProductDTO> selectAll(){
		return dao.getAll();
	}
	//검수안한것만
	public List<ProductDTO> selectInsepection(){
		return dao.selectInsepection();
	}
	//검수반려만
	public List<ProductDTO> rejectInsepection(){
		return dao.rejectInsepection();
	}
	
	public List<ProductDTO> selectOneMj(String keyword,String from_date,String to_date,String inspection) {
		return dao.selectOneMj(keyword,from_date, to_date, inspection);
	}
	//반려메시지 불러오기
	public List<Rejected_messageDTO> selectRejectmessage(String nickname) {
		return dao.selectRejectmessage(nickname);
	}
	
	public List<ProductDTO> selectInspection(int inspection) {
		return dao.selectInspection(inspection);
	}
	
	public void insertOne(ProductDTO dto) {
		dao.insertOne(dto);
	}
	
	public List<ProductDTO> selectOne(int cateno) {
		// TODO Auto-generated method stub
		return dao.selectOne(cateno);
	}
	
	public List<ProductDTO> selectList(String contents) {
		// TODO Auto-generated method stub
		return dao.selectList(contents);
	}
	

	//유저당 상품개수조회
	public Integer countProduct(String nickname) {

		return dao.countProduct(nickname);
	}
	//유저당 상품조회수조회
	public Integer countProductReview(String nickname) {

		return dao.countProductReview(nickname);
	}
	//유저당 상품신고수조회
	public Integer countProductReport(String nickname) {
		
		return dao.countProductReport(nickname);
	}
	
	//유저당 상품평점조회
	public float countProductLike(String nickname) {
		
		return dao.countProductLike(nickname);
	}

	public List<ProductDTO> searchList(String title){
		return dao.searchList(title);

	}
	
	//찜한상품만가져오기
	public List<ProductDTO> selectOneJjim(String nickname) {

		return dao.selectOneJjim(nickname);
	}
	
	//검수확인 수정
	public void inspectionmodifyOne(Rejected_messageDTO dto) {
		dao.inspectionmodifyOne(dto);
	}
	
	//검수반려 수정
	public void rejectmodifyOne(Rejected_messageDTO dto) {
		dao.rejectmodifyOne(dto);
	}
	
	//반려테이블 추가
	public void rejectinsertOne(Rejected_messageDTO dto) {
		dao.rejectinsertOne(dto);
	}
	
	//판매상품조회
	public List<PayDTO> salesList(String nickname) {

		return dao.salesList(nickname);
	}
	//월별판매량조회
	public List<ChartDTO> salesListmonth(String nickname) {
		
		return dao.salesListmonth(nickname);
	}
	
	// host프로필수정
	public void hostmodifyOne(String nickname, String email, String phone, String field, String profileimage,
			String id) {
		dao.hostmodifyOne(nickname, email, phone, field, field, id);
	}
	//리뷰전체조회
	public List<ReviewDTO> showAllReview(){
		return dao.showAllReview();
	}
	//리뷰검색조회
	public List<ReviewDTO> selectOneReview(String contents,String writer) {
		return dao.selectOneReview(contents,writer);
	}
	
	/*public List<BoardDTO> selectAll(int startNo, int endNo){
		StartEnd se = new StartEnd(startNo, endNo);
		return dao.readAll(se);
		
	}
	
	public List<pro> selectAll(){
		return dao.getAll();
	}
	
	
	
	public BoardDTO selectOne(int bno) {
		dao.raiseHits(bno);
		return dao.selectOne(bno);
	}
	
	public void modifyOne(BoardDTO dto) {
		dao.modifyOne(dto);
	}
	
	public void deleteOne(int bno) {
		dao.deleteOne(bno);
	}
	
	public int getTotal() {
		return dao.getTotal();
	}*/
}
