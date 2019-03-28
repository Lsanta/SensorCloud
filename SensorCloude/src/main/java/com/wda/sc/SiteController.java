package com.wda.sc;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wda.sc.domain.MemberVO;
import com.wda.sc.domain.SiteVO;
import com.wda.sc.service.SiteService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/site")
public class SiteController {
	
	private SiteService siteservice;
		
	@RequestMapping(value = "/siteadd", method = RequestMethod.GET)
	public String siteadd(Locale locale, Model model) {
	
		return "site/siteadd";
	}
	

	@RequestMapping(value = "sitealarm", method = RequestMethod.GET)
	public String sitealarm(Locale locale, Model model) {
		return "site/sitealarm";
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
		
		return "site/sitealarm";
	}
		
	@RequestMapping(value = "{site_id}" + "/siterepair", method = RequestMethod.GET)
	public String siterepair(@PathVariable String site_id, Model model) {
		System.out.println("수리내역");
		model.addAttribute("siteInfo",siteservice.getSite(site_id));  //현장정보
		model.addAttribute("alarmMember",siteservice.getAlarm_member(site_id));  //연락망
		
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
	
	
}
