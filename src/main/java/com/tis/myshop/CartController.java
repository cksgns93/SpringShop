package com.tis.myshop;

import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tis.common.util.CommonUtil;
import com.tis.domain.CartVO;
import com.tis.domain.UserVO;
import com.tis.service.ShopService;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/user")
public class CartController {
	
	@Resource(name="shopServiceImpl")
	private ShopService shopSvc;
	
	@Inject
	private CommonUtil util;
	
	@RequestMapping(value="/cartAdd",method=RequestMethod.POST) 
	public String addCart(Model model, HttpSession ses, @RequestParam(defaultValue="0")int pnum,
			@RequestParam(defaultValue="0")int oqty) {
		log.info("shopSvc==="+shopSvc);
		log.info("pnum="+pnum+", oqty="+oqty);
		if(pnum==0||oqty==0) {
			return "redirect:index";
		}
		UserVO loginUser=(UserVO) ses.getAttribute("loginUser");
		int idx=0;
		if(loginUser!=null) {
			idx=loginUser.getIdx();
		}
		
		CartVO cartVo=new CartVO();
		cartVo.setPnum(pnum);
		cartVo.setOqty(oqty);
		cartVo.setIdx(idx); 
		int n=shopSvc.addCart(cartVo);
		String str=(n>0)? "장바구니 담기 성공":"실패";
		String loc=(n>0)? "cartList":"javascript:history.back";
		 
		model.addAttribute("message",str);
		model.addAttribute("loc",loc);
		
		return "msg";
		
		//로그인한 사람의 회원번호를 가져와서 해당 회원의 장바구니 목록에 추가
		//return "shop/cartList";
	}
	@RequestMapping(value="/cartList",method=RequestMethod.GET)
	public String cartList(Model model,HttpSession ses) {
		//로그인한 회원의 회원번호 알아내기
		UserVO loginUser=(UserVO) ses.getAttribute("loginUser");
		int idx=0;
		if(loginUser!=null) {
			idx=loginUser.getIdx();
		}
		//특정 회원의 장바구니 목록 가져오기
		List<CartVO> cList=this.shopSvc.selectCartView(idx);
		//장바구니 총액,총 포인트 구하기
		CartVO cvo=this.shopSvc.getCartTotal(idx);
		
		model.addAttribute("cartList",cList);
		if(cvo!=null) {	
		model.addAttribute("cartTotalPrice",cvo.getCartTotalPrice());
		model.addAttribute("cartTotalPoint",cvo.getCartTotalPoint());
		}
		return "shop/cartList";
	}
	//장바구니 삭제
	//@RequestMapping(value="/cartDel",method=RequestMethod.POST)
	//get맵핑일땐 GetMapping / post일땐 PostMapping
	@PostMapping("/cartDel")
	public String cartDelete(@RequestParam(defaultValue="0") int cartNum) {
		log.info("cartNum==="+cartNum);
		if(cartNum==0) {
			return "redirect:index";
		}
		int n=this.shopSvc.delCart(cartNum);
		
		return "redirect:cartList";
	}
	@PostMapping("/cartEdit")
	public String cartEdit(Model model,@ModelAttribute("cartVo") CartVO cartVo) {
	 //log.info("cartNum="+cartNum+"/ oqty="+oqty);
	log.info("cartVo=="+cartVo);
	int oqty=cartVo.getOqty();
	if(oqty>0) {
	//수량 수정 처리
		this.shopSvc.editCart(cartVo);
	}else if(oqty==0) {
	//삭제 처리
		this.shopSvc.delCart(Integer.parseInt(cartVo.getCartNum()));
	}else {
	//수량이 음수일 경우	
		return util.addMsgBack(model,"수량은 음수여선 안돼요!!");
	}
	return "redirect:cartList";
	}
}
