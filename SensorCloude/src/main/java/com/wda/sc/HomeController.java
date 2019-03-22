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
		return "login/login";
	}
	
	@RequestMapping(value = "/help", method = RequestMethod.GET)
	public String help(Model model) {
			return "login/help";
	}
	
	@RequestMapping(value = "/sign", method = RequestMethod.GET)
	public String sign(Model model) {
			return "login/sign";
	}
	
	@RequestMapping("main")
	public String main(Locale locale, Model model) {
	
		return "main";
	}
	
	@RequestMapping(value = "/sitelist", method = RequestMethod.GET)
	public String sitelist(Locale locale, Model model) {
	
		return "site/sitelist";
	}
	
	@RequestMapping(value = "/siteadd", method = RequestMethod.GET)
	public String siteadd(Locale locale, Model model) {
	
		return "site/siteadd";
	}
	
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	public String check(Locale locale, Model model) {
	
		return "check/check";
	}
	
	@RequestMapping(value = "/checkadd", method = RequestMethod.GET)
	public String checkadd(Locale locale, Model model) {
	
		return "check/checkadd";
	}
	
	@RequestMapping(value = "/manage", method = RequestMethod.GET)
	public String manage(Locale locale, Model model) {
	
		return "manage/manage";
	}
	
	@RequestMapping(value = "/mypage", method = RequestMethod.GET)
	public String mypage(Locale locale, Model model) {
	
		return "mypage/mypage";
	}
	

	@RequestMapping(value = "/timeline", method = RequestMethod.GET)
	public String timeline(Locale locale, Model model) {
	
		return "timeline/timeline";
	}
	
	@RequestMapping(value = "/mysensor", method = RequestMethod.GET)
	public String mysensor(Locale locale, Model model) {
	
		return "mysensor/mysensor";
	}
	
	@RequestMapping(value = "/modifymypage", method = RequestMethod.GET)
	public String modifymypage(Locale locale, Model model) {
	
		return "mypage/modifymypage";
	}
	
	
	@RequestMapping("/login.do")
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
}
