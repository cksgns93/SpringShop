package com.tis.common.interceptor;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.tis.domain.UserVO;

import lombok.extern.log4j.Log4j;

/*
 * Interceptor
 * -��Ʈ�ѷ��� ����Ǳ� ���� ���� ó���� �۾��� �ִٸ� ������������ ���ͼ��ͷ� �����Ѵ�.
 * 1. ���ͼ��� �����ϱ�
 * 	[1] HandlerInterceptor�������̽��� ��ӹ޾� �����ϱ�
 * 	[2] HandlerInterceptorAdapter�߻�Ŭ������ ��ӹ޾� �����ϱ�
 * 2. servlet-context.xml�� ���ͼ��� ����ϰ� ���� ���� �����ϱ�
 * */

@Log4j
public class LoginCheckInterceptor implements HandlerInterceptor{@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse response, Object handler) //preHandle : ����
			throws Exception {
		log.info("LoginCheckInterceptor...preHandle()");
		
		HttpSession ses=req.getSession();
		UserVO loginUser=(UserVO)ses.getAttribute("loginUser");
		if(loginUser!=null) return true; //�α��� �ߴٸ� true�� ��ȯ�Ѵ�. > controller�� ���޵�
		
		//�α��� ���� �ʾҴٸ� ==>false�� ��ȯ
		String str="�α��� �ؾ� �̿��� �� �־��";
		String loc=req.getContextPath()+"/index";
		
		String viewName="/WEB-INF/views/msg.jsp";
		
		//req�� ����
		req.setAttribute("message", str);
		req.setAttribute("loc", loc);
		
		RequestDispatcher disp=req.getRequestDispatcher(viewName);
		disp.forward(req, response);
		
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, //postHandle : ����
			ModelAndView modelAndView) throws Exception {
		log.info("LoginCheckInterceptor...postHandle()");
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)//afterCompletion :�Ϸ�� �Ŀ�
			throws Exception {
		log.info("LoginCheckInterceptor...afterCompletion()");
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

	
}
