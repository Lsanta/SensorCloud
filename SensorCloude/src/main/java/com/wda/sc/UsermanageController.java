package com.wda.sc;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.wda.sc.domain.MemberVO;
import com.wda.sc.domain.SiteVO;
import com.wda.sc.service.UsermanageService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/manage")
public class UsermanageController {
	
private UsermanageService usermanageservice;
	
	@RequestMapping(value = "usermodify" + "/{id}", method = RequestMethod.GET)
	public String address(@PathVariable String id, Model model) {
		
		model.addAttribute("userInfo",usermanageservice.getInfo(id));
		return "manage/usermodify";
	}
	
	//사용자 수정(update)
	@RequestMapping(value ="updateuser.do", method = RequestMethod.POST)
	@ResponseBody
	public String insertSite(MemberVO vo) { 
		
		int a = usermanageservice.updateuser(vo);
		
		if( a == 0 )  {
			return "false";
		} else {
			return "success";
		}   
	}  
	/// 승급요청시 요청승급과 유저 아이디를 세션에 저장//levelup에서 호출
	@RequestMapping(value ="userlevelmanage.do", method = RequestMethod.POST)
	@ResponseBody
	public String userlevelup(HttpSession session , @RequestParam(value="userid") String userId , 
			@RequestParam(value="selectlevel") String selectlevel){
		session.setAttribute("selectlevel", selectlevel);
		session.setAttribute("luserId", userId);
		
		System.out.println(selectlevel);
		
		return "success";
		
	}  
	
	
	
}
