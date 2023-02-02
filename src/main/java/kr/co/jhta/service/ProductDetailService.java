package kr.co.jhta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.jhta.dao.ProductDetailDAO;
import kr.co.jhta.dto.ProductDTO;

@Service
public class ProductDetailService {
	
	@Autowired
	ProductDetailDAO dao;

	public ProductDTO selectOne(int p_no) {
		
		return dao.getOne(p_no);
	}

	
}
