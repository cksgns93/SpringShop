package com.tis.myshop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tis.domain.ProductVO;
import com.tis.service.ShopService;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class ProductController {
	
	@Autowired
	private ShopService shopSvc;

	@RequestMapping(value="/prodPspec",method=RequestMethod.GET)
	public String productByPspec(Model model,@RequestParam(defaultValue="") String pspec) {	
		//String pspec=req.getParapeter("pspec")
		List<ProductVO> plist=shopSvc.selectByPspec(pspec);
		
		model.addAttribute("hitList",plist);
		return "shop/mallHit";
		// /WEB-INF/views/shop/mallHit.jsp
	}
	//매핑해보기
	//log level: info<debug<warn<error
	@RequestMapping(value="/prodDetail")
	public String productView(Model model,@RequestParam(defaultValue="0") int pnum) {
		log.info("pnum=="+pnum);
		//유효성체크
		if(pnum==0) {
			return "redirect:index";
		}
		
		ProductVO prod=this.shopSvc.selectByPnum(pnum);
		model.addAttribute("prod",prod);
		
		return "shop/productView";
	}
}
