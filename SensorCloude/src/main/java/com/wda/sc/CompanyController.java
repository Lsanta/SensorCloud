package com.wda.sc;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wda.sc.domain.CompanyVO;
import com.wda.sc.domain.MysensorVO;
import com.wda.sc.domain.Paging;
import com.wda.sc.domain.Search;
import com.wda.sc.service.CompanyService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/company")
public class CompanyController {
	private CompanyService companyservice;
	
	@RequestMapping(value = ""+"/{num}", method = RequestMethod.GET)
	public String companyPage(@PathVariable String num, Locale locale, Model model ,HttpServletResponse response,HttpSession session) throws IOException {


		int mlevel = (int) session.getAttribute("mlevel");


		if (mlevel != 6) {

			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script langauge='javascript'>");
			out.println("alert('권한이 없습니다.\\n3등급(쓰기권한)이상이 열람가능합니다');history.go(-1);");
			out.println("</script>");

		}
		// 회사 테이블 + 페이징
		Paging page = new Paging();
		ArrayList<Integer> arr=null;
		Map<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();

		int pageNum = 0;
		int mapNum=0;
		int sendPageNum=0;
		int realNum = Integer.parseInt(num);
		
		// 회사의 총 갯수 가져오기
		page.setTotalNum(companyservice.getPageNum());
		System.out.println("회사테이블 안에 내용" + companyservice.getPageNum());

		if(page.getTotalNum() <= page.getOnePageBoard() ) {
			pageNum = 1;
		}else {
			pageNum = page.getTotalNum()/page.getOnePageBoard();
			if(page.getTotalNum()%page.getOnePageBoard() > 0) {
				pageNum = pageNum + 1;
			}
		}

		if(pageNum%5 != 0) {
			mapNum=pageNum/5+1;
		}else {
			mapNum=pageNum/5;
		}

		for(int i=0; i<mapNum; i++) {
			arr = new ArrayList<Integer>();
			for(int j=0; j<5; j++) {

				if((i*5)+j+1 > pageNum) {
					break;
				}

				arr.add((i*5)+j+1);
			}
			map.put(i,arr);
		}

		sendPageNum = (realNum-1)/5;

		page.setEndnum((realNum*10)+1);
		page.setStartnum(page.getEndnum()-10);

		model.addAttribute("lastNum", pageNum);
		model.addAttribute("pageNum",map.get(sendPageNum));
		model.addAttribute("companylist",companyservice.getList(page));
		System.out.println("lastNum : " + pageNum);
		System.out.println("pageNum : " + map.get(sendPageNum));
		System.out.println("확인" + companyservice.getList(page));
		if(realNum > pageNum) {
			System.out.println("pageNum : " + pageNum);
			return "redirect:/company/"+pageNum;
		}
		model.addAttribute("depth0","메인화면");
		model.addAttribute("depth1","협력사관리");


		return "company/company";
	}
	@RequestMapping(value = "companyadd", method = RequestMethod.GET)
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

		return "company/companyadd";
	}

	@RequestMapping(value = "companymod", method = RequestMethod.GET)
	public String sensormod(Locale locale, Model model, HttpSession session , HttpServletResponse response) throws IOException {

		int mlevel = (int) session.getAttribute("mlevel");

		if (mlevel < 4) {

			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script langauge='javascript'>");
			out.println("alert('권한이 없습니다.\\n4등급(수정권한)이상이 열람가능합니다'); window.opener.location.reload(); window.close(); ");
			out.println("</script>");
			
		}
		return "company/companymod";
	}
	
		// 협력사 추가
		@RequestMapping(value = "/companyadd.do")
		@ResponseBody
		public String companyadd(CompanyVO vo) {
			// 보유센서 추가

			int a = companyservice.insertCompany(vo);

			System.out.println("추가됨");
			if (a == 0) {
				return "false";
			} else if (a == 1) {
				return "success";
			}

			return "false";
		}

		// 보유센서 수정
		@RequestMapping(value = "companymod.do")
		@ResponseBody
		public String mysensormod(CompanyVO vo) {
			// 보유센서 수정

			int a = companyservice.modCompany(vo);

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
			ArrayList<CompanyVO> searchArr = new ArrayList<CompanyVO>();
			ArrayList<Integer> arr = new ArrayList<Integer>();
			Map<Object, Object> parm = new HashMap<Object, Object>();
			Map<Integer, ArrayList<Integer>> map = new HashMap<Integer,ArrayList<Integer>>();

			p.getOnePageBoard(); // 페이지 당 보여지는 데이터 수

			s.setPage(page);
			s.setKeyword(keyword);
			s.setSearchType(searchType);

			searchArr = companyservice.companySearch(s);

			System.out.println(searchArr);

			int pageNum = 0;
			int mapNum=0;
			int sendPageNum=0;
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

			if(pageNum%5 != 0) {
				mapNum=pageNum/5+1;
			}else {
				mapNum=pageNum/5;
			}

			for(int i=0; i<mapNum; i++) {
				arr = new ArrayList<Integer>();
				for(int j=0; j<5; j++) {
					
					if((i*5)+j+1 > pageNum) {
						break;
					}
					
					arr.add((i*5)+j+1);
				}
				map.put(i,arr);
			}

			sendPageNum = (realNum-1)/5;

			p.setEndnum((realNum * 10) + 1);
			p.setStartnum(p.getEndnum() - 10);

			parm.put("Paging", p);
			parm.put("Search", s);
			
			model.addAttribute("lastNum", pageNum);
			model.addAttribute("pageNum", map.get(sendPageNum));
			System.out.println("pageNum" + arr);

			model.addAttribute("company", companyservice.CompanySearchResult(parm));
			System.out.println("company" + companyservice.CompanySearchResult(parm));
			
			model.addAttribute("depth0","메인화면");
			model.addAttribute("depth1","협력사관리");

			if(realNum > pageNum) {
				System.out.println("pageNum : " + pageNum);
				System.out.println("keyword : " + keyword);
				try {
					keyword = URLEncoder.encode(keyword, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return "redirect:/company/search/"+pageNum+"/"+searchType+"/"+keyword+"";
			}
			
			return "company/company";
		}
		
		@RequestMapping(value = "/companyCheck", method = RequestMethod.POST)
		@ResponseBody
		public String companyCheck(Model model, @RequestParam String number) {
			ArrayList<CompanyVO> vo = companyservice.getAllCompany();
			model.addAttribute("Allcompany", vo);
			System.out.println(vo);
			System.out.println(number);
			for(int i=0; i < vo.size(); i++) {
				if( Integer.parseInt(number) == vo.get(i).getReg_number()) {
					System.out.println(vo.get(i).getReg_number());
					return "false";
				}
			}
			
			return "success";
		}
}
