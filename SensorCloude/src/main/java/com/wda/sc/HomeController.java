package com.wda.sc;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wda.sc.service.CheckboardService;
import com.wda.sc.service.MyPageService;
import com.wda.sc.service.MysensorService;
import com.wda.sc.service.SiteService;

import com.wda.sc.service.TimelineService;
import com.wda.sc.service.UsermanageService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class HomeController {
	
	
	private SiteService siteservice;
	private TimelineService timelineservice;
	private CheckboardService checkboardservice;
	private MysensorService mysensorservice;
	private UsermanageService usermanageservice;
	private MyPageService mypageservice;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "login/login";
	}
	
	@RequestMapping("main")
	public String main(Locale locale, Model model) {
		model.addAttribute("sitelist",siteservice.getList());
		model.addAttribute("timelinelist",timelineservice.timedesc());
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
		model.addAttribute("userlist",usermanageservice.getList());
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
	
	@RequestMapping(value = "/mypage", method = RequestMethod.GET)
	public String mypage(Locale locale, Model model,HttpSession session) {
		
		String id = (String)session.getAttribute("id");
		System.out.println(mypageservice.getInfo(id));
		model.addAttribute("userInfo",mypageservice.getInfo(id));
		model.addAttribute("mychecklist",mypageservice.myList(id));
		return "mypage/mypage";
	}
	
	@RequestMapping(value = "/alarmadd", method = RequestMethod.GET)
	public String alarmadd(Locale locale, Model model) {
		
		return "site/alarmadd";
	}
}
