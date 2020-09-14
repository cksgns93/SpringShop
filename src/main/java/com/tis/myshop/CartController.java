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
		String str=(n>0)? "��ٱ��� ��� ����":"����";
		String loc=(n>0)? "cartList":"javascript:history.back";
		 
		model.addAttribute("message",str);
		model.addAttribute("loc",loc);
		
		return "msg";
		
		//�α����� ����� ȸ����ȣ�� �����ͼ� �ش� ȸ���� ��ٱ��� ��Ͽ� �߰�
		//return "shop/cartList";
	}
	@RequestMapping(value="/cartList",method=RequestMethod.GET)
	public String cartList(Model model,HttpSession ses) {
		//�α����� ȸ���� ȸ����ȣ �˾Ƴ���
		UserVO loginUser=(UserVO) ses.getAttribute("loginUser");
		int idx=0;
		if(loginUser!=null) {
			idx=loginUser.getIdx();
		}
		//Ư�� ȸ���� ��ٱ��� ��� ��������
		List<CartVO> cList=this.shopSvc.selectCartView(idx);
		//��ٱ��� �Ѿ�,�� ����Ʈ ���ϱ�
		CartVO cvo=this.shopSvc.getCartTotal(idx);
		
		model.addAttribute("cartList",cList);
		if(cvo!=null) {	
		model.addAttribute("cartTotalPrice",cvo.getCartTotalPrice());
		model.addAttribute("cartTotalPoint",cvo.getCartTotalPoint());
		}
		return "shop/cartList";
	}
	//��ٱ��� ����
	//@RequestMapping(value="/cartDel",method=RequestMethod.POST)
	//get�����϶� GetMapping / post�϶� PostMapping
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
	//���� ���� ó��
		this.shopSvc.editCart(cartVo);
	}else if(oqty==0) {
	//���� ó��
		this.shopSvc.delCart(Integer.parseInt(cartVo.getCartNum()));
	}else {
	//������ ������ ���	
		return util.addMsgBack(model,"������ �������� �ȵſ�!!");
	}
	return "redirect:cartList";
	}
}
