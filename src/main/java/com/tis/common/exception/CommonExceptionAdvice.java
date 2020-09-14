package com.tis.common.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.tis.domain.NotUserException;

import lombok.extern.log4j.Log4j;
/*
 * ��� ���ܸ� ó���ϴ� ������ �Ѵ�.
 * @ControllerAdvice������̼��� ���̸� �� Ŭ������ ��ü��
 * ��Ʈ�ѷ����� �߻��ϴ� ���ܸ� ���������� ó���ϴ� Ŭ�������� ����ϴ� ���̴�.
 * */
@ControllerAdvice
@Log4j
public class CommonExceptionAdvice {

	@ExceptionHandler(NotUserException.class)
	public String commonLoginError(NotUserException ex) {
		log.error("�α��� ó�� �� ����: "+ex.getMessage());
		return "member/errorAlert";
	}
	
	@ExceptionHandler(Exception.class)
	public String commonLoginError(Exception ex) {
		log.error("����: "+ex.getMessage());
		//ex.printStackTrace();
		return "member/errorAlert";
	}
}
