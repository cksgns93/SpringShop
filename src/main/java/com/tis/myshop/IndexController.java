package com.tis.myshop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

//POJO(Plain Old Java Object)��ü�� ��Ʈ�ѷ��� �����.
@Controller
public class IndexController {
	
	@RequestMapping("/top")
	public void showTop() {
		//������� ���ڿ��� ��ȯ���� ���� ���,
		//��û ���� "/top" �տ� ���ξ ���̰�, �ڿ��� ���̾ �ٿ� �̵��Ѵ�.
		//WEB-INF/views/top.js
	}
	@RequestMapping("/foot")
	public void showFoot() {
		
	}
	@RequestMapping("/carousel")
		public void showCarousel() {
	}

	// /index�� ��û�� ���� home()�޼ҵ尡 ����ǰ� index.jsp�� ������
	@RequestMapping(value="/index")
	public String home(Model model) {
		
		model.addAttribute("msg","Form IndexController");
		model.addAttribute("cr","red");
		
		return "index"; //���̸��� ��ȯ
		//WEB-INF/views/index.jsp�� ã�ư�
		
	}
}
