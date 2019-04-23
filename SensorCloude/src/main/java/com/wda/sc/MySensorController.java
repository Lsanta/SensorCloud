package com.wda.sc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wda.sc.domain.MysensorVO;
import com.wda.sc.domain.Paging;
import com.wda.sc.domain.Search;
import com.wda.sc.service.MysensorService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/mysensor")
public class MySensorController {

	private MysensorService mysensorservice;

	@RequestMapping(value = "mysensoradd", method = RequestMethod.GET)
	public String address(Locale locale, Model model, HttpSession session, HttpServletResponse response)
			throws IOException {

		int mlevel = (int) session.getAttribute("mlevel");

		if (mlevel < 4) {

			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script langauge='javascript'>");
			out.println("alert('권한이 없습니다.\\n4등급(수정권한)이상이 열람가능합니다'); window.opener.location.reload(); window.close();");
			out.println("</script>");
			
			
		}

		return "mysensor/mysensoradd";
	}

	@RequestMapping(value = "mysensormod", method = RequestMethod.GET)
	public String sensormod(Locale locale, Model model, HttpSession session , HttpServletResponse response) throws IOException {

		int mlevel = (int) session.getAttribute("mlevel");

		if (mlevel < 4) {

			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script langauge='javascript'>");
			out.println("alert('권한이 없습니다.\\n4등급(수정권한)이상이 열람가능합니다'); window.opener.location.reload(); window.close(); ");
			out.println("</script>");
			
		}
		return "mysensor/mysensormod";
	}

	// 보유센서 Insert
	@RequestMapping(value = "mysensoradd.do")
	@ResponseBody
	public String mysensoradd(MysensorVO vo) {
		// 보유센서 추가

		int a = mysensorservice.insertmysensor(vo);

		if (a == 0) {
			return "false";
		} else if (a == 1) {
			return "success";
		}

		return "false";
	}

	// 보유센서 수정
	@RequestMapping(value = "mysensormod.do")
	@ResponseBody
	public String mysensormod(MysensorVO vo) {
		// 보유센서 수정

		int a = mysensorservice.modmysensor(vo);

		if (a == 0) {
			return "false";
		} else if (a == 1) {
			return "success";
		}

		return "false";
	}

	@RequestMapping(value = "/search" + "/{page}" + "/{searchType}" + "/{keyword}", method = RequestMethod.GET)
	public String mysensorSearch(@PathVariable int page, @PathVariable String searchType, @PathVariable String keyword,
			Model model) {

		Paging p = new Paging();
		Search s = new Search();
		ArrayList<MysensorVO> searchArr = new ArrayList<MysensorVO>();
		ArrayList<Integer> arr = new ArrayList<Integer>();
		Map<Object, Object> parm = new HashMap<Object, Object>();

		p.getOnePageBoard(); // 페이지 당 보여지는 데이터 수

		s.setPage(page);
		s.setKeyword(keyword);
		s.setSearchType(searchType);

		searchArr = mysensorservice.mysensorSearch(s);

		System.out.println(searchArr);

		int pageNum = 0;
		int realNum = page;
		p.setTotalNum(searchArr.size());

		if (p.getTotalNum() <= p.getOnePageBoard()) {
			pageNum = 1;
		} else {
			pageNum = p.getTotalNum() / p.getOnePageBoard();
			if (p.getTotalNum() % p.getOnePageBoard() > 0) {
				pageNum = pageNum + 1;
			}
		}

		for (int i = 0; i < pageNum; i++) {
			arr.add(i + 1);
		}

		p.setEndnum((realNum * 10) + 1);
		p.setStartnum(p.getEndnum() - 10);

		parm.put("Paging", p);
		parm.put("Search", s);

		model.addAttribute("pageNum", arr);
		System.out.println("pageNum" + arr);

		model.addAttribute("sensor", mysensorservice.getSearchResult(parm));
		System.out.println("sensor" + mysensorservice.getSearchResult(parm));

		return "/mysensor/mysensor";
	}
}
