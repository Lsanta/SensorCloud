package com.wda.sc;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wda.sc.domain.CheckBoardVO;

import com.wda.sc.service.CheckboardService;
import com.wda.sc.service.SiteService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/checkboard")
public class CheckboardCotroller {
	private CheckboardService Checkboardservice;
	private SiteService siteservice;
	
	@RequestMapping(value = "checkadd", method = RequestMethod.GET)
	public String address(Locale locale, Model model) {
		System.out.println("글쓰기누름");
		model.addAttribute("checksitelist",siteservice.getchecksite());
		return "check/checkadd";
	}
	
	@RequestMapping(value = "/checkmod"+ "/{board_no}", method = RequestMethod.GET)
	public String checkmod(Locale locale, Model model, @PathVariable String board_no,HttpSession session) {
		System.out.println("수정누름");
		
		model.addAttribute("modlist",Checkboardservice.viewgetList(board_no));
		
		String user_id = (String) session.getAttribute("id");
		model.addAttribute("auth",Checkboardservice.checkauthority(user_id));
		model.addAttribute("checksitelist",siteservice.getchecksite());
		return "check/checkadd";
	}
	
	@RequestMapping(value ="checkadd.do", method = RequestMethod.POST)
		public String insertcheckboard(CheckBoardVO vo, HttpSession session,RedirectAttributes rttr) {

		 String id = (String) session.getAttribute("id");
		 	vo.setUser_id(id);
		 	System.out.println("------------------------");
		 	System.out.println("checkboard:" + vo);
		 	if(vo.getAttachList() != null) {
		 		vo.getAttachList().forEach(attach -> System.out.println(attach));
		 	}
		 	System.out.println("-----------------------------");
		 	
		 	Checkboardservice.register(vo);

			rttr.addFlashAttribute("result", vo.getBoard_no());

			return "redirect: /check/1";
		}

	
	//점검이력 content보기
	@RequestMapping(value= "{board_no}", method = RequestMethod.GET)
	public String checkin(Locale locale,@PathVariable  String board_no, Model model ,HttpSession session) {
	
		System.out.println("보드넘버=" +board_no);
		
		//수정클릭시 권한체크 위해 id에 해당하는 m_level을 넘긴다.
		String user_id = (String) session.getAttribute("id");
		model.addAttribute("checkauthority",Checkboardservice.checkauthority(user_id));
		System.out.println(Checkboardservice.checkauthority(user_id));
		
		model.addAttribute("cklist",Checkboardservice.viewgetList(board_no));
		
		System.out.println(Checkboardservice.viewgetList(board_no));
		
		return "check/checkview";
	}	
	
}
