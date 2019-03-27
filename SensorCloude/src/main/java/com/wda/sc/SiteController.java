package com.wda.sc;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wda.sc.domain.memberVO;
import com.wda.sc.domain.siteVO;
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
	
	@RequestMapping(value = "siterepair", method = RequestMethod.GET)
	public String siterepair(Locale locale, Model model) {
	
		return "site/siterepair";
	}
	
	@RequestMapping(value = "sensoradd", method = RequestMethod.GET)
	public String sensoradd(Locale locale, Model model) {
	
		return "site/sensoradd";
	}
	
	@RequestMapping(value = "download", method = RequestMethod.GET)
	public String download(Locale locale, Model model) {
	
		return "site/download";
	}
	
	@RequestMapping(value = "{site_id}", method = RequestMethod.GET)
	public String siteclick(@PathVariable String site_id, Model model) {
		System.out.println("현장 iD =" + site_id);
		model.addAttribute("siteInfo",siteservice.getSite(site_id));
		model.addAttribute("alarmMember",siteservice.getAlarm_member(site_id));
		
		return "site/sitemain";
	}
	
	@RequestMapping(value ="/siteadd", method = RequestMethod.POST)
	public String siteadd(siteVO s) {
			
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
