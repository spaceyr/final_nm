package kr.co.jhta.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.jhta.dao.PayDAO;
import kr.co.jhta.dto.PayDTO;

@Service
public class PayService {

	@Autowired
	private PayDAO dao;

	public void payAddOne(PayDTO dto2) {
		dao.payAddOne(dto2);	
		//dao.testAdd(dto);
	}

	public List<PayDTO> getPayAll(String nickname) {
		return dao.getPayAll(nickname);
	}

	public void deletePay(int pay_no) {
		dao.deletePay(pay_no);
	}

	

	

}
