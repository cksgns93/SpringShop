package com.tis.mapper;

import java.util.List;

import com.tis.domain.ProductVO;

public interface ProductMapper {

	//pspec���� ��ǰ���� ��������
	public List<ProductVO> selectByPspec(String pspec);
	public ProductVO selectByPnum(Integer pnum);
}
