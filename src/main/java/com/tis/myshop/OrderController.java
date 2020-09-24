package com.tis.myshop;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tis.common.util.CommonUtil;
import com.tis.domain.OrderVO;
import com.tis.domain.ProductVO;
import com.tis.domain.ReceiverVO;
import com.tis.domain.UserVO;
import com.tis.service.OrderService;
import com.tis.service.ShopService;
import com.tis.service.UserService;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/user")
@Log4j
public class OrderController {
	
	@Inject 
	private ShopService shopSvc;
	
	@Inject
	private OrderService orderSvc;
	
	@Inject
	private UserService userSvc;
	
	@Inject
	private CommonUtil util;
	
	@RequestMapping("/orderSheet")
	public String orderForm(Model model, HttpSession ses, 
			@RequestParam("opnum") int[] opnum,
			@RequestParam("oqty") int[] oqty) {
		
		log.info("opnum=="+opnum[0]+", oqty=="+oqty[0]);
		//�ֹ��� ��ǰ������ DB���� �������� 
		List<ProductVO> orderList = new ArrayList<>();
		if(opnum!=null &&oqty!=null) {
			for (int i = 0; i < opnum.length; i++) {
				int pnum = opnum[i];
				ProductVO prod = this.shopSvc.selectByPnum(pnum);
				//ProductVO���� pqty(����)==> ���θ� ���� ����
				//������ �ֹ��������� ��������.
				prod.setPqty(oqty[i]);
				orderList.add(prod);//�ֹ���Ͽ� ��ǰ �߰�
			}
		}
		
		model.addAttribute("orderList",orderList);
		//�������� ����
		ses.setAttribute("orderList", orderList);
		//�ֹ����� ���ϸ��� ���� ��������
		UserVO loginUser = (UserVO)ses.getAttribute("loginUser");
		UserVO user = this.userSvc.getUser(loginUser.getIdx());
		ses.setAttribute("mileage", user.getMileage());
		return "shop/orderSheet";
	}
	
	//�ֹ� ó���ϴ� �޼ҵ�
	@RequestMapping("/orderAdd")
	public String orderAdd(Model model, HttpSession ses,
			@ModelAttribute("ovo") OrderVO ovo,
			@ModelAttribute("receiver") ReceiverVO receiver) {
		
		log.info("ovo=="+ovo);//�ֹ��Ѿ�, ������Ʈ, ��ۺ�..���
		log.info("receiver=="+receiver);//������ ����
		//1.�ֹ��� ��ǰ���� �������� (���ǿ���)
		List<ProductVO> orderList=(List<ProductVO>)ses.getAttribute("orderList");
		//2.�ֹ��� ��ǰ������ OrderVO�� setting
		ovo.setOrderList(orderList);
		//3.������������ OrderVO�� setting
		ovo.setReceiver(receiver);
		//4.��������� ���� �������°��� ����
		//1) ������ �Ա� (100) => opayState�� �̰���
		//2) ī�� ����(200) => opayState�� �����Ϸ�
		String opayWay=ovo.getOpayWay();
		if(opayWay.equals("100")) {
			ovo.setOpayState("�̰���");
		}else if(opayWay.equals("200")){
			ovo.setOpayState("�����Ϸ�");
		}
		ovo.setOdeliver("�̹��");
		log.info("ovo>>"+ovo);
		//5.�ֹ�����(�ֹ�����,�ֹ���ǰ,������)�� DB�� insert�Ѵ�.
		String onum = this.orderSvc.orderInsert(ovo);
		
		if(onum!=null) {
			ses.setAttribute("onum", onum);
		}
		//6. �ֹ��� �Ϸ�Ǹ� ������ ���� ������ �����Ƿ� ȸ�������� db���� �ٽ� ������ ���ǿ� mileage�� �����Ѵ�.
		UserVO user=this.userSvc.getUser(ovo.getIdx_fk());
		if(user!=null) {
			int mileage=user.getMileage();
			log.info("mileage===="+mileage);
			ses.setAttribute("mileage", mileage);
		}
		
		//7. �ֹ��� �Ϸ�Ǹ� ��ٱ��Ͽ��� �ش� ��ǰ�� ���� ó��
		return "redirect:orderDetail?onum="+onum;
		
		/*
		 * 8. �ֹ��� ������ �����ִ� �������� �̵��Ѵ�. �̶� redirect�̵��ؾ� ��.
		 * forward�� �̵��ϸ� ���ΰ�ħ�� ���� �ֹ��� �߻��� �� �ִ�.
		 * ���� redirect�̵��ؾ� �Ѵ�.
		 * */
	}
	
	@GetMapping("/orderDetail")
	public String orderDetail(Model model, HttpSession ses, @RequestParam(defaultValue="") String onum) {
		log.info("onum==="+onum);
		if(onum.isEmpty()) {
			onum=(String)ses.getAttribute("onum");
		}
		if(onum==null) {
			return util.addMsgBack(model, "�߸� ���� ����Դϴ�.");
		}
	//�ֹ���ȣ�� �� �ֹ� ���� ��������
	List<OrderVO> orderDesc=this.orderSvc.getOrderDesc(onum);
	model.addAttribute("onum",onum); //�ֹ���ȣ
	model.addAttribute("orderDesc",orderDesc); //�ֹ�����
	
	return "shop/orderDesc";
		
}
}
