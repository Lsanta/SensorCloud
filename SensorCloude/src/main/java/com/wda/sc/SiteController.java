package com.wda.sc;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wda.sc.service.SiteService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/site")
public class SiteController {
		
	@RequestMapping(value = "siteadd", method = RequestMethod.GET)
	public String siteadd(Locale locale, Model model) {
	
		return "site/siteadd";
	}
	
	@RequestMapping(value = "sitealarm1", method = RequestMethod.GET)
	public String sitealarm(Locale locale, Model model) {
	
		return "site/sitealarm";
	}
	
	@RequestMapping(value = "{site_id}", method = RequestMethod.GET)
	public String siteclick(@PathVariable String site_id) {
		System.out.println(site_id);
		return "site/sitemain";
	}
	
	
}
