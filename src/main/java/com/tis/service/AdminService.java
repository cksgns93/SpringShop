package com.tis.service;

import java.util.List;

import com.tis.domain.CategoryVO;
import com.tis.domain.ProductVO;

public interface AdminService {

	int addUpCategory(CategoryVO cvo);

	int addDownCategory(CategoryVO cvo);

	int deleteUpCategory(int upcg_code);

	int deleteDownCategory(int downcg_code);

	public List<CategoryVO> getUpCategory();

	public List<CategoryVO> getDownCategory(int upcg_code);

	int getTotalProduct();
	int addProduct(ProductVO prod);

	List<ProductVO> listProduct(int start, int end);

//	List<OrderVO> getOrderListByMonth(String month);
	void manageOrder(String onum, String colName, String colVal);
//	List<SumVO> getSumYear();
//	List<SumVO> getSumMonth(String year);
	int getUserSumPoint(int midx_fk);
}
