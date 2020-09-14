package com.tis.mapper;

import java.util.List;

import com.tis.domain.ProductVO;

public interface ProductMapper {

	//pspec별로 상품정보 가져오기
	public List<ProductVO> selectByPspec(String pspec);
	public ProductVO selectByPnum(Integer pnum);
}
