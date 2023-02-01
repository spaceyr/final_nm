package kr.co.jhta.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.jhta.dto.ProductDTO;

@Mapper
@Repository
public interface PayDAO {

	ProductDTO getOne(int p_no);

	
}
