package com.tis.mapper;

import java.util.List;
import java.util.Map;

import com.tis.domain.OrderVO;
import com.tis.domain.ProductVO;

public interface OrderMapper {
	//�ֹ� ���� ���� insert
	int orderDescInsert(OrderVO ovo);
	
	//�ֹ���ǰ ���� insert
	int orderProductInsert_old(ProductVO pd);
	int orderProductInsert(Map<String,Object> map);
	
	//������ ���� insert
	int receiverInsert(OrderVO ovo);
	
	//�ֹ� �������� ��������
	List<OrderVO> getOrderDesc(String onum);
	
	//Ư�� ȸ���� �ֹ����� ��������(mypage)
	List<OrderVO> getUserOrderList(int idx_fk);

}
