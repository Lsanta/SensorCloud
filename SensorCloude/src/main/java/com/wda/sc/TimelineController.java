package com.wda.sc;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wda.sc.domain.timelineVO;
import com.wda.sc.service.TimelineService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/timeline")
public class TimelineController {
	
	private TimelineService timelineservice;
	
	@RequestMapping(value ="submit", method = RequestMethod.POST)
	public String submit(timelineVO m) {
		
		ArrayList<timelineVO> arr = new ArrayList<timelineVO>();
		System.out.println(m);
		int checknum = timelineservice.submit(m);
		
		if(checknum == 1) {
			return "";
		}
		else if(checknum == 0) {
			return "";
		}
		
		return "";
	}

}
