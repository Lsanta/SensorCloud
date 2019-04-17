package com.wda.sc;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wda.sc.domain.CheckBoardFileVO;
import com.wda.sc.domain.CheckBoardVO;
import com.wda.sc.domain.Paging;
import com.wda.sc.domain.Search;
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
		model.addAttribute("checksitelist", siteservice.getchecksite());
		return "check/checkadd";
	}

	@RequestMapping(value = "checkadd.do", method = RequestMethod.POST)
	public String insertcheckboard(CheckBoardVO vo, HttpSession session, RedirectAttributes rttr) {

		String id = (String) session.getAttribute("id");
		vo.setUser_id(id);
		System.out.println("------------------------");
		System.out.println("checkboard:" + vo);
		if (vo.getAttachList() != null) {
			vo.getAttachList().forEach(attach -> System.out.println(attach));
		}
		System.out.println("-----------------------------");

		Checkboardservice.register(vo);

		rttr.addFlashAttribute("result", vo.getBoard_no());

		return "redirect: /check/1";
	}

	// 점검이력 content보기
	@RequestMapping(value = "{board_no}", method = RequestMethod.GET)
	public String checkin(Locale locale, @PathVariable String board_no, Model model, HttpSession session) {

		System.out.println("보드넘버=" + board_no);

		// 수정클릭시 권한체크 위해 id에 해당하는 m_level을 넘긴다.
		String user_id = (String) session.getAttribute("id");
		model.addAttribute("checkauthority", Checkboardservice.checkauthority(user_id));
		System.out.println(Checkboardservice.checkauthority(user_id));

		model.addAttribute("cklist", Checkboardservice.viewgetList(board_no));
		System.out.println(Checkboardservice.viewgetList(board_no));
		model.addAttribute("siteid", Checkboardservice.getsiteid(board_no));
		model.addAttribute("board_no", board_no);

		System.out.println("사이트아이디=" + Checkboardservice.getsiteid(board_no));
		return "check/checkview";
	}

	// 현장 에서 해당 글을 클릭후 수정버튼을 누르면 글쓰기 폼으로 되돌아가면서 정보 전달
	@RequestMapping(value = "/checkmod" + "/{board_no}" + "/{site_id}", method = RequestMethod.GET)
	public String checkmod(Locale locale, Model model, @PathVariable String board_no, @PathVariable int site_id,
			HttpSession session) {

		// board_no를 통해 점검이력 내용 가져오기 + site_id 넣기
		ArrayList<CheckBoardVO> cvo = Checkboardservice.viewgetList(board_no);
		cvo.get(0).setSite_id(site_id);
		model.addAttribute("modlist", cvo);
		// ID를 통해 권한레벨 가져오기
		String user_id = (String) session.getAttribute("id");
		model.addAttribute("auth", Checkboardservice.checkauthority(user_id));

		// 사이트 이름 등등의 정보
		model.addAttribute("checksitelist", siteservice.getchecksite());

		// 파일 정보
		
		return "check/checkaddInSite";
	}

	@GetMapping(value = "/getAttachList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<CheckBoardFileVO>> getAttachList(int board_no) {
		System.out.println("getAttachList" + board_no);

		return new ResponseEntity<>(Checkboardservice.getAttachList(board_no), HttpStatus.OK);
	}

	/// 점검이력에서 해당글 클릭후 수정버튼을 누를시 글쓰기폼에 해당 데이터 전달
	@RequestMapping(value = "/checkmod2" + "/{board_no}", method = RequestMethod.GET)
	public String checkmod2(Locale locale, Model model, @PathVariable String board_no, HttpSession session) {

		System.out.println(board_no);
		// board_no를 통해 점검이력 내용 가져오기
		model.addAttribute("modlist", Checkboardservice.viewgetList(board_no));

		// ID를 통해 권한레벨 가져오기
		String user_id = (String) session.getAttribute("id");
		model.addAttribute("auth", Checkboardservice.checkauthority(user_id));

		// 사이트 이름,점검이력 제목,점검이력내용 정보 가져오기
		model.addAttribute("checksitelist", siteservice.getchecksite());

		// 현장아이디
		model.addAttribute("siteid", Checkboardservice.getsiteid(board_no));

		return "check/checklistmodify";
	}

	// 현장 에서 해당 글을 클릭후 삭제버튼을 누른 후

	@RequestMapping(value = "/checkdel" + "/{board_no}" + "/{site_id}", method = RequestMethod.GET)
	public String checkdel(Locale locale, Model model, @PathVariable String board_no, @PathVariable String site_id) {
		int num = Integer.parseInt(board_no);
		List<CheckBoardFileVO> attachList = Checkboardservice.getAttachList(num);

		System.out.println("삭제버튼 후 board_no :" + board_no);
		System.out.println("삭제버튼 후 site_id :" + site_id);
		// board_no를 통해 첨부파일 삭제
		int delN = Checkboardservice.filedelete(board_no);

		// board_no를 통해 게시글 삭제
		int delN2 = Checkboardservice.checkboardDelete(board_no);
		deleteFiles(attachList);

		System.out.println("삭제 완료 수리내역화면으로");

		return "redirect: /site/" + site_id + "/siterepair/1";
	}
	
	//점검이력에서 해당 글을 클릭후  삭제버튼을 누른 후
		@RequestMapping(value = "/checkdel2"+ "/{board_no}", method = RequestMethod.GET)
		public String checkdel2(Locale locale, Model model, @PathVariable String board_no) {
			System.out.println("gg");
			int num = Integer.parseInt(board_no);
			List<CheckBoardFileVO> attachList = Checkboardservice.getAttachList(num);
			
		
			
			//board_no를 통해 첨부파일 삭제
			int delN = Checkboardservice.filedelete(board_no);
			
			//board_no를 통해 게시글 삭제
			int delN2 = Checkboardservice.checkboardDelete(board_no);
			
			deleteFiles(attachList);
			
			System.out.println("삭제 완료 점검이력화면으로");
			
			return "redirect: /check/1";
		}


	// 마이페이지에서 해당 글을 클릭후 수정 버튼을 눌렀을 시 글쓰기 폼으로
	@RequestMapping(value = "/checkmod3" + "/{board_no}", method = RequestMethod.GET)
	public String checkmod3(Locale locale, Model model, @PathVariable String board_no, HttpSession session) {

		System.out.println(board_no);
		// board_no를 통해 점검이력 내용 가져오기
		model.addAttribute("modlist", Checkboardservice.viewgetList(board_no));

		// ID를 통해 권한레벨 가져오기
		String user_id = (String) session.getAttribute("id");
		model.addAttribute("auth", Checkboardservice.checkauthority(user_id));

		// 사이트 이름,점검이력 제목,점검이력내용 정보 가져오기
		model.addAttribute("checksitelist", siteservice.getchecksite());

		// 현장아이디
		model.addAttribute("siteid", Checkboardservice.getsiteid(board_no));

		return "mypage/modifychecklist";
	}

	@RequestMapping(value = "/checkdel3" + "/{board_no}", method = RequestMethod.GET)
	public String checkdel3(Locale locale, Model model, @PathVariable String board_no) {		
		// int num = Integer.parseInt(board_no);
		// List<CheckBoardFileVO> attachList = Checkboardservice.getAttachList(num);

		// board_no를 통해 첨부파일 삭제
		int delN = Checkboardservice.filedelete(board_no);

		// board_no를 통해 게시글 삭제
		int delN2 = Checkboardservice.checkboardDelete(board_no);

		//deleteFiles(attachList);

		System.out.println("삭제 완료 마이페이지점검이력화면으로");

		return "redirect: /mypage/1";
	}

	private void deleteFiles(List<CheckBoardFileVO> attachList) {
		if (attachList == null || attachList.size() == 0) {
			return;
		}
		attachList.forEach(attach -> {
			try {
				Path file = Paths.get(
						"C:\\upload\\" + attach.getFile_Path() + "\\" + attach.getUuid() + "_" + attach.getFile_name());
				Files.deleteIfExists(file);
				if (Files.probeContentType(file).startsWith("image")) {
					Path thumbNail = Paths.get("C:\\upload\\" + attach.getFile_Path() + "\\s_" + attach.getUuid() + "_"
							+ attach.getFile_name());
					Files.delete(thumbNail);
				}
			} catch (Exception e) {
				System.out.println("delete file error" + e.getMessage());
			}
		});
	}

	// 수리내역에서 글쓰기 누를시 site_id 전달
	@RequestMapping(value = "/checkadd/" + "{site_id}", method = RequestMethod.GET)
	public String address(Locale locale, Model model, @PathVariable String site_id) {
		model.addAttribute("site_id", site_id);
		model.addAttribute("checksitelist", siteservice.getchecksite());
		return "check/checkaddInSite";
	}

	// 수리내역에서 글쓰기를 누른 후 파일 업로드 및 글쓰기
	@RequestMapping(value = "checkaddInSite.do", method = RequestMethod.POST)
	public String insertcheckboardIn(CheckBoardVO vo, HttpSession session, RedirectAttributes rttr) {
		
		int site_id = vo.getSite_id();
		String id = (String) session.getAttribute("id");
		vo.setUser_id(id);

		System.out.println("------------------------");
		System.out.println("checkboard:" + vo);
		if (vo.getAttachList() != null) {
			vo.getAttachList().forEach(attach -> System.out.println(attach));
		}
		System.out.println("-----------------------------");

		Checkboardservice.register(vo);

		rttr.addFlashAttribute("result", vo.getBoard_no());

		return "redirect: /site/" + site_id + "/siterepair/1";
	}

	
	  //점검이력 검색
	  @RequestMapping(value ="/search" + "/{page}" + "/{searchType}" + "/{keyword}", method = RequestMethod.GET)
	  public String checkSearch(
			  @PathVariable int page, 
			  @PathVariable String searchType, 
			  @PathVariable String keyword, Model model) {
		  
		  Paging p = new Paging();
		  Search s = new Search();
		  ArrayList<CheckBoardVO> searchArr = new ArrayList<CheckBoardVO>();
		  ArrayList<Integer> arr = new ArrayList<Integer>();
		  Map<Object, Object> parm = new HashMap<Object, Object>();
		  
		  s.setPage(page);
		  s.setKeyword(keyword);
		  s.setSearchType(searchType);
		  
		  System.out.println(page); //현재 페이지 번호
		  System.out.println(searchType); //검색 옵션
		  System.out.println(keyword); //검색 키워드
		  
		  searchArr = Checkboardservice.checkSearch(s);
		  
		  System.out.println("체크 검색 :" + searchArr);
		  
		  int pageNum = 0;
		  int realNum = 1;
		  p.setTotalNum(searchArr.size());
		  
		  System.out.println("체크 전체숫자" +p.getTotalNum());
		  
		  if(p.getTotalNum() <= p.getOnePageBoard() ) {
				pageNum = 1;
			}else {
				pageNum = p.getTotalNum()/p.getOnePageBoard();
				if(p.getTotalNum()%p.getOnePageBoard() > 0) {
					pageNum = pageNum + 1;
				}
			}
		  
		  for(int i = 0; i < pageNum; i ++) {
				arr.add(i+1);
			}
		  
		  p.setEndnum((realNum*10)+1);
		  p.setStartnum(p.getEndnum()-10);
		  
		  parm.put("Paging", p);
		  parm.put("Search", s);
		  
		  model.addAttribute("pageNum",arr);
		  model.addAttribute("check",Checkboardservice.getSearchResult(parm));
		  System.out.println("체크 검색 결과 :" + Checkboardservice.getSearchResult(parm));
		  return "check/check";
	  }
}