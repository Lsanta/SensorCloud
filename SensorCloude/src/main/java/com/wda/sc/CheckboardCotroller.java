package com.wda.sc;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wda.sc.domain.CheckBoardVO;
import com.wda.sc.domain.TimelineVO;
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
	
	
	@RequestMapping(value ="checkadd.do")
	@ResponseBody
		public String timeline(CheckBoardVO vo, HttpSession session, Model model) {
		 String id = (String) session.getAttribute("id");
		/* model.addAttribute("writingInfo",timelineservice.getInfo(id)); */
		 	vo.setUser_id(id);

			int a = Checkboardservice.insertcheckboard(vo);

			
			if( a == 0) {
				return "false";
			} else{
				return "success";
			}
		}

	
	
	@RequestMapping(value= "{board_no}", method = RequestMethod.GET)
	public String checkin(Locale locale,@PathVariable  String board_no, Model model) {
	
		System.out.println("보드넘버=" +board_no);
		
		model.addAttribute("cklist",Checkboardservice.viewgetList(board_no));
		
		System.out.println(Checkboardservice.viewgetList(board_no));
		
		return "check/checkview";
	}
	
}
