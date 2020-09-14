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

	//preHandler() 오버라이드하기
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		HandlerMethod method = (HandlerMethod)handler;
		Method mobj = method.getMethod();//현재 실행하려는 메소드 객체를 반환
		log.info("Method: "+mobj);
		log.info("Bean: "+method.getBean());
		
		HttpSession ses = req.getSession();
		UserVO user = (UserVO)ses.getAttribute("loginUser");
		
		if(user!=null) {//로그인 했다면 
			//관리자가 맞는지 체크
			if(!user.getUserid().equals("admin")&&!user.getUserid().equals("admin2")){
				//관리자가 아니라면
				String str="관리자만 이용할 수 있어요";
				String loc=req.getContextPath()+"/index";
				
				String viewName="/WEB-INF/views/msg.jsp";
				
				//req에 저장
				req.setAttribute("message", str);
				req.setAttribute("loc", loc);
				
				RequestDispatcher disp=req.getRequestDispatcher(viewName);
				disp.forward(req, response);
				
				
				return false;
			}
		}
		
		return true;
	}
	//로그인한 사람이 관리자가 아닐 경우 메시지 보여주고 false를 반환토록 처리
	
	//servlet-context.xml에 등록하고 설정하기
}
