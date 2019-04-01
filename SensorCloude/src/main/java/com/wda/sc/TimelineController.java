package com.wda.sc;

import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wda.sc.domain.TimelineVO;
import com.wda.sc.service.TimelineService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/timeline")
public class TimelineController {
	
	private TimelineService timelineservice;
	
	@RequestMapping(value = "timeline", method = RequestMethod.GET)
	public String timeline(Locale locale, Model model, HttpSession session) {
		String id = (String)session.getAttribute("id");
		model.addAttribute("userInfo",timelineservice.getInfo(id));
		System.out.println(id);
		
		
		return "timeline/timeline";
	}
	
	
	
	@RequestMapping(value ="timeline.do")
	@ResponseBody
		public String timeline(TimelineVO vo) {
			
			int a = timelineservice.submit(vo);
			
			if( a == 0) {
				return "false";
			} else if( a == 1){
				return "success";
			}
			
			return "false";
		}
	
}
