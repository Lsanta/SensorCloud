package com.wda.sc;

import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.wda.sc.domain.TimelineVO;
import com.wda.sc.service.TimelineService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@SessionAttributes("id")
@RequestMapping("/timeline")
public class TimelineController {
	
	private TimelineService timelineservice;
	
	
	 @RequestMapping(value = "timeline", method = RequestMethod.GET) 
	 public String timeline(Locale locale, Model model, HttpSession session) {
		
	  return "timeline/timeline"; }
	 
	
	
	@RequestMapping(value ="timeline.do")
	@ResponseBody
		public String timeline(TimelineVO vo, HttpSession session, Model model) {
		 String id = (String) session.getAttribute("id");
		/* model.addAttribute("writingInfo",timelineservice.getInfo(id)); */
			System.out.println(id);
			System.out.println(vo);
			int a = timelineservice.insert(vo);
			
			System.out.println(a);
			System.out.println("fwfsdfwe");
			
			if( a == 0) {
				System.out.println("1111111");
				return "false";
			} else if( a == 1){
				System.out.println("ddddd");
				return "success";
			}
			System.out.println("asasasas");
			return "false";
		}
}
