package kr.co.jhta.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.jhta.dto.ProductDTO;

@Mapper
@Repository
public interface ProductDetailDAO {

	List<ProductDTO> getOne(int p_no);

	ProductDTO payOne(int p_no);

	
}
