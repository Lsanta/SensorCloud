package com.wda.sc;

import java.util.ArrayList;
import java.util.HashMap;
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
import net.sf.json.JSONObject;

@Controller
@AllArgsConstructor
@RequestMapping("/app")
public class mTimelineController {
	private TimelineService timelineservice;
	
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value = "/mtimeline", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	 public JSONObject timeline(Locale locale, Model model,Criteria criteria) {
		 int pagenum = criteria.getPagenum();
		 
		 Paging page = new Paging();	//최대한 코드를 수정 안하기 위한 기존 페이징
         criteria.setTotalcount(timelineservice.getPageNum());   //전체 게시글 개수를 지정
         criteria.setContentnum(5);
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

	public @ResponseBody String mtimeline(@RequestBody Map<String, String> map, TimelineVO vo) throws Exception {
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
/*
 * @RequestMapping(value = "mtimelinemodify.do") public @ResponseBody String
 * mtimelinemodify(@RequestBody Map<String, String> map, TimelineVO vo) throws
 * Exception { System.out.println("수정");
 * 
 * 
 * }
 */