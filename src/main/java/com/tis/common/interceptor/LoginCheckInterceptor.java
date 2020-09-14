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
 * -컨트롤러가 실행되기 전에 사전 처리할 작업이 있다면 스프링에서는 인터셉터로 구현한다.
 * 1. 인터셉터 구현하기
 * 	[1] HandlerInterceptor인터페이스를 상속받아 구현하기
 * 	[2] HandlerInterceptorAdapter추상클래스를 상속받아 구현하기
 * 2. servlet-context.xml에 인터셉터 등록하고 매핑 정보 설정하기
 * */

@Log4j
public class LoginCheckInterceptor implements HandlerInterceptor{@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse response, Object handler) //preHandle : 사전
			throws Exception {
		log.info("LoginCheckInterceptor...preHandle()");
		
		HttpSession ses=req.getSession();
		UserVO loginUser=(UserVO)ses.getAttribute("loginUser");
		if(loginUser!=null) return true; //로그인 했다면 true를 반환한다. > controller로 전달됨
		
		//로그인 하지 않았다면 ==>false를 반환
		String str="로그인 해야 이용할 수 있어요";
		String loc=req.getContextPath()+"/index";
		
		String viewName="/WEB-INF/views/msg.jsp";
		
		//req에 저장
		req.setAttribute("message", str);
		req.setAttribute("loc", loc);
		
		RequestDispatcher disp=req.getRequestDispatcher(viewName);
		disp.forward(req, response);
		
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, //postHandle : 사후
			ModelAndView modelAndView) throws Exception {
		log.info("LoginCheckInterceptor...postHandle()");
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)//afterCompletion :완료된 후에
			throws Exception {
		log.info("LoginCheckInterceptor...afterCompletion()");
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

	
}
