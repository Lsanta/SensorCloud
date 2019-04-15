package com.wda.sc;

import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	
	//현장 에서 해당 글을 클릭후  수정버튼을 누르면 글쓰기 폼으로 되돌아가면서 정보 전달
	@RequestMapping(value = "/checkmod"+ "/{board_no}" + "/{site_id}", method = RequestMethod.GET)
	public String checkmod(Locale locale, Model model, @PathVariable String board_no, @PathVariable String site_id, HttpSession session) {
		
		//board_no를 통해 점검이력 내용 가져오기 + site_id 넣기
		ArrayList<CheckBoardVO> cvo = Checkboardservice.viewgetList(board_no);
		cvo.get(0).setSite_id(site_id);
		model.addAttribute("modlist",cvo);
		//ID를 통해 권한레벨 가져오기
		String user_id = (String) session.getAttribute("id");
		model.addAttribute("auth",Checkboardservice.checkauthority(user_id));
		
		//사이트 이름 등등의 정보 
		model.addAttribute("checksitelist",siteservice.getchecksite());
		
		return "check/checkadd";
	}
	
	//현장 에서 해당 글을 클릭후  삭제버튼을 누른 후
	@RequestMapping(value = "/checkdel"+ "/{board_no}" + "/{site_id}", method = RequestMethod.GET)
	public String checkdel(Locale locale, Model model, @PathVariable String board_no, @PathVariable String site_id) {
		
		System.out.println("삭제버튼 후 board_no :" + board_no );
		System.out.println("삭제버튼 후 site_id :" + site_id );
		//board_no를 통해 첨부파일 삭제
		int delN = Checkboardservice.filedelete(board_no);
		
		//board_no를 통해 게시글 삭제
		int delN2 = Checkboardservice.checkboardDelete(board_no);
		
		System.out.println("삭제 완료 수리내역화면으로");
		
		return "redirect: /site/"+site_id+"/siterepair/1";
	}
	//수리내역에서 글쓰기 누를시 site_id 전달
	@RequestMapping(value = "/checkadd/" + "{site_id}", method = RequestMethod.GET)
	public String address(Locale locale, Model model, @PathVariable String site_id) {
		model.addAttribute("site_id",site_id);
		model.addAttribute("checksitelist",siteservice.getchecksite());
		return "check/checkadd";
	}
	
}
