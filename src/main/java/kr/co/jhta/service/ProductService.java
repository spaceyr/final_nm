package kr.co.jhta.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.jhta.dao.ProductDAO;
import kr.co.jhta.dto.ProductDTO;


@Service
public class ProductService {
	
	@Autowired
	private ProductDAO dao;
	
	public List<ProductDTO> selectAll(){
		return dao.getAll();
	}
	
	public List<ProductDTO> selectOneMj(String keyword,String from_date,String to_date,String inspection) {
		return dao.selectOneMj(keyword,from_date, to_date, inspection);
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
	
	public List<ProductDTO> searchList(String title){
		return dao.searchList(title);
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
