package com.wda.sc;

import java.util.ArrayList;
import java.util.Locale;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wda.sc.domain.memberVO;
import com.wda.sc.service.LoginService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class HomeController {
	
	private LoginService loginservice;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		System.out.println("gd");
		return "login/login";
	}
	
	@RequestMapping(value = "/help", method = RequestMethod.GET)
	public String help(Model model) {
		 	System.out.println("아이디 비밀번호 찾기");
			return "login/help";
	}
	
	@RequestMapping(value = "/sign", method = RequestMethod.GET)
	public String sign(Model model) {
		 	System.out.println("회원가입");
			return "login/sign";
	}
	
	@RequestMapping("main")
	public String main(Locale locale, Model model) {
	
		return "main";
	}
	
	
	@RequestMapping("/login.do")
	@ResponseBody
	public String loginCheck(Model model,HttpSession session, @RequestParam String id, @RequestParam String password) {

		ArrayList<memberVO> arr = new ArrayList<memberVO>();
		arr = loginservice.login(id);
		System.out.println(arr);
		if(arr.size() != 0) {
			if(arr.get(0).getPassword().equals(password)) {
				model.addAttribute("id",arr.get(0).getUser_id());
				session.setAttribute("id", arr.get(0).getUser_id());
				return "success";
			}else {
				return "fail";
			}
		}else {
			return "fail";
		}
		
	}
}
