package com.wda.sc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wda.sc.domain.Criteria;
import com.wda.sc.domain.Paging;
import com.wda.sc.domain.TimelineVO;
import com.wda.sc.service.TimelineService;

import lombok.AllArgsConstructor;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@AllArgsConstructor
@RequestMapping("/app/timeline")
public class mTimelineController {
	private TimelineService timelineservice;

	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value = "/mtimeline", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public JSONObject timeline(Locale locale, Model model,Criteria criteria) {
		int pagenum = criteria.getPagenum();

		Paging page = new Paging();	//최대한 코드를 수정 안하기 위한 기존 페이징
		criteria.setTotalcount(timelineservice.getPageNum());   //전체 게시글 개수를 지정
		criteria.setContentnum(4);
		criteria.setPagenum(pagenum);   //현재 페이지를 페이지 객체에 지정
		criteria.setStartnum(pagenum);   //컨텐츠 시작 번호 지정
		criteria.setEndnum(pagenum);   //컨텐츠 끈 번호 지정 
		criteria.setCurrentblock(pagenum);   //현재 페이지 블록이 몇번인지 현재 페이지 번호 통해 지정
		criteria.setLastblock(criteria.getTotalcount());   //마지막 블록 번호를 전체 게시글 수를 통해 정함
		criteria.prevnext(pagenum);   //현재 페이지 번호로 화살표를 나타낼지 정함
		criteria.setStartPage(criteria.getCurrentblock());   //시작 페이지를 페이지 블록번호로 정함
		criteria.setEndPage(criteria.getLastblock(),criteria.getCurrentblock());   //마지막 페이지를 마지막 페이지 블록과 현재 페이지 블록으로 정함
		page.setEndnum(criteria.getEndnum()+1);	//기존 코드를 유지하기 위해 +1함 (기존은 endnum이 5면 4까지 나온다 )
		page.setStartnum(criteria.getStartnum());

		ArrayList<TimelineVO> result = timelineservice.getList(page);
		System.out.println(result);
		System.out.println(result.size());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mtimelineList", result);
		map.put("criteria", criteria);
		JSONObject json = JSONObject.fromObject(map);

		System.out.println(json);

		return json;
	}

	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value = "/mtimeline.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String mtimeline(@RequestBody Map<String, String> map, TimelineVO vo) throws Exception {
		System.out.println("등록");
		String id = (String)map.get("user_id");
		String content = (String)map.get("content");  
		System.out.println(id); 
		System.out.println(content); 

		vo.setUser_id(id);
		vo.setContent(content);
		int a = timelineservice.insert(vo);
		System.out.println(vo);

		if (a == 0) {
			return "false";
		} else {
			return "success";
			
		}
	}
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value = "/mtimelinedelete.do",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String mtimelinedelete(@RequestBody Map<String, String> map, TimelineVO vo) throws Exception {
		System.out.println("삭제");
		String timeline_n = (String)map.get("timeline_n");

		System.out.println(timeline_n);

		int a = timelineservice.timelinedelete(timeline_n);

		System.out.println(a);
		System.out.println("진짜 됨?");
		if (a != 0) {
			System.out.println("오긴 오냐?");
			return "success";

		} else {
			return "false";

		}
	}

	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value = "/mtimelinemodify.do",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public JSONObject mtimelinemodify(@RequestBody Map<String, String> map, TimelineVO vo) throws Exception {
		System.out.println("수정클릭");
		String content = (String)map.get("content");
		String timeline_n = (String)map.get("timeline_n");
		
		map.put("content", content);
		map.put("timeline_n", timeline_n);
		JSONObject json = JSONObject.fromObject(map);
     
        System.out.println(json);
    
     return json;
  }
	
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value = "/mtimelinemodifyy.do",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String mtimelinemodifyy(@RequestBody Map<String, String> map, TimelineVO vo) throws Exception {
		System.out.println("수정");
		String content = (String)map.get("content");
		String timeline_n = (String)map.get("timeline_n");
		
		vo.setContent(content);
		vo.setTimeline_n(timeline_n);
		
		int a = timelineservice.timelinemodify(vo);
		System.out.println(a);
		
		if (a != 0) {
			return "success";

		} else {
			return "false";

		}
	}

	

}