package com.tis.myshop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tis.domain.UserVO;
import com.tis.service.UserService;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class UserController {
	
	@Autowired
	private UserService userSvc;

	@RequestMapping("/userCount")
	public String showUserCosunt(Model model) {
		int count=userSvc.getUserCount();
		model.addAttribute("msg","회원수를 알아봅시다.");
		model.addAttribute("count",count);
		return "index";
	}
	
	@GetMapping("/signup")
	public String joinForm() {
		
		return "member/join";
	}
	
	@GetMapping("/idCheck_old")
	public ResponseEntity<String> idCheck(@RequestParam("userid") String userid){
		log.info("userid==="+userid);
		boolean b=this.userSvc.idCheck(userid);
		//사용가능한 아이디면 true를 반환하고==>1, 이미 사용중인 아이디면 false를 반환 ==>-1
		int n=(b)? 1:-1;
		String result="{\"isUse\":"+n+"}";
		HttpHeaders header=new HttpHeaders();
		header.add("Content-Type","application/json; charset=UTF-8");
		
		ResponseEntity<String> res=new ResponseEntity<String>(result,header,HttpStatus.OK);
		return res;
	}//----------------------------------
	/*스프링에서 JSON데이터를 생성해야 하는 경우
	    * 아래와 같이 @ResponseBody어노테이션을 사용하고
	    * [1] pom.xml에 Jasckson-databind라이브러리를 추가해야 한다.
	    * --------pom.xml----------------------------
	    * <!-- JSON lib -->
	      <dependency>
	         <groupId>com.fasterxml.jackson.core</groupId>
	         <artifactId>jackson-databind</artifactId>
	         <version>2.9.6</version>
	      </dependency>

	      <!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.dataformat/jackson-dataformat-xml -->
	      <dependency>
	         <groupId>com.fasterxml.jackson.dataformat</groupId>
	         <artifactId>jackson-dataformat-xml</artifactId>
	         <version>2.9.6</version>
	      </dependency>
	    * -------------------------------------------
	    * [2] 컨트롤러에서 json데이터를 생성하기 위해
	    * @ResponseBody를 붙인 자료유형을 Map또는 VO 등의
	    * 적절한 객체를 반환타입으로 주면, 위 라이브러리가 알아서
	    * json형태로 변환해준다.
	    * */
	
	@GetMapping("/idCheck")
	public @ResponseBody Map<String,Integer> idCheck2(@RequestParam("userid") String userid){
		
		boolean b=this.userSvc.idCheck(userid);
		int n=(b)? 1:-1;
		Map<String, Integer> map=new HashMap<>();
		map.put("isUse", n);
		return map;
		
	}
	
	@PostMapping("/join")
	public String joinEnd(Model model, @ModelAttribute("user") UserVO user){
		log.info("user=="+user);
		int n=userSvc.createUser(user);
		String str=(n>0)? "회원가입 성공":"가입 실패";
		String loc=(n>0)? "users":"signup";
			
		model.addAttribute("message",str);
		model.addAttribute("loc",loc);
		return "msg";
	}
	@GetMapping("/admin/users")
	public String userList(Model model) {
		int count=this.userSvc.getUserCount();
		
		List<UserVO> users=this.userSvc.listUser();
		model.addAttribute("userList",users);
		model.addAttribute("userCount",count);
		
		return "member/userList";
	}
	@PostMapping("/delete")
	public String userDelete(Model model,@RequestParam(defaultValue="0")int idx) {
		log.info("idx==="+idx);
		this.userSvc.deleteUser(idx); //삭제결과받음
		int n= this.userSvc.deleteUser(idx);
		String str=(n>0)? "회원정보 삭제성공":"삭제 실패";
		model.addAttribute("message",str);
		model.addAttribute("loc","users");
		return "msg";
	}
}

