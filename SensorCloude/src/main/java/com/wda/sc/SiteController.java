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
import org.springframework.web.bind.annotation.ResponseBody;
import com.wda.sc.domain.AlarmMemberVO;
import com.wda.sc.domain.AlarmVO;
import com.wda.sc.domain.MemberVO;
import com.wda.sc.domain.Paging;
import com.wda.sc.domain.SiteVO;
import com.wda.sc.service.SiteService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/site")
public class SiteController {
	
	private SiteService siteservice;
	
	@RequestMapping(value = "/address", method = RequestMethod.GET)
	public String address(Locale locale, Model model) {
	
		return "site/address";
	}
		
	@RequestMapping(value = "/siteadd", method = RequestMethod.GET)
	public String siteadd(Locale locale, Model model) {
	
		return "site/siteadd";
	}
	

	@RequestMapping(value = "{site_id}", method = RequestMethod.GET)
	public String siteclick(@PathVariable String site_id, Model model) {
		System.out.println("현장 iD =" + site_id);
		model.addAttribute("siteInfo",siteservice.getSite(site_id));
		model.addAttribute("alarmMember",siteservice.getAlarm_member(site_id));	
		return "site/sitemain";

	}
	
	
	@RequestMapping(value = "{site_id}" + "/sitealarm", method = RequestMethod.GET)
	public String sitealarm(@PathVariable String site_id, Model model) {
		System.out.println("알람페이지");
		model.addAttribute("siteInfo",siteservice.getSite(site_id));
		model.addAttribute("alarmMember",siteservice.getAlarm_member(site_id));
		model.addAttribute("alarm",siteservice.getAlarm(site_id)); //알람 내용 정보
		
		return "site/sitealarm";
	}
		
	@RequestMapping(value = "{site_id}" + "/siterepair", method = RequestMethod.GET)
	public String siterepair(@PathVariable String site_id, Model model) {
		System.out.println("수리내역");
		model.addAttribute("siteInfo",siteservice.getSite(site_id));  //현장정보
		model.addAttribute("checkboardlist",siteservice.sitecheck(site_id));// 수리내역
		model.addAttribute("alarmMember",siteservice.getAlarm_member(site_id));  //연락망
		System.out.println(siteservice.sitecheck(site_id));
		return "site/siterepair";
	}

	@RequestMapping(value = "{site_id}" + "/sensoradd", method = RequestMethod.GET)
	public String sensoradd(@PathVariable String site_id, Model model) {
		System.out.println("센서추가");
		model.addAttribute("siteInfo",siteservice.getSite(site_id));  //현장정보
		model.addAttribute("alarmMember",siteservice.getAlarm_member(site_id)); //연락망
		
		return "site/sensoradd";
	}
	
	@RequestMapping(value = "{site_id}" + "/download", method = RequestMethod.GET)
	public String download(@PathVariable String site_id, Model model) {
		System.out.println("스크립트다운로드");
		model.addAttribute("siteInfo",siteservice.getSite(site_id)); //현장정보
		model.addAttribute("alarmMember",siteservice.getAlarm_member(site_id)); //연락망
		
		return "site/download";
	}	

	@RequestMapping(value ="/siteadd", method = RequestMethod.POST)
	public String siteadd(SiteVO s) {
			
		System.out.println(s);
		int checknum = siteservice.siteadd(s);
		
		if(checknum == 1) {
			return "site/sitelist";
		}
		else if(checknum == 0) {
			return null;
		}
		
		return "site/sitelist";
	}
		
	// 알림 Insert
	@RequestMapping(value ="alarmMemberadd.do")
	@ResponseBody
	public String alarmMemberadd(AlarmMemberVO vo) {
		//연락망 추가 폼을 이용한 추가
		
		int a = siteservice.insertAlarmMember(vo);
		
		if( a == 0) {
			return "false";
		} else if( a == 1){
			return "success";
		}
		
		return "false";
	}
	
	@RequestMapping(value ="alarmadd.do")
	@ResponseBody
	public String alarmadd(AlarmVO vo, HttpSession session) {
		//연락망 추가 폼을 이용한 추가
		String user = (String) session.getAttribute("id");
		vo.setSend_user(user);
		
		int a = siteservice.insertAlarm(vo);
		
		if( a == 0) {
			return "false";
		} else if( a == 1){
			return "success";
		}
		
		return "false";
	}
	
}
