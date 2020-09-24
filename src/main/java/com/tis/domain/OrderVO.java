package com.tis.domain;

import java.sql.Date;
import java.util.List;

import lombok.Data;

@Data
public class OrderVO {
	//orderDesc���� property
	private String onum;//�ֹ���ȣ
	private int idx_fk; //ȸ����ȣ
	
	private int ototalPrice;//�ֹ��Ѿ�
	private int ototalPoint;//�ֹ�������Ʈ
	private String opayState;//���һ���(�̰���, �����Ϸ�, �������)
	private String odeliver;//��ۻ���(�����, �����, ��ۿϷ�)
	private int odeliverPrice;//��ۺ�
	private Date orderDate;//�ֹ���¥
	private String orderMemo;//��û����
	private String opayWay;//���ҹ��(100:������ �Ա�, 200:ī�����)
	private int opointUse;//�������Ʈ
	
	
	//orderProduct���� property
	//OrderVO have Products
	private List<ProductVO> orderList;
	
	//receiver���� property
	//OrderVO has Receiver
	private ReceiverVO receiver;//������ ����
	
	
}
