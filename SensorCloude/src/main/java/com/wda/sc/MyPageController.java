package com.wda.sc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wda.sc.domain.MemberFileVO;
import com.wda.sc.domain.MemberVO;
import com.wda.sc.service.CheckboardService;
import com.wda.sc.service.MyPageService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {

	private MyPageService mypageservice;
	private CheckboardService checkboardservice;
	
	@RequestMapping(value = "/levelup", method = RequestMethod.GET)
	public String address(Locale locale, Model model, HttpSession session) {
		String id = (String)session.getAttribute("id");
		model.addAttribute("userInfo",mypageservice.getInfo(id));
		return "mypage/levelup";
	}

	@RequestMapping(value = "modifymypage", method = RequestMethod.GET)
	public String modifymypage(Locale locale, Model model, HttpSession session) {
		String id = (String) session.getAttribute("id");
		model.addAttribute("userInfo", mypageservice.getInfo(id));
		return "mypage/modifymypage";
	}
	
	@RequestMapping(value = "/imgupload", method = RequestMethod.GET)
	public String address(Locale locale, Model model) {
		
		return "mypage/imgupload";
	}

	@RequestMapping(value = "modifymyinfo", method = RequestMethod.GET)
	public String modifymyinfo(Locale locale, Model model, HttpSession session) {
		String id = (String) session.getAttribute("id");
		model.addAttribute("userInfo", mypageservice.getInfo(id));
		return "mypage/modifymyinfo";
	}

	@RequestMapping(value = "mypagemodifymyinfo", method = RequestMethod.POST)
	@ResponseBody
	public String mypagemodifymyinfo(Locale locale, Model model, MemberVO vo) {

		if (vo.getPassword().equals("") || vo.getName().equals("") || vo.getUser_id().equals("") || vo.getPhone().equals("")) {

			return "false";

		} else {
			mypageservice.updateuserinfo(vo);
			return "success";
		}
	}
	
	
	

	@RequestMapping("mypageconfirmpasswd.do")
	@ResponseBody
	public String MyPageConfirmPasswd(Model model, HttpSession session, @RequestParam("password") String password) {

		String confirmid = (String) session.getAttribute("id");

		ArrayList<MemberVO> arr = new ArrayList<MemberVO>();
		arr = mypageservice.confirmpasswd(confirmid);

		if (arr.size() != 0) {
			if (arr.get(0).getPassword().equals(password)) {
				return "success";
			}
		}
		return confirmid;

	}
	@RequestMapping(value = "mypageimage.do", method = RequestMethod.POST)
	public String insertmypageimage(MemberVO member,HttpSession session, RedirectAttributes rttr) {
		
		String id = (String) session.getAttribute("id");
		member.setUser_id(id);
		System.out.println("------------------------");
		System.out.println("mypageimage:" + member);
		if (member.getAttachList() != null) {
			member.getAttachList().forEach(attach -> System.out.println(attach));
		}
		System.out.println("-----------------------------");

		mypageservice.insert(member);

		 rttr.addFlashAttribute("result", member.getUser_id());
		System.out.println("gg");
		return "redirect: /mypage/imagecomplete";
	}
	@RequestMapping(value = "/imagecomplete", method = RequestMethod.GET)
	public String imagecomplete(Locale locale, Model model) {
		
		return "mypage/imagecomplete";
	}

}
