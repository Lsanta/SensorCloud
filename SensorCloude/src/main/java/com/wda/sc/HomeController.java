package com.wda.sc;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wda.sc.domain.memberVO;
import com.wda.sc.service.LoginService;


/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private LoginService loginservice;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		System.out.println("gd");
		return "main";
	}
	
	@RequestMapping("main")
	public String main(Locale locale, Model model) {
	
		return "main";
	}
	
	@RequestMapping("/login.do")
	@ResponseBody
	public String loginCheck(Model model,HttpSession session, @RequestParam String id,@RequestParam String password) {
    
		ArrayList<memberVO> arr = new ArrayList<memberVO>();
		arr = loginservice.login(id);
		
		if(arr.size() != 0) {
			
			if(arr.get(0).getPassword().equals(password)) {
				model.addAttribute("id",arr.get(0).getUserid());
				session.setAttribute("id", arr.get(0).getUserid());
				return "success";
			}else {
				return "fail";
			}
		}else {
			return "fail";
		}
		
	}
}
