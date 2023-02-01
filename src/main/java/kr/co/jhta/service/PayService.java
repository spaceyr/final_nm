package kr.co.jhta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.jhta.dao.PayDAO;
import kr.co.jhta.dto.ProductDTO;

@Service
public class PayService {
	
	@Autowired
	PayDAO dao;

	public ProductDTO selectOne(int p_no) {
		
		return dao.getOne(p_no);
	}

	
}
