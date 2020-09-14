package com.tis.service;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.tis.domain.CartVO;
import com.tis.domain.ProductVO;
import com.tis.mapper.CartMapper;
import com.tis.mapper.ProductMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ShopServiceImpl implements ShopService {
	
	@Inject 
	private ProductMapper productMapper;

	@Inject
	private CartMapper cartMapper;
	
	@Override
	public List<ProductVO> selectByPspec(String pspec) {	
		return productMapper.selectByPspec(pspec);
	}

	@Override
	public List<ProductVO> selectByCategory(String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductVO selectByPnum(Integer pnum) {
		return this.productMapper.selectByPnum(pnum);
	}

	//서비스 계층 ==>biz logic, 트랜잭션 처리 등등을 수행함
	@Override
	public int addCart(CartVO cartVo) {
		//1. 특정 회원이 장바구니에 추가한 특정 상품이 이미 존재하는 상품인지 여부를 카운트를 세서 가져오자.
		int count=cartMapper.selectCartByPnum(cartVo);
		log.info("count===>"+count);
		int n=0;
		if(count>0) {
			//1_1. 장바구니 테이블에 수량만 수정(update)
			n=cartMapper.updateCartOqty(cartVo);//update문 수행
		}else { //새로 추가하는 상품이라면
			//1_2. 장바구니에 추가(insert)
			n=cartMapper.addCart(cartVo);//insert문 수행
		}
		return n;
	}

	@Override
	public int updateCartQty(CartVO cartVo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int editCart(CartVO cartVo) {
		return this.cartMapper.editCart(cartVo);
	}

	@Override
	public List<CartVO> selectCartView(int midx) {
		return this.cartMapper.selectCartView(midx);
	}

	//장바구니 번호로 특정 상품 삭제처리
	@Override
	public int delCart(int cartNum) {
		return this.cartMapper.delCart(cartNum);
	}

	@Override
	public int delCartAll(CartVO cartVo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delCartOrder(Map<String, Integer> map) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCartCountByIdx(CartVO cartVo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public CartVO getCartTotal(int midx_fk) {
		return this.cartMapper.getCartTotal(midx_fk);
	}

	@Override
	public void delCartByOrder(int midx_fk, int pnum) {
		// TODO Auto-generated method stub

	}

}
