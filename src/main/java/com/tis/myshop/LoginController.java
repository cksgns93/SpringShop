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
			//세션에 로그인 인증을 받은 회원정보를 저장하자.
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
	 * 스프링의 예외 처리 방법
	 * [1] 컨트롤러에서 @ExceptionHandler를 이용하는 메소드를 구성하는 방법
	 * [2] @ControllerAdvice를 이용하는 방법
	 * [3] @ResponseStatus를 이용한 Http상태코드 처리
	 * */
	
	/*[1]@ExceptionHandler(NotUserException.class)
	public String loginError(Exception ex) {
		log.error("로그인 중 에러 발생: "+ex.toString());
		return "member/errorAlert";
		//WEB-INF/views/member/errorAlert.jsp
	}
	*/
	
}
