package com.tis.mapper;

import java.util.List;
import java.util.Map;

import com.tis.domain.OrderVO;
import com.tis.domain.ProductVO;

public interface OrderMapper {
	//주문 개요 정보 insert
	int orderDescInsert(OrderVO ovo);
	
	//주문상품 정보 insert
	int orderProductInsert_old(ProductVO pd);
	int orderProductInsert(Map<String,Object> map);
	
	//수령자 정보 insert
	int receiverInsert(OrderVO ovo);
	
	//주문 내역정보 가져오기
	List<OrderVO> getOrderDesc(String onum);
	
	//특정 회원의 주문내역 가져오기(mypage)
	List<OrderVO> getUserOrderList(int idx_fk);

}
