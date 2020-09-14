package com.shop.service;

import java.util.List;

import com.shop.domain.OrderVO;

public interface OrderService {
	//�ֹ����� ������ �ֹ���ǰ����, ������ ������ insert�ϴ� �޼ҵ�
	//�ֹ����� ���ϸ��� �������� ���� �Ǵ� ���� ó��
	String orderInsert(OrderVO ovo);
	
	//�ֹ���ȣ�� �ֹ� ���������� �������� �޼ҵ�
	List<OrderVO> getOrderDesc(String onum);
	
	//ȸ����ȣ�� ȸ���� �ֹ��� ����� �������� �޼ҵ�
	List<OrderVO> getUserOrderList(int midx_fk);


}
