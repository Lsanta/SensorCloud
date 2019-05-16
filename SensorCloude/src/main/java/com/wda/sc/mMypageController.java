package com.wda.sc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wda.sc.domain.CheckBoardVO;
import com.wda.sc.domain.Criteria;
import com.wda.sc.domain.MemberVO;
import com.wda.sc.domain.Paging;
import com.wda.sc.service.CheckboardService;
import com.wda.sc.service.MyPageService;

import lombok.AllArgsConstructor;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@AllArgsConstructor
@RequestMapping("/app/mypage")
public class mMypageController {
	private CheckboardService checkboardservice;
	private MyPageService mypageservice;
	
	//마이페이지 메인
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value ="/mypagemain", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	   public JSONObject mainlist(Locale locale, Model model, Criteria criteria) {
			System.out.println("확인");
		      
	      int pagenum = criteria.getPagenum();
	       
	       	 Paging page = new Paging();   //최대한 코드를 수정 안하기 위한 기존 페이징
	         criteria.setTotalcount(checkboardservice.getPageNum());   //전체 게시글 개수를 지정
	         criteria.setContentnum(5);
	         criteria.setPagenum(pagenum);   //현재 페이지를 페이지 객체에 지정
	         criteria.setStartnum(pagenum);   //컨텐츠 시작 번호 지정
	         criteria.setEndnum(pagenum);   //컨텐츠 끈 번호 지정 
	         criteria.setCurrentblock(pagenum);   //현재 페이지 블록이 몇번인지 현재 페이지 번호 통해 지정
	         criteria.setLastblock(criteria.getTotalcount());   //마지막 블록 번호를 전체 게시글 수를 통해 정함
	         criteria.prevnext(pagenum);   //현재 페이지 번호로 화살표를 나타낼지 정함
	         criteria.setStartPage(criteria.getCurrentblock());   //시작 페이지를 페이지 블록번호로 정함
	         criteria.setEndPage(criteria.getLastblock(),criteria.getCurrentblock());   //마지막 페이지를 마지막 페이지 블록과 현재 페이지 블록으로 정함
	         page.setEndnum(criteria.getEndnum()+1);   //기존 코드를 유지하기 위해 +1함 (기존은 endnum이 5면 4까지 나온다 )
	         page.setStartnum(criteria.getStartnum());
		         
	       ArrayList<CheckBoardVO> result = checkboardservice.getList(page);
	       System.out.println(result);
	       System.out.println(result.size());

	         Map<String, Object> map = new HashMap<String, Object>();
	         map.put("mpcheckList", result);
	         map.put("criteria", criteria);
	         JSONObject json = JSONObject.fromObject(map);
	         
	         System.out.println(json);
	         
	         return json;
	
	   }
	
	//정보수정 전 비밀번호 확인
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping("/mypageconfirmpasswd.do")
	@ResponseBody
	public String ConfirmPasswd(Model model, @RequestParam("password") String password, @RequestBody String confirmid) {
		
		ArrayList<MemberVO> arr = new ArrayList<MemberVO>();
		arr = mypageservice.confirmpasswd(confirmid);

		if (arr.size() != 0) {
			if (arr.get(0).getPassword().equals(password)) {
				return "success";
			}
		}
		return confirmid;
	}

	//내 정보 수정
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value = "/usermodify", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public JSONObject modifymyinfo(@RequestBody String id, Locale locale, Model model) {
		 System.out.println("내정보");
		 
		  ArrayList<MemberVO> result = mypageservice.getInfom(id);
		  System.out.println(result);
	      JSONObject json = new JSONObject();
	      json.put("a",result);
		
	      System.out.println(json);
		
		return json;
	}
	
//	@CrossOrigin(origins = "*", maxAge = 3600)
//	@RequestMapping(value = "/usermodify", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//	@ResponseBody
//	public String updatemyinfo(Locale locale, Model model, MemberVO vo) {
//
//		if (vo.getPassword().equals("") || vo.getName().equals("") || vo.getUser_id().equals("") || vo.getPhone().equals("")) {
//
//			return "false";
//
//		} else {
//			mypageservice.updateuserinfo(vo);
//			return "success";
//		}
//	}
	
	
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value = "/levelup", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String mlevelup(@RequestBody String param) throws Exception {
	System.out.println(param);	
//	List<Map<String,Object>> Map = new ArrayList<Map<String,Object>>();
//	Map = JSONArray.fromObject(param);
		
//	System.out.println(map.get("id"));
//	System.out.println(map.get("mlevel"));

	return "";
	}
	
	
}