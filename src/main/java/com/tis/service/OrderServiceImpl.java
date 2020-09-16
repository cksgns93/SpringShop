package com.tis.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.tis.domain.OrderVO;
import com.tis.mapper.OrderMapper;

@Service("orderServiceImpl")
public class OrderServiceImpl implements OrderService {
	@Inject
	private OrderMapper orderMapper;
	
	@Override
	public String orderInsert(OrderVO ovo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderVO> getOrderDesc(String onum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderVO> getUserOrderList(int idx_fk) {
		// TODO Auto-generated method stub
		return null;
	}

}
