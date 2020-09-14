package com.tis.service;

import java.util.List;
import java.util.Map;

import com.tis.domain.CartVO;
import com.tis.domain.ProductVO;

public interface ShopService {
	
	/** ��ǰ���� �޼ҵ�================*/
	
	/*Pspec ���� ��ǰ ���� ��������*/
	public List<ProductVO> selectByPspec(String pspec);
	
	/*ī�װ��� ��ǰ���� ��������*/
	public List<ProductVO> selectByCategory(String code);
	
	/**��ǰ��ȣ�� Ư�� ��ǰ ���� ��������*/
	public ProductVO selectByPnum(Integer pnum);
	
	/**��ٱ��� ���� �޼ҵ�===============*/
	int addCart(CartVO cartVo);//��ٱ��� �߰��ϱ�
	int updateCartQty(CartVO cartVo);//��ٱ��� �߰� ����=>������ ��� ��ǰ�̸� ������ �����ϱ�
	int editCart(CartVO cartVo);// ��ٱ��� �����ϱ�
	List<CartVO> selectCartView(int midx);//Ư�� ȸ���� ��ٱ��� ��Ϻ���
	
	int delCart(int cartNum);
	int delCartAll(CartVO cartVo);
	int delCartOrder(Map<String,Integer>map);
	
	int getCartCountByIdx(CartVO cartVo);
	
	CartVO getCartTotal(int midx_fk);//Ư�� ȸ���� ��ٱ��� �Ѿ�,������Ʈ ���ϱ�

	public void delCartByOrder(int midx_fk, int pnum);
	
	
	/**��ǰ��-���� ���� �޼ҵ�=================*/
	/*int insertReview(PReviewVO rvo);
	List<PReviewVO> getReviewList(int pnum_fk, int start, int end);
	int getReviewCount(int pnum_fk);
	int deleteReview(int ridx);
	PReviewVO getReview(int ridx);
	int updateReview(PReviewVO rvo);*/

	
}



