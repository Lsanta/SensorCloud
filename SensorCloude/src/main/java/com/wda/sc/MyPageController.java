package com.wda.sc;

import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wda.sc.domain.memberVO;
import com.wda.sc.service.MyPageModifyService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {
	
	private MyPageModifyService mypagemodifyservice;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String mypage(Locale locale, Model model) {
		return "mypage/mypage";
	}
	
	@RequestMapping(value = "/modifymypage", method = RequestMethod.GET)
	public String modifymypage(Locale locale, Model model) {
	
		return "mypage/modifymypage";
	}
	
	@RequestMapping("mypageconfirmpasswd.do") 
	@ResponseBody
	public String MyPageConfirmPasswd(Model model,HttpSession session, @RequestParam String id, @RequestParam String pass) {
		System.out.println("된다");
		 String confirmid =(String)session.getAttribute(id);
		
		
		ArrayList<memberVO> arr = new ArrayList<memberVO>();
		arr = mypagemodifyservice.confirmpasswd(confirmid);
			
			if(arr.size() !=0) {
				if(arr.get(0).getPassword().equals(pass)) {
					return "success";
				}
			}
			return confirmid;
			
			
			
		
				
		
	}
	
	
	
}
