package com.tis.common.interceptor;

import java.lang.reflect.Method;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.tis.domain.UserVO;

import lombok.extern.log4j.Log4j;

@Log4j
public class AdminCheckInterceptor implements HandlerInterceptor {

	//preHandler() �������̵��ϱ�
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		HandlerMethod method = (HandlerMethod)handler;
		Method mobj = method.getMethod();//���� �����Ϸ��� �޼ҵ� ��ü�� ��ȯ
		log.info("Method: "+mobj);
		log.info("Bean: "+method.getBean());
		
		HttpSession ses = req.getSession();
		UserVO user = (UserVO)ses.getAttribute("loginUser");
		
		if(user!=null) {//�α��� �ߴٸ� 
			//�����ڰ� �´��� üũ
			if(!user.getUserid().equals("admin")&&!user.getUserid().equals("admin2")){
				//�����ڰ� �ƴ϶��
				String str="�����ڸ� �̿��� �� �־��";
				String loc=req.getContextPath()+"/index";
				
				String viewName="/WEB-INF/views/msg.jsp";
				
				//req�� ����
				req.setAttribute("message", str);
				req.setAttribute("loc", loc);
				
				RequestDispatcher disp=req.getRequestDispatcher(viewName);
				disp.forward(req, response);
				
				
				return false;
			}
		}
		
		return true;
	}
	//�α����� ����� �����ڰ� �ƴ� ��� �޽��� �����ְ� false�� ��ȯ��� ó��
	
	//servlet-context.xml�� ����ϰ� �����ϱ�
}
