package com.wda.sc;

import java.util.Locale;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wda.sc.service.CheckboardService;
import com.wda.sc.service.MysensorService;
import com.wda.sc.service.SiteService;

import com.wda.sc.service.TimelineService;


import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class HomeController {
	
	
	private SiteService siteservice;
	private TimelineService timelineservice;
	private CheckboardService checkboardservice;
	private MysensorService mysensorservice;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "login/login";
	}
	
	@RequestMapping("main")
	public String main(Locale locale, Model model) {
		model.addAttribute("sitelist",siteservice.getList());
		model.addAttribute("timelinelist",timelineservice.getList());
<<<<<<< HEAD
=======
<<<<<<< HEAD
=======

>>>>>>> 35ae4c352aa1f96f80fcefb9794157f9db0a61aa
>>>>>>> 185c5dae5376c84af45ae57b8ab1bb6473ccf8be
		return "main";
	}
		
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	public String check(Locale locale, Model model) {
		model.addAttribute("checkboardlist",checkboardservice.getList());
		return "check/check";
	}
	
	@RequestMapping(value = "/sitelist", method = RequestMethod.GET)
	public String sitelist(Locale locale, Model model) {
		model.addAttribute("sitelist",siteservice.getList());
		return "site/sitelist";
	}
	
	@RequestMapping(value = "/checkadd", method = RequestMethod.GET)
	public String checkadd(Locale locale, Model model) {
		return "check/checkadd";
	}
	
	@RequestMapping(value = "/manage", method = RequestMethod.GET)
	public String manage(Locale locale, Model model) {
		return "manage/manage";
	}
	
	@RequestMapping(value = "/timeline", method = RequestMethod.GET)
	public String timeline(Locale locale, Model model) {
	
		return "timeline/timeline";
	}
	
	@RequestMapping(value = "/mysensor", method = RequestMethod.GET)
	public String mysensor(Locale locale, Model model) {
		model.addAttribute("sensorlist",mysensorservice.getList());
		return "mysensor/mysensor";
	}
	
	
	
	
}
