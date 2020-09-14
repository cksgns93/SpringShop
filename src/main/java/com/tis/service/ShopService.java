package com.tis.service;

import java.util.List;
import java.util.Map;

import com.tis.domain.CartVO;
import com.tis.domain.ProductVO;

public interface ShopService {
	
	/** 상품관련 메소드================*/
	
	/*Pspec 별로 상품 정보 가져오기*/
	public List<ProductVO> selectByPspec(String pspec);
	
	/*카테고리별 상품정보 가져오기*/
	public List<ProductVO> selectByCategory(String code);
	
	/**상품번호로 특정 상품 정보 가져오기*/
	public ProductVO selectByPnum(Integer pnum);
	
	/**장바구니 관련 메소드===============*/
	int addCart(CartVO cartVo);//장바구니 추가하기
	int updateCartQty(CartVO cartVo);//장바구니 추가 관련=>기존에 담긴 상품이면 수량만 수정하기
	int editCart(CartVO cartVo);// 장바구니 수정하기
	List<CartVO> selectCartView(int midx);//특정 회원의 장바구니 목록보기
	
	int delCart(int cartNum);
	int delCartAll(CartVO cartVo);
	int delCartOrder(Map<String,Integer>map);
	
	int getCartCountByIdx(CartVO cartVo);
	
	CartVO getCartTotal(int midx_fk);//특정 회원의 장바구니 총액,총포인트 구하기

	public void delCartByOrder(int midx_fk, int pnum);
	
	
	/**상품평-리뷰 관련 메소드=================*/
	/*int insertReview(PReviewVO rvo);
	List<PReviewVO> getReviewList(int pnum_fk, int start, int end);
	int getReviewCount(int pnum_fk);
	int deleteReview(int ridx);
	PReviewVO getReview(int ridx);
	int updateReview(PReviewVO rvo);*/

	
}



