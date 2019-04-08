package com.wda.sc;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
		System.out.println("현장 추가 컨트롤러입니다.");
		
		int a = usermanageservice.updateuser(vo);
		
		if( a == 0 )  {
			return "false";
		} else {
			return "success";
		}   
	}  
}
