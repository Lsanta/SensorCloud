	package com.wda.sc;


import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.wda.sc.domain.MemberVO;
import com.wda.sc.service.LoginService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@SessionAttributes("id")
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
		
		ArrayList<MemberVO> arr = new ArrayList<MemberVO>();
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

	@RequestMapping(value ="/signup", method = RequestMethod.POST)
	public String signup(MemberVO m) {
			
		System.out.println(m);
		int checknum = loginservice.signup(m);
		
		if(checknum == 1) {
			return "login/login";
		}
		else if(checknum == 0) {
			return "login/sign";
		}
		
		return "login/sign";
	}
	
	 @RequestMapping("idFind.do")
	 @ResponseBody
	 public String idCheck(Model model, HttpSession session, @RequestParam String name,@RequestParam String tel) {
		 ArrayList<MemberVO> arr2 = new ArrayList<MemberVO>();	 
		 arr2 = loginservice.idFind(name);
		 
		 System.out.println(arr2.get(0).getUser_id());
		 if(arr2.size() == 0)
			  return "none"; 
		 if(arr2.size() != 0) {
			 if(arr2.get(0).getPhone().equals(tel)) {
				 //model.addAttribute("find_id",arr2.get(0).getUser_id());
				 session.setAttribute("find_id", arr2.get(0).getUser_id());
				 return arr2.get(0).getUser_id(); 
			 } else {
				 return "isN"; 
			 }
		 }
		 return "none";
	 }
}
