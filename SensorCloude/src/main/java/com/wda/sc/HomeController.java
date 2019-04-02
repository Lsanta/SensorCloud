package com.wda.sc;

import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wda.sc.domain.Paging;
import com.wda.sc.domain.SiteVO;
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

	@RequestMapping(value = "/check"+"/{num}", method = RequestMethod.GET)
	public String check(@PathVariable String num, Model model) {
		Paging page = new Paging();

		ArrayList<Integer> arr = new ArrayList<Integer>();
		int realNum = Integer.parseInt(num);
		
		page.setTotalNum(checkboardservice.getPageNum());
		int pageNum = page.getTotalNum()/page.getOnePageBoard();

		for(int i = 0; i < pageNum; i ++) {
			arr.add(i+1);
		}

		page.setEndnum((realNum*10)+1);
		page.setStartnum(page.getEndnum()-10);

		model.addAttribute("pageNum",arr);
		model.addAttribute("checkboardlist",checkboardservice.getList(page));
		System.out.println(checkboardservice.getList(page));
		return "check/check";
	}

	@RequestMapping(value = "/sitelist"+"/{num}", method = RequestMethod.GET)
	public String siteList(@PathVariable String num, Model model) {
		Paging page = new Paging();

		ArrayList<Integer> arr = new ArrayList<Integer>();
		int realNum = Integer.parseInt(num);
		
		page.setTotalNum(siteservice.getPageNum());
		int pageNum = page.getTotalNum()/page.getOnePageBoard();
		
		for(int i = 0; i < pageNum; i ++) {
			arr.add(i+1);
		}
		
		page.setEndnum((realNum*10)+1);
		page.setStartnum(page.getEndnum()-10);

		model.addAttribute("content",siteservice.getContent(page));
		model.addAttribute("pageNum",arr);
		model.addAttribute("sitelist",siteservice.getList());
		System.out.println(siteservice.getContent(page));
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
	
}
