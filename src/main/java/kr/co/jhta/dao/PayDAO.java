package kr.co.jhta.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.jhta.dto.PayDTO;

@Repository
@Mapper
public interface PayDAO {

	void payAddOne(PayDTO dto2);

	List<PayDTO> getPayAll(String nickname);

	void deletePay(int pay_no);



}
