package com.tis.domain;

import java.sql.Date;
import java.util.List;

import lombok.Data;

@Data
public class OrderVO {
	//orderDesc관련 property
	private String onum;//주문번호
	private int idx_fk; //회원번호
	
	private int ototalPrice;//주문총액
	private int ototalPoint;//주문총포인트
	private String opayState;//지불상태(미결제, 결제완료, 결제취소)
	private String odeliver;//배송상태(배송전, 배송중, 배송완료)
	private int odeliverPrice;//배송비
	private Date orderDate;//주문날짜
	private String orderMemo;//요청사항
	private String opayWay;//지불방법(100:무통장 입금, 200:카드결제)
	private int opointUse;//사용포인트
	
	
	//orderProduct관련 property
	//OrderVO have Products
	private List<ProductVO> orderList;
	
	//receiver관련 property
	//OrderVO has Receiver
	private ReceiverVO receiver;//수령자 정보
	
	
}
