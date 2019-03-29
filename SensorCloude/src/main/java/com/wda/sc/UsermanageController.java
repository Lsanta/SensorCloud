package com.wda.sc;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wda.sc.service.UsermanageService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/manage")
public class UsermanageController {
	
private UsermanageService usermanageservice;
	
	@RequestMapping(value = "usermodify", method = RequestMethod.GET)
	public String address(Locale locale, Model model, HttpSession session) {
		String id = (String)session.getAttribute("id");
		model.addAttribute("userInfo",usermanageservice.getInfo(id));
		return "manage/usermodify";
	}
}
