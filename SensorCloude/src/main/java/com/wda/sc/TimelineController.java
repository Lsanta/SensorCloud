package com.wda.sc;

import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.wda.sc.domain.InstallSensorVO;
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

		return "timeline/timeline";
	}
	
	
	
	
	@RequestMapping(value = "/timelinemodify", method = RequestMethod.GET)
	public String timelinemodify(Locale locale, Model model) {

		return "timeline/timelinemodify";
	}
	
	
	@RequestMapping(value = "timeline.do")
	@ResponseBody
	public String timeline(TimelineVO vo, HttpSession session, Model model) {
		String id = (String) session.getAttribute("id");
		vo.setUser_id(id);

		int a = timelineservice.insert(vo);

		if (a == 0) {
			return "false";
		} else {
			return "success";
		}
	}

	@RequestMapping(value = "timelinedelete.do")
	@ResponseBody
	public String timelinedelete(TimelineVO vo) {
		String content = vo.getContent();

		System.out.println(content);

		int a = timelineservice.timelinedelete(content);
		
		System.out.println(a);

		if (a != 0) {

			return "success";

		} else {
			return "false";

		}
	}
	
	@DateTimeFormat
	@RequestMapping(value = "timelinemodify.do")
	@ResponseBody
	public String timelinemodify(TimelineVO vo, HttpSession session) {
		String content = vo.getContent();

	
		System.out.println(content);

		vo.setContent(content);
		System.out.println(vo);
		
		
		int a = timelineservice.timelinemodify(vo);
		System.out.println(a);
		
		if (a > 0) {
			return "success";
		} else {
			return "false";

		}
		
		
	}

}