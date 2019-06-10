package com.wda.sc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wda.sc.domain.CheckBoardVO;
import com.wda.sc.domain.Criteria;
import com.wda.sc.domain.MemberFileVO;
import com.wda.sc.domain.MemberVO;
import com.wda.sc.domain.Paging;
import com.wda.sc.service.CheckboardService;
import com.wda.sc.service.MyPageService;
import com.wda.sc.service.UsermanageService;

import lombok.AllArgsConstructor;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@AllArgsConstructor
@RequestMapping("/app/mypage")
public class mMypageController {
	private CheckboardService checkboardservice;
	private MyPageService mypageservice;
	private UsermanageService usermanageservice;
	
	//마이페이지 메인
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value ="/mypagemain", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	   public JSONObject mainlist(@RequestBody String id,Locale locale, Model model, Criteria criteria) {
			System.out.println("확인");
			
	      int pagenum = criteria.getPagenum();
	       
	       	 Paging page = new Paging();   //최대한 코드를 수정 안하기 위한 기존 페이징
	         criteria.setTotalcount(mypageservice.getPageNum(id.toString()));   //전체 게시글 개수를 지정
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
		         
	       int result = mypageservice.getPageNum(id.toString());
	       System.out.println(result);
	       //ystem.out.println(result.size());

	         Map<String, Object> map = new HashMap<String, Object>();
	         map.put("mpcheckList", result);
	         map.put("criteria", criteria);
	         JSONObject json = JSONObject.fromObject(map);
	         
	         System.out.println(json);
	         
	         return json;
	
	   }
	
	//정보수정 전 비밀번호 확인
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value = "/mypageconfirmpasswd.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String ConfirmPasswd(@RequestBody String id1, String password1) {
		System.out.println("ID"+id1);
		
		String[] array = id1.split("&");
		String[] idt = array[0].split("=");
		String[] passwordt = array[1].split("=");

		String user_id = idt[1];
		String password = passwordt[1];
		
		System.out.println(user_id);
		System.out.println(password);
		
		ArrayList<MemberVO> arr = new ArrayList<MemberVO>();
		arr = mypageservice.confirmpasswd(user_id);

		System.out.println(arr);
		if (arr.size() != 0) {
			if (arr.get(0).getPassword().equals(password)) {
				return "success";
			}
		}
		return user_id;
	}

	//내 정보 불러오기
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value = "/usermodify", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public JSONObject modifymyinfo(@RequestBody String id1, Locale locale, Model model) {
		  System.out.println("내정보");
		  System.out.println(id1);
		  
		  String[] array = id1.split("&");
		  String[] idt = array[0].split("=");
		  
		  String id = idt[1];
		  
		  System.out.println(id);
		  
		  ArrayList<MemberVO> result = mypageservice.getInfo(id);
		  System.out.println(result);
	      JSONObject json = new JSONObject();
	      json.put("a",result);
		
	      System.out.println(json);
		
		return json;
	}
	
	//내정보 수정
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value = "/updatemyinfo.do")
	@ResponseBody
	public String updatemyinfo(Locale locale, Model model, MemberVO vo) {
		 System.out.println("여기~");

		if (vo.getPassword().equals("") || vo.getName().equals("") || vo.getUser_id().equals("") || vo.getPhone().equals("")) {

			return "false";

		} else {
			mypageservice.updateuserinfo(vo);
			return "success";
		}
	}
	
	//마이페이지 승급요청
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value = "/levelup", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String mlevelup(@RequestBody  Map<String, String> map) throws Exception {
	System.out.println(map);	
//	String id = map.get("id");
//	String mlevel2 = map.get("mlevel");
	
	int i = usermanageservice.mrequestlevel(map);
	System.out.println("결과값" + i);
	
	if( i == 1 ) {
		System.out.println("성공");
		return "success";
		

	} else {
		System.out.println("실패");
		return "false";
	
	}

	}
	
	
	/*
	 * @CrossOrigin(origins = "*" ,maxAge = 3600)
	 * 
	 * @GetMapping(value = "/mgetAttachListmypage", produces =
	 * MediaType.APPLICATION_JSON_UTF8_VALUE)
	 * 
	 * @ResponseBody public ResponseEntity<List<MemberFileVO>>
	 * getAttachListmypage(String user_id) {
	 * System.out.println("getAttachListmypage" + user_id);
	 * 
	 * return new ResponseEntity<>(mypageservice.getAttachListmypage(user_id),
	 * HttpStatus.OK); }
	 */

	
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value = "/mgetAttachListmypage", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ArrayList<MemberFileVO> getAttachListmypage(@RequestBody String user_id, Locale locale, Model model) {
		System.out.println("오나");
		System.out.println("getAttachListmypage" + user_id);
		
		ArrayList<MemberFileVO> arr = (ArrayList<MemberFileVO>) mypageservice.getAttachListmypage(user_id);

		return arr;
	}
	
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value = "/deleteappToken", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String deleteToken(@RequestBody String user_id, Locale locale, Model model) {
		
		
		 int result = mypageservice.deleteappToken(user_id);
		 
		 if ( result == 1) {
			 return "success";
		 } else {
			 return "fail";
		 }
		
	}
	
	
	
	
}