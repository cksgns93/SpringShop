package com.tis.common.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.tis.domain.NotUserException;

import lombok.extern.log4j.Log4j;
/*
 * 모든 예외를 처리하는 역할을 한다.
 * @ControllerAdvice어노테이션을 붙이면 이 클래스의 객체가
 * 컨트롤러에서 발생하는 예외를 전문적으로 처리하는 클래스임을 명시하는 것이다.
 * */
@ControllerAdvice
@Log4j
public class CommonExceptionAdvice {

	@ExceptionHandler(NotUserException.class)
	public String commonLoginError(NotUserException ex) {
		log.error("로그인 처리 중 예외: "+ex.getMessage());
		return "member/errorAlert";
	}
	
	@ExceptionHandler(Exception.class)
	public String commonLoginError(Exception ex) {
		log.error("예외: "+ex.getMessage());
		//ex.printStackTrace();
		return "member/errorAlert";
	}
}
