package com.wda.sc;

import java.util.ArrayList;

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
@RequestMapping("/login")
public class LoginController {
	
	private LoginService loginservice;
	
	@RequestMapping(value = "help", method = RequestMethod.GET)
	public String help(Model model) {
		return "login/help";
	}
	
	@RequestMapping(value = "sign", method = RequestMethod.GET)
	public String sign(Model model) {
		return "login/sign";
	}
	
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "login/login";
	}
	
	@RequestMapping("login.do") 
	@ResponseBody
	public String loginCheck(Model model,HttpSession session, @RequestParam String id, @RequestParam String password) {

		ArrayList<memberVO> arr = new ArrayList<memberVO>();
		arr = loginservice.login(id);
		
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

	@RequestMapping(value ="/signup", method = RequestMethod.POST)
	public String signup(memberVO m) {
			
		System.out.println(m);
		int checknum = loginservice.signup(m);
		
		if(checknum == 1) {
			return "login/login";
		}
		else if(checknum == 0) {
			return "loginh/sign";
		}
		
		return "login/sign";
	}
}
