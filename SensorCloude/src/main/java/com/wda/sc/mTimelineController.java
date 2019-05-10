package com.wda.sc;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wda.sc.domain.MemberVO;
import com.wda.sc.domain.TimelineVO;
import com.wda.sc.service.TimelineService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/app")
public class mTimelineController {
	private TimelineService timelineservice;
	
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value = "/mtimeline", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	 public ArrayList<TimelineVO> timeline(Locale locale, Model model) {
		 ArrayList<TimelineVO> result = timelineservice.getListm();
		 System.out.println("짠");
		 System.out.println(result);
		 return result;
	
	}
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value = "/mtimeline.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)

	public @ResponseBody String mtimeline(@RequestBody Map<String, String> map, TimelineVO vo) throws Exception {
		System.out.println("등록");
		String id = (String)map.get("user_id");
		String content = (String)map.get("content");      
		System.out.println(id); 
		System.out.println(content); 
	    
	    vo.setUser_id(id);
		int a = timelineservice.insert(vo);
		System.out.println(vo);

		if (a == 0) {
			return "false";
		} else {
			return "success";
		}
	}
	
	@RequestMapping(value = "mtimelinedelete.do")
	public @ResponseBody String mtimelinedelete(@RequestBody Map<String, String> map, TimelineVO vo) throws Exception {
		System.out.println("삭제");
		String id = (String)map.get("user_id");
		String content = (String)map.get("content");      
		System.out.println(id); 
		System.out.println(content);

		int a = timelineservice.timelinedelete(content);

		System.out.println(a);

		if (a != 0) {

			return "success";

		} else {
			return "false";

		}
	}
}