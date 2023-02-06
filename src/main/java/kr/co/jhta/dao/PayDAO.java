package kr.co.jhta.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.jhta.dto.PayDTO;

@Repository
@Mapper
public interface PayDAO {

	void payAddOne(PayDTO dto2);

}
