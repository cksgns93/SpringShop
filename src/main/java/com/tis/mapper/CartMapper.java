package com.tis.mapper;

import java.util.List;

import com.tis.domain.CartVO;

public interface CartMapper {
	int addCart(CartVO cartVo); //장바구니에 추가
	int updateCartOqty(CartVO cartVo);//장바구니에 이미 담긴 상품을 추가하면 수량만 증가	
	int selectCartByPnum(CartVO cartVo);
	List<CartVO> selectCartView(int idx);//특정 회원의 장바구니 목록 가져오기
	CartVO getCartTotal(int idx); 
	int delCart(int cartNum); //장바구니 특정 상품 삭제
	int editCart(CartVO cartVo); //장바구니 수량 수정
	
}
