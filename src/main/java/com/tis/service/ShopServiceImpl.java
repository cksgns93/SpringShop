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

	//���� ���� ==>biz logic, Ʈ����� ó�� ����� ������
	@Override
	public int addCart(CartVO cartVo) {
		//1. Ư�� ȸ���� ��ٱ��Ͽ� �߰��� Ư�� ��ǰ�� �̹� �����ϴ� ��ǰ���� ���θ� ī��Ʈ�� ���� ��������.
		int count=cartMapper.selectCartByPnum(cartVo);
		log.info("count===>"+count);
		int n=0;
		if(count>0) {
			//1_1. ��ٱ��� ���̺� ������ ����(update)
			n=cartMapper.updateCartOqty(cartVo);//update�� ����
		}else { //���� �߰��ϴ� ��ǰ�̶��
			//1_2. ��ٱ��Ͽ� �߰�(insert)
			n=cartMapper.addCart(cartVo);//insert�� ����
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

	//��ٱ��� ��ȣ�� Ư�� ��ǰ ����ó��
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
