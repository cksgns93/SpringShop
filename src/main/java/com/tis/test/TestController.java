package com.tis.test;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tis.service.SampleService;

@Controller
public class TestController {
	
	@Autowired //by type���� ������. SampleServiceŸ���� ��ü�� ������ ��������
	@Resource(name="sampleServiceImpl")//by name���� �����Ѵ�. ���� �̸��� sampleServiceImpl����
	//�Ǿ� �ִ� ��ü�� ã�� ��������
	private SampleService sampleService;

	@RequestMapping("/test")
	public String test(Model model) {
		int tabCnt=sampleService.getTableCount();
		
		model.addAttribute("msg","MyShop������ ���̺� ��: "+tabCnt);
		model.addAttribute("cr","green");
		return "index";
	}
}
