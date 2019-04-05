package com.wda.sc;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wda.sc.service.CheckboardService;
import com.wda.sc.service.SiteService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/checkboard")
public class CheckboardCotroller {
	private CheckboardService Checkboardservice;
	private SiteService siteservice;
	
	@RequestMapping(value = "checkadd", method = RequestMethod.GET)
	public String address(Locale locale, Model model) {
		model.addAttribute("checksitelist",siteservice.getchecksite());
		return "check/checkadd";
	}
	
	
	@RequestMapping(value= "{board_no}", method = RequestMethod.GET)
	public String checkin(Locale locale,@PathVariable  String board_no, Model model) {
	
		System.out.println("보드넘버=" +board_no);
		
		model.addAttribute("cklist",Checkboardservice.viewgetList(board_no));
		
		System.out.println(Checkboardservice.viewgetList(board_no));
		
		return "check/checkview";
	}
	

}
