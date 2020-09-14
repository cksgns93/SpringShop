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
		model.addAttribute("msg","ȸ������ �˾ƺ��ô�.");
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
		//��밡���� ���̵�� true�� ��ȯ�ϰ�==>1, �̹� ������� ���̵�� false�� ��ȯ ==>-1
		int n=(b)? 1:-1;
		String result="{\"isUse\":"+n+"}";
		HttpHeaders header=new HttpHeaders();
		header.add("Content-Type","application/json; charset=UTF-8");
		
		ResponseEntity<String> res=new ResponseEntity<String>(result,header,HttpStatus.OK);
		return res;
	}//----------------------------------
	/*���������� JSON�����͸� �����ؾ� �ϴ� ���
	    * �Ʒ��� ���� @ResponseBody������̼��� ����ϰ�
	    * [1] pom.xml�� Jasckson-databind���̺귯���� �߰��ؾ� �Ѵ�.
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
	    * [2] ��Ʈ�ѷ����� json�����͸� �����ϱ� ����
	    * @ResponseBody�� ���� �ڷ������� Map�Ǵ� VO ����
	    * ������ ��ü�� ��ȯŸ������ �ָ�, �� ���̺귯���� �˾Ƽ�
	    * json���·� ��ȯ���ش�.
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
		String str=(n>0)? "ȸ������ ����":"���� ����";
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
		this.userSvc.deleteUser(idx); //�����������
		int n= this.userSvc.deleteUser(idx);
		String str=(n>0)? "ȸ������ ��������":"���� ����";
		model.addAttribute("message",str);
		model.addAttribute("loc","users");
		return "msg";
	}
}

