package com.tis.mapper;

import java.util.List;

import com.tis.domain.CartVO;

public interface CartMapper {
	int addCart(CartVO cartVo); //��ٱ��Ͽ� �߰�
	int updateCartOqty(CartVO cartVo);//��ٱ��Ͽ� �̹� ��� ��ǰ�� �߰��ϸ� ������ ����	
	int selectCartByPnum(CartVO cartVo);
	List<CartVO> selectCartView(int idx);//Ư�� ȸ���� ��ٱ��� ��� ��������
	CartVO getCartTotal(int idx); 
	int delCart(int cartNum); //��ٱ��� Ư�� ��ǰ ����
	int editCart(CartVO cartVo); //��ٱ��� ���� ����
	
}
