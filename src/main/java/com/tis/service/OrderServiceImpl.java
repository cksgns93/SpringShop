package com.tis.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.tis.domain.OrderVO;
import com.tis.domain.UserVO;
import com.tis.mapper.OrderMapper;
import com.tis.mapper.UserMapper;

import lombok.extern.log4j.Log4j;

@Service("orderServiceImpl")
@Log4j
public class OrderServiceImpl implements OrderService {
	
	@Inject
	private OrderMapper orderMapper;
	
	@Inject
	private UserMapper userMapper;

	
	@Override
	public String orderInsert(OrderVO ovo) {
		//1.�ֹ���ȣ ����(������ ���ĺ��빮��3������Ͻú���)
		String onum=makeOnum();
		log.info(onum);
		//2.ovo�� �ֹ���ȣ setting
		ovo.setOnum(onum);
		//3.�ֹ��������� insert
		int n1=this.orderMapper.orderDescInsert(ovo);
		//4.�ֹ���ǰ���� insert
		/*
		 * List<ProductVO> orderList = ovo.getOrderList(); if(orderList!=null) { for
		 * (ProductVO pd : orderList) { pd.setOnum(onum); int n2 =
		 * this.orderMapper.orderProductInsert_old(pd); } }
		 */
		/*oracle�� insert all������ �̿��ؼ� ��ǰ������ �Է�
		 * 
		 * insert all
			  into order_product
			    values(1,'GNH20200918114805',1000,10,3,'apple1.png')
			  into order_product
			    values(2,'GNH20200918114805',1000,10,3,'apple1.png')
			  into order_product
			    values(3,'GNH20200918114805',1000,10,3,'apple1.png')
			select * from dual;
			commit;
		 * */
		Map<String,Object> map=new HashMap<>();
		map.put("onum",onum);
		map.put("orderList", ovo.getOrderList());
		int n2=this.orderMapper.orderProductInsert(map);
		//5.����� ���� insert
		int n3=this.orderMapper.receiverInsert(ovo);
		//6.����� ����Ʈ�� �ִٸ� ȸ���� ���ϸ��� �������� ����
		if(ovo.getOpointUse()>0) {
			this.userMapper.updateMileageDown(ovo);
		}
		//7.ī�� ������� ���� ����Ʈ�� ȸ���� ���ϸ��� ������ ����
		if(ovo.getOpayWay().equals("200")) {
			this.userMapper.updateMileageUp(ovo);
		}
		return onum;
	}
	private String makeOnum() {
		char c1=(char)(Math.random()*26+'A');
		char c2=(char)(Math.random()*26+'A');
		char c3=(char)(Math.random()*26+'A');
		
		Date d= new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		String str =sdf.format(d);
		String onum=String.valueOf(c1)+c2+c3+str;
		return onum;
	}

	@Override
	public List<OrderVO> getOrderDesc(String onum) {
		return this.orderMapper.getOrderDesc(onum);
	}

	@Override
	public List<OrderVO> getUserOrderList(int idx_fk) {
		// TODO Auto-generated method stub
		return null;
	}

}
