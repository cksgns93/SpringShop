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
		UserVO user = this.userSvc.getUser(loginUser.getIdx());
		ses.setAttribute("mileage", user.getMileage());
		return "shop/orderSheet";
	}
	
	//주문 처리하는 메소드
	@RequestMapping("/orderAdd")
	public String orderAdd(Model model, HttpSession ses,
			@ModelAttribute("ovo") OrderVO ovo,
			@ModelAttribute("receiver") ReceiverVO receiver) {
		
		log.info("ovo=="+ovo);//주문총액, 총포인트, 배송비..등등
		log.info("receiver=="+receiver);//수령자 정보
		//1.주문한 상품정보 가져오기 (세션에서)
		List<ProductVO> orderList=(List<ProductVO>)ses.getAttribute("orderList");
		//2.주문한 상품정보를 OrderVO에 setting
		ovo.setOrderList(orderList);
		//3.수령자정보를 OrderVO에 setting
		ovo.setReceiver(receiver);
		//4.결제방법에 따라 결제상태값을 설정
		//1) 무통장 입급 (100) => opayState는 미결제
		//2) 카드 결제(200) => opayState는 결제완료
		String opayWay=ovo.getOpayWay();
		if(opayWay.equals("100")) {
			ovo.setOpayState("미결제");
		}else if(opayWay.equals("200")){
			ovo.setOpayState("결제완료");
		}
		ovo.setOdeliver("미배송");
		log.info("ovo>>"+ovo);
		//5.주문정보(주문개요,주문상품,수령자)를 DB에 insert한다.
		String onum = this.orderSvc.orderInsert(ovo);
		
		if(onum!=null) {
			ses.setAttribute("onum", onum);
		}
		//6. 주문이 완료되면 적립금 등의 변동이 있으므로 회원정보를 db에서 다시 가져와 세션에 mileage를 저장한다.
		UserVO user=this.userSvc.getUser(ovo.getIdx_fk());
		if(user!=null) {
			int mileage=user.getMileage();
			log.info("mileage===="+mileage);
			ses.setAttribute("mileage", mileage);
		}
		
		//7. 주문이 완료되면 장바구니에서 해당 상품만 삭제 처리
		return "redirect:orderDetail?onum="+onum;
		
		/*
		 * 8. 주문상세 내역을 보여주는 페이지로 이동한다. 이때 redirect이동해야 함.
		 * forward로 이동하면 새로고침시 이중 주문이 발생할 수 있다.
		 * 따라서 redirect이동해야 한다.
		 * */
	}
	
	@GetMapping("/orderDetail")
	public String orderDetail(Model model, HttpSession ses, @RequestParam(defaultValue="") String onum) {
		log.info("onum==="+onum);
		if(onum.isEmpty()) {
			onum=(String)ses.getAttribute("onum");
		}
		if(onum==null) {
			return util.addMsgBack(model, "잘못 들어온 경로입니다.");
		}
	//주문번호로 상세 주문 내역 가져오기
	List<OrderVO> orderDesc=this.orderSvc.getOrderDesc(onum);
	model.addAttribute("onum",onum); //주문번호
	model.addAttribute("orderDesc",orderDesc); //주문내역
	
	return "shop/orderDesc";
		
}
}
