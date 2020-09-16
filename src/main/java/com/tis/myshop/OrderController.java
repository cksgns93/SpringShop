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
		//주문한 상품정보를 DB에서 가져오기 
		List<ProductVO> orderList = new ArrayList<>();
		if(opnum!=null &&oqty!=null) {
			for (int i = 0; i < opnum.length; i++) {
				int pnum = opnum[i];
				ProductVO prod = this.shopSvc.selectByPnum(pnum);
				//ProductVO에는 pqty(수량)==> 쇼핑몰 보유 수량
				//수량을 주문수량으로 변경하자.
				prod.setPqty(oqty[i]);
				orderList.add(prod);//주문목록에 상품 추가
			}
		}
		
		model.addAttribute("orderList",orderList);
		//세연에도 저장
		ses.setAttribute("orderList", orderList);
		
		//주문자의 마일리지 점수 가져오기
		UserVO loginUser = (UserVO)ses.getAttribute("loginUser");
		ses.setAttribute("mileage", loginUser.getMileage());
		
		
		return "shop/orderSheet";
	}
	
	//주문 처리하는 메소
	@RequestMapping("/orderAdd")
	public String orderAdd(Model model, HttpSession ses,
			@ModelAttribute("ovo") OrderVO ovo,
			@ModelAttribute("receiver") ReceiverVO receiver) {
		
		log.info("ovo=="+ovo);//주문총액, 총포인트, 배송비..등등
		log.info("receiver=="+receiver);//수령자 정보
		
		
		
		
		return "redirect:orderDetail?onum=";
	}
}
