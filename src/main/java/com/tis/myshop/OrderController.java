package com.tis.myshop;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tis.domain.OrderVO;
import com.tis.domain.ProductVO;
import com.tis.domain.ReceiverVO;
import com.tis.domain.UserVO;
import com.tis.service.OrderService;
import com.tis.service.ShopService;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/user")
@Log4j
public class OrderController {
	
	@Inject 
	private ShopService shopSvc;
	
	@Inject
	private OrderService orderSvc;
	
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
		ses.setAttribute("mileage", loginUser.getMileage());
		
		
		return "shop/orderSheet";
	}
	
	//�ֹ� ó���ϴ� �޼�
	@RequestMapping("/orderAdd")
	public String orderAdd(Model model, HttpSession ses,
			@ModelAttribute("ovo") OrderVO ovo,
			@ModelAttribute("receiver") ReceiverVO receiver) {
		
		log.info("ovo=="+ovo);//�ֹ��Ѿ�, ������Ʈ, ��ۺ�..���
		log.info("receiver=="+receiver);//������ ����
		
		
		
		
		return "redirect:orderDetail?onum=";
	}
}
