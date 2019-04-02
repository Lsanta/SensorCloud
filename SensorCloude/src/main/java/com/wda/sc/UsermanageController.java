package com.wda.sc;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.wda.sc.service.UsermanageService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/manage")
public class UsermanageController {
	
private UsermanageService usermanageservice;
	
	@RequestMapping(value = "usermodify" + "/{id}", method = RequestMethod.GET)
	public String address(@PathVariable String id, Model model) {
		
		model.addAttribute("userInfo",usermanageservice.getInfo(id));
		return "manage/usermodify";
	}
}
