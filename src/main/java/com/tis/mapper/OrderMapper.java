package com.tis.mapper;

import java.util.List;

import com.tis.domain.OrderVO;
import com.tis.domain.ProductVO;

public interface OrderMapper {
	//�ֹ� ���� ���� insert
	int orderDescInsert(OrderVO ovo);
	
	//�ֹ���ǰ ���� insert
	int orderProductInsert(List<ProductVO> prod);
	
	//������ ���� insert
	int receiverInsert(OrderVO ovo);
	
	//�ֹ� �������� ��������
	List<OrderVO> getOrderDesc(String onum);
	
	//Ư�� ȸ���� �ֹ����� ��������(mypage)
	List<OrderVO> getUserOrderList(int idx_fk);
}
