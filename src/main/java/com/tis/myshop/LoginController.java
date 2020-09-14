package com.tis.myshop;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.tis.domain.NotUserException;
import com.tis.domain.UserVO;
import com.tis.service.UserService;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class LoginController {
	
	@Resource(name="userServiceImpl")
	private UserService userSvc;

	@PostMapping("/login")
	public String loginEnd(HttpSession ses,@ModelAttribute("user") UserVO user) throws NotUserException {
		log.info("user==="+user);
		UserVO loginUser=this.userSvc.loginCheck(user);
		if(loginUser!=null) {
			//���ǿ� �α��� ������ ���� ȸ�������� ��������.
			ses.setAttribute("loginUser", loginUser);
		}
		return "redirect:index";
	}
	@GetMapping("/logout")
	public String logout(HttpSession ses) {
		ses.invalidate();
		return "redirect:index";
	}
	/*
	 * �������� ���� ó�� ���
	 * [1] ��Ʈ�ѷ����� @ExceptionHandler�� �̿��ϴ� �޼ҵ带 �����ϴ� ���
	 * [2] @ControllerAdvice�� �̿��ϴ� ���
	 * [3] @ResponseStatus�� �̿��� Http�����ڵ� ó��
	 * */
	
	/*[1]@ExceptionHandler(NotUserException.class)
	public String loginError(Exception ex) {
		log.error("�α��� �� ���� �߻�: "+ex.toString());
		return "member/errorAlert";
		//WEB-INF/views/member/errorAlert.jsp
	}
	*/
	
}
