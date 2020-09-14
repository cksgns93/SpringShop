package com.shop.service;

import java.util.List;

import com.shop.domain.OrderVO;

public interface OrderService {
	//주문개요 정보와 주문상품정보, 수령자 정보를 insert하는 메소드
	//주문자의 마일리지 적립금을 차감 또는 적립 처리
	String orderInsert(OrderVO ovo);
	
	//주문번호로 주문 내역정보를 가져오는 메소드
	List<OrderVO> getOrderDesc(String onum);
	
	//회원번호로 회원의 주문한 목록을 가져오는 메소드
	List<OrderVO> getUserOrderList(int midx_fk);


}
