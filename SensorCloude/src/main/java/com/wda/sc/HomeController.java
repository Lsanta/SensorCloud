package com.wda.sc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wda.sc.domain.CheckBoardVO;
import com.wda.sc.domain.InstallSensorVO;
import com.wda.sc.domain.MysensorVO;
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
		
		//메인화면 점검이력 제목 substring
		ArrayList<CheckBoardVO> arr = checkboardservice.mainList();
	
		for(int i = 0; i < arr.size(); i++) {
			if(arr.get(i).getTitle().length() > 6) {
				arr.get(i).setTitle(arr.get(i).getTitle().substring(0,6)+"...");
			}
		}
		model.addAttribute("sitelist",siteservice.getList());
		model.addAttribute("timelinelist",timelineservice.timedesc());
		model.addAttribute("mainchecklist",arr);
		return "main";
	}

	@RequestMapping(value = "/check"+"/{num}", method = RequestMethod.GET)
	public String check(@PathVariable String num, Model model) {
		Paging page = new Paging();
		int pageNum = 0;
		ArrayList<Integer> arr = new ArrayList<Integer>();
		int realNum = Integer.parseInt(num);

		page.setTotalNum(checkboardservice.getPageNum());

		System.out.println(page.getTotalNum());
		if(page.getTotalNum() < page.getOnePageBoard()) {
			pageNum = 1;
		}else {
			pageNum = page.getTotalNum()/page.getOnePageBoard();
		}

		for(int i = 0; i < pageNum; i ++) {
			arr.add(i+1);
		}

		page.setEndnum((realNum*10)+1);
		page.setStartnum(page.getEndnum()-10);

		model.addAttribute("pageNum",arr);
		model.addAttribute("checkboardlist",checkboardservice.getList(page));
		return "check/check";
	}

	@RequestMapping(value = "/sitelist"+"/{num}", method = RequestMethod.GET)
	public String siteList(@PathVariable String num, Model model) {
		Paging page = new Paging();
		int pageNum = 0;
		ArrayList<Integer> arr = new ArrayList<Integer>();
		int realNum = Integer.parseInt(num);
		page.setTotalNum(siteservice.getPageNum());

		if(page.getTotalNum() < page.getOnePageBoard() ) {
			pageNum = 1;
		}else {
			pageNum = page.getTotalNum()/page.getOnePageBoard();
		}

		for(int i = 0; i < pageNum; i ++) {
			arr.add(i+1);
		}

		page.setEndnum((realNum*10)+1);
		page.setStartnum(page.getEndnum()-10);

		model.addAttribute("content",siteservice.getContent(page));
		model.addAttribute("pageNum",arr);
		model.addAttribute("sitelist",siteservice.getList());

		return "site/sitelist";
	}


	@RequestMapping(value = "/checkadd", method = RequestMethod.GET)
	public String checkadd(Locale locale, Model model) {
		return "check/checkadd";
	}

	@RequestMapping(value = "/manage"+"/{num}", method = RequestMethod.GET)
	public String manage(@PathVariable String num, Model model) {

		Paging page = new Paging();
		int pageNum = 0;
		ArrayList<Integer> arr = new ArrayList<Integer>();
		int realNum = Integer.parseInt(num);
		page.setTotalNum(usermanageservice.getPageNum());

		if(page.getTotalNum() < page.getOnePageBoard() ) {
			pageNum = 1;
		}else {
			pageNum = page.getTotalNum()/page.getOnePageBoard();
		}

		for(int i = 0; i < pageNum; i ++) {
			arr.add(i+1);
		}

		page.setEndnum((realNum*10)+1);
		page.setStartnum(page.getEndnum()-10);

		model.addAttribute("pageNum",arr);
		model.addAttribute("userlist",usermanageservice.getList(page));
		return "manage/manage";
	}

	@RequestMapping(value = "/timeline", method = RequestMethod.GET)
	public String timeline(Locale locale, Model model) {
		model.addAttribute("timelinelist",timelineservice.getList());
		System.out.println(timelineservice.getList());

		return "timeline/timeline";
	}

	@RequestMapping(value = "/mysensor", method = RequestMethod.GET)
	public String mysensor(Locale locale, Model model) {

		model.addAttribute("sensorlist",mysensorservice.getList());


		return "mysensor/mysensor";
	}

	@RequestMapping(value = "/mypage"+"/{num}", method = RequestMethod.GET)
	public String mypage(@PathVariable String num, Model model,HttpSession session) {

		Object id = (Object)session.getAttribute("id");
		int pageNum=0;

		Paging page = new Paging();
		Map<String, Object> parm = new HashMap<String, Object>();
		ArrayList<Integer> arr = new ArrayList<Integer>();
		int realNum = Integer.parseInt(num);

		page.setTotalNum(mypageservice.getPageNum(id.toString()));

		if(page.getTotalNum() < page.getOnePageBoard() ) {
			pageNum = 1;
		}else {
			pageNum = page.getTotalNum()/page.getOnePageBoard();
		}

		for(int i = 0; i < pageNum; i ++) {
			arr.add(i+1);
		}

		page.setEndnum((realNum*10)+1);
		page.setStartnum(page.getEndnum()-10);

		parm.put("paging", page);
		parm.put("user_id", id);

		model.addAttribute("pageNum",arr);
		model.addAttribute("userInfo",mypageservice.getInfo(id.toString()));
		model.addAttribute("mychecklist",mypageservice.myList(parm));
		return "mypage/mypage";
	}

}

