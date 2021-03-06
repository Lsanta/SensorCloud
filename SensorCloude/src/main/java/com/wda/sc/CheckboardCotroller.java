package com.wda.sc;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
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
import com.wda.sc.domain.SiteVO;
import com.wda.sc.service.CheckboardService;
import com.wda.sc.service.SiteService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/checkboard")
public class CheckboardCotroller {
   private CheckboardService Checkboardservice;
   private SiteService siteservice;
   
   //점검이력 글쓰기 
   @RequestMapping(value = "checkadd", method = RequestMethod.GET)
   public String address(Locale locale, Model model,HttpServletResponse response,HttpSession session) throws IOException {

      int mlevel = (int) session.getAttribute("mlevel");

      if (mlevel < 3) {

         response.setContentType("text/html; charset=UTF-8");
         PrintWriter out = response.getWriter();
         out.println("<script langauge='javascript'>");
         out.println("alert('권한이 없습니다. \\n 3등급(쓰기권한)이상이 열람가능합니다'); history.go(-1);");
         out.println("</script>");

         
      }
      model.addAttribute("depth0","메인화면");
	  model.addAttribute("depth1","점검이력");
	  
      model.addAttribute("checksitelist", siteservice.getchecksite());
      return "check/checkadd";
   }

   @RequestMapping(value = "checkadd.do", method = RequestMethod.POST)
   public String insertcheckboard(CheckBoardVO vo, HttpSession session, RedirectAttributes rttr) {

      String id = (String) session.getAttribute("id");
      vo.setUser_id(id);
     
      if (vo.getAttachList() != null) {
         vo.getAttachList().forEach(attach -> System.out.println(attach));
      }
      

      Checkboardservice.register(vo);

      rttr.addFlashAttribute("result", vo.getBoard_no());

      return "redirect: /check/1";
   }

   // 점검이력 content보기
   @RequestMapping(value = "{board_no}", method = RequestMethod.GET)
   public String checkin(Locale locale, @PathVariable String board_no, Model model, HttpSession session ,HttpServletResponse response) throws IOException {
      
      int mlevel = (int) session.getAttribute("mlevel");

      if (mlevel < 2) {

         response.setContentType("text/html; charset=UTF-8");
         PrintWriter out = response.getWriter();
         out.println("<script langauge='javascript'>");
         out.println("alert('권한이 없습니다. \\n 2등급(읽기권한)이상이 열람가능합니다'); history.go(-1);");
         out.println("</script>");

         
      }

      // 수정클릭시 권한체크 위해 id에 해당하는 m_level을 넘긴다.
      String user_id = (String) session.getAttribute("id");
      model.addAttribute("checkauthority", Checkboardservice.checkauthority(user_id));

      model.addAttribute("cklist", Checkboardservice.viewgetList(board_no));
      model.addAttribute("siteid", Checkboardservice.getsiteid(board_no));
      model.addAttribute("board_no", board_no);
      
      model.addAttribute("depth0","메인화면");
	  model.addAttribute("depth1","점검이력");
      return "check/checkview";
   }

   // 현장 에서 해당 글을 클릭후 수정버튼을 누르면 글쓰기 폼으로 되돌아가면서 정보 전달 
   @RequestMapping(value = "/checkmod" + "/{board_no}" + "/{site_id}", method = RequestMethod.GET)
   public String checkmod(Locale locale, Model model, @PathVariable String board_no, @PathVariable int site_id,
         HttpSession session ,HttpServletResponse response) throws IOException {
      
      int mlevel = (int) session.getAttribute("mlevel");

      if (mlevel < 3) {

         response.setContentType("text/html; charset=UTF-8");
         PrintWriter out = response.getWriter();
         out.println("<script langauge='javascript'>");
         out.println("alert('권한이 없습니다. \\n 3등급(쓰기권한)이상이 열람가능합니다'); history.go(-1);");
         out.println("</script>");

         
      }
      
      
      // board_no를 통해 점검이력 내용 가져오기 + site_id 넣기
      ArrayList<CheckBoardVO> cvo = Checkboardservice.viewgetList(board_no);
      cvo.get(0).setSite_id(site_id);
      model.addAttribute("modlist", cvo);
      // ID를 통해 권한레벨 가져오기
      String user_id = (String) session.getAttribute("id");
      model.addAttribute("auth", Checkboardservice.checkauthority(user_id));

      // 사이트 이름 등등의 정보
      model.addAttribute("checksitelist", siteservice.getchecksite());

      String site_name = siteservice.getSiteName(site_id);
		  
	  model.addAttribute("depth0","메인화면");
	  model.addAttribute("depth1","현장관리");
	  model.addAttribute("depth2", site_name);
	  model.addAttribute("depth3", "수리내역");
      
      return "check/checkaddInSite";
   }

   @GetMapping(value = "/getAttachList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
   @ResponseBody
   public ResponseEntity<List<CheckBoardFileVO>> getAttachList(int board_no) {
	   
      return new ResponseEntity<>(Checkboardservice.getAttachList(board_no), HttpStatus.OK);
   }

   @GetMapping(value = "/getAttachListmain", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
   @ResponseBody
   public ResponseEntity<List<CheckBoardFileVO>> getAttachListmain(int board_no) {
  
      return new ResponseEntity<>(Checkboardservice.getAttachListmain(board_no), HttpStatus.OK);
   }

   /// 점검이력에서 해당글 클릭후 수정버튼을 누를시 글쓰기폼에 해당 데이터 전달
   @RequestMapping(value = "/checkmod2" + "/{board_no}", method = RequestMethod.GET)
   public String checkmod2(Locale locale, Model model, @PathVariable String board_no, HttpSession session,HttpServletResponse response) throws IOException {

      int mlevel = (int) session.getAttribute("mlevel");

      if (mlevel < 3) {

         response.setContentType("text/html; charset=UTF-8");
         PrintWriter out = response.getWriter();
         out.println("<script langauge='javascript'>");
         out.println("alert('권한이 없습니다. \\n 3등급(쓰기권한)이상이 열람가능합니다'); history.go(-1);");
         out.println("</script>");

         
      }
      
      // board_no를 통해 점검이력 내용 가져오기
      model.addAttribute("modlist", Checkboardservice.viewgetList(board_no));

      // ID를 통해 권한레벨 가져오기
      String user_id = (String) session.getAttribute("id");
      model.addAttribute("auth", Checkboardservice.checkauthority(user_id));
 
      // 사이트 이름,점검이력 제목,점검이력내용 정보 가져오기
      model.addAttribute("checksitelist", siteservice.getchecksite());

      // 현장아이디
      model.addAttribute("siteid", Checkboardservice.getsiteid(board_no));
     
      model.addAttribute("depth0","메인화면");
	  model.addAttribute("depth1","점검이력");
      return "check/checklistmodify";
   }

   // 현장 에서 해당 글을 클릭후 삭제버튼을 누른 후

   @RequestMapping(value = "/checkdel" + "/{board_no}" + "/{site_id}", method = RequestMethod.GET)
   public String checkdel(Locale locale, Model model, @PathVariable int board_no, @PathVariable String site_id) {
      int num = board_no;
      List<CheckBoardFileVO> attachList = Checkboardservice.getAttachList(num);

      // board_no를 통해 첨부파일 삭제
      int delN = Checkboardservice.filedelete(board_no);

      // board_no를 통해 게시글 삭제
      int delN2 = Checkboardservice.checkboardDelete(board_no);
      deleteFiles(attachList);
      
      return "redirect: /site/" + site_id + "/siterepair/1";
   }

   // 점검이력에서 해당 글을 클릭후 삭제버튼을 누른 후
   @RequestMapping(value = "/checkdel2" + "/{board_no}", method = RequestMethod.GET)
   public String checkdel2(Locale locale, Model model, @PathVariable int board_no) {

      int num = board_no;
      List<CheckBoardFileVO> attachList = Checkboardservice.getAttachList(num);

      // board_no를 통해 첨부파일 삭제
      int delN = Checkboardservice.filedelete(board_no);

      // board_no를 통해 게시글 삭제
      int delN2 = Checkboardservice.checkboardDelete(board_no);

      deleteFiles(attachList);
      
      return "redirect: /check/1";
   }

   // 마이페이지에서 해당 글을 클릭후 수정 버튼을 눌렀을 시 글쓰기 폼으로
   @RequestMapping(value = "/checkmod3" + "/{board_no}", method = RequestMethod.GET)
   public String checkmod3(Locale locale, Model model, @PathVariable String board_no, HttpSession session) {

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
   public String checkdel3(Locale locale, Model model, @PathVariable int board_no) {
      int num = board_no;
      List<CheckBoardFileVO> attachList = Checkboardservice.getAttachList(num);

      // board_no를 통해 첨부파일 삭제
      int delN = Checkboardservice.filedelete(board_no);

      // board_no를 통해 게시글 삭제
      int delN2 = Checkboardservice.checkboardDelete(board_no);

      deleteFiles(attachList);

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
   public String address(Locale locale, Model model, @PathVariable String site_id,HttpSession session, HttpServletResponse response) throws IOException {
      
      int mlevel = (int) session.getAttribute("mlevel");

      if (mlevel < 3) {

         response.setContentType("text/html; charset=UTF-8");
         PrintWriter out = response.getWriter();
         out.println("<script langauge='javascript'>");
         out.println("alert('권한이 없습니다. \\n 3등급(쓰기권한)이상이 열람가능합니다'); history.go(-1);");
         out.println("</script>");

         
      }
      
  	  ArrayList<SiteVO> depthArr = new ArrayList<SiteVO>();
	  depthArr = siteservice.getSite(site_id.toString());
	  
	  model.addAttribute("depth0","메인화면");
	  model.addAttribute("depth1","현장관리");
	  model.addAttribute("depth2", depthArr.get(0).getSite_name());
	  model.addAttribute("depth3", "수리내역");
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

      if (vo.getAttachList() != null) {
//         vo.getAttachList().forEach(attach -> System.out.println(attach));
      }

      Checkboardservice.register(vo);

      rttr.addFlashAttribute("result", vo.getBoard_no());

      return "redirect: /site/" + site_id + "/siterepair/1";
   }

   // 수리내역에서 글쓰기를 누른 후 수정버튼을 누른 후 수정
   @RequestMapping(value = "checkmodInSite.do", method = RequestMethod.POST)
   public String modcheckboardIn(CheckBoardVO vo, HttpSession session, RedirectAttributes rttr) {

      int site_id = vo.getSite_id();
      String id = (String) session.getAttribute("id");
      vo.setUser_id(id);

      // vo안에 있는 site_id 로 site_name 가져오기
      vo.setSite_name(siteservice.getSiteName(vo.getSite_id()));

      int i = Checkboardservice.updateCheck(vo);

      if (i == 1) {
         Checkboardservice.fileupdate(vo);
      }

      return "redirect: /site/" + site_id + "/siterepair/1";
   }

   // 점검이력에서 글쓰기를 누른 후 수정버튼을 누른 후 수정
   @RequestMapping(value = "checkmod.do", method = RequestMethod.POST)
   public String modcheckboard(CheckBoardVO vo, HttpSession session, RedirectAttributes rttr) {

      String id = (String) session.getAttribute("id");
      vo.setUser_id(id);

      // vo안에 있는 site_id 로 site_name 가져오기
      vo.setSite_name(siteservice.getSiteName(vo.getSite_id()));

      int i = Checkboardservice.updateCheckBoard(vo);

      if (i == 1) {
         Checkboardservice.fileupdate(vo);
      }

      return "redirect: /check/1";
   }

   // 점검이력 검색
   @RequestMapping(value = "/search" + "/{page}" + "/{searchType}" + "/{keyword}", method = RequestMethod.GET)
   public String checkSearch(@PathVariable int page, @PathVariable String searchType, @PathVariable String keyword,
         Model model) {

      Paging p = new Paging();
      Search s = new Search();
      ArrayList<CheckBoardVO> searchArr = new ArrayList<CheckBoardVO>();
      ArrayList<Integer> arr = new ArrayList<Integer>();
      Map<Object, Object> parm = new HashMap<Object, Object>();
      Map<Integer, ArrayList<Integer>> map = new HashMap<Integer,ArrayList<Integer>>();

      s.setPage(page);
      s.setKeyword(keyword);
      s.setSearchType(searchType);
      searchArr = Checkboardservice.checkSearch(s);

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
      
      model.addAttribute("depth0","메인화면");
	  model.addAttribute("depth1","점검이력");
      model.addAttribute("lastNum", pageNum);
      model.addAttribute("pageNum", map.get(sendPageNum));
      model.addAttribute("check", Checkboardservice.getSearchResult(parm));
      
      if(realNum > pageNum) {
         try {
            keyword = URLEncoder.encode(keyword, "UTF-8");
         } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
         return "redirect:/checkboard/search/"+pageNum+"/"+searchType+"/"+keyword+"";
      }
      return "check/check";
   }

   @RequestMapping(value = "/dataSearch" + "/{page}" + "/{data}", method = RequestMethod.GET)
   public String dataSearch(@PathVariable int page, @PathVariable int data, Model model) {
      
      
      Paging p = new Paging();
      ArrayList<CheckBoardVO> term = new ArrayList<CheckBoardVO>();
      ArrayList<Integer> arr = new ArrayList<Integer>();
      Map<Object, Object> parm = new HashMap<Object, Object>();
      Map<Integer, ArrayList<Integer>> map = new HashMap<Integer,ArrayList<Integer>>();
      
      if (data != 0) {
         term = Checkboardservice.dateChange(data);

         int pageNum = 0;
         int mapNum=0;
         int sendPageNum=0;
         int realNum = page;
         p.setTotalNum(term.size());

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
//         for (int i = 0; i < pageNum; i++) {
//            arr.add(i + 1);
//         }

         p.setEndnum((realNum * 10) + 1);
         p.setStartnum(p.getEndnum() - 10);

         parm.put("paging", p);
         parm.put("data", data);
         
         model.addAttribute("depth0","메인화면");
   	  	 model.addAttribute("depth1","점검이력");
         model.addAttribute("lastNum", pageNum);
         model.addAttribute("pageNum", map.get(sendPageNum)); //arr
         model.addAttribute("checkboardlist", Checkboardservice.getTermList(parm));
         
         if (realNum > pageNum) {
            return "redirect:/checkboard/dataSearch/" + pageNum  + "/" + data;
         }   
         
         return "check/check";
      } else {

         return "redirect: /check/1";
      }

   }
   
   //점검이력 관리 날짜 검색
   @RequestMapping(value = "/admindataSearch" +"/{num0}"+"/{num1}"+"/{num2}"+ "/{data0}" + "/{data1}" + "/{data2}"  + "/{status}", method = RequestMethod.GET)
   public String adminDataSearch(@PathVariable int num0, @PathVariable int num1, @PathVariable int num2, 
		   @PathVariable int data0 , @PathVariable int data1 , @PathVariable int data2 ,
		   @PathVariable int status, Model model) {
      
      String result = "";
      CheckboardManage cm = new CheckboardManage();
    	  if(status == 0) {
    		  for(int i=0; i < 3 ; i++) {
                  if( i == 0) {
                     result = cm.dataSearch(Checkboardservice, model, num0, data0, i);
                     if(result == "false") {    
                        int number =num0-1;
                        return "redirect:/checkboard/admindataSearch/" + number + "/" + num1 + "/" + num2 +"/"+ data0 +"/"+ data1 +"/"+ data2 + "/0";
                     }
                  } else if ( i == 1) {
                     result = cm.dataSearch(Checkboardservice, model, num1, data1, i);
                     if(result == "false") {
                        int number = num1-1;
                        return "redirect:/checkboard/admindataSearch/" + num0 + "/" + number+ "/" + num2 +"/"+ data0 +"/"+ data1 +"/"+ data2 + "/0";
                     }
                  } else if ( i == 2 ) {
                     result = cm.dataSearch(Checkboardservice, model, num2, data2, i);
                     if(result == "false") {
                        int number = num2-1;
                        return "redirect:/checkboard/admindataSearch/" + num0 + "/" + num1 + "/" + number +"/"+ data0 +"/"+ data1 +"/"+ data2 + "/0";
                     }
                  } 
               }  
    	  } else if(status == 1) {
    		  for(int i=0; i < 3 ; i++) {
                  if( i == 0) {
                     result = cm.dataSearch(Checkboardservice, model, num0, data0, i);
                     if(result == "false") {
                        int number =num0-1;
                        return "redirect:/checkboard/admindataSearch/" + number + "/" + num1 + "/" + num2 +"/"+ data0 +"/"+ data1 +"/"+ data2 + "/1";
                     }
                  } else if ( i == 1) {
                     result = cm.dataSearch(Checkboardservice, model, num1, data1, i);
                     if(result == "false") {
                        int number = num1-1;
                        return "redirect:/checkboard/admindataSearch/" + num0 + "/" + number+ "/" + num2 +"/"+ data0 +"/"+ data1 +"/"+ data2 + "/1";
                     }
                  } else if ( i == 2 ) {
                     result = cm.dataSearch(Checkboardservice, model, num2, data2, i);
                     if(result == "false") {
                        int number = num2-1;
                        return "redirect:/checkboard/admindataSearch/" + num0 + "/" + num1 + "/" + number +"/"+ data0 +"/"+ data1 +"/"+ data2 + "/1";
                     }
                  } 
               }  
    	  } else if(status == 2) {
    		  for(int i=0; i < 3 ; i++) {
                  if( i == 0) {
                     result = cm.dataSearch(Checkboardservice, model, num0, data0, i);
                     if(result == "false") {
                        int number =num0-1;
                        return "redirect:/checkboard/admindataSearch/" + number + "/" + num1 + "/" + num2 +"/"+ data0 +"/"+ data1 +"/"+ data2 + "/2";
                     }
                  } else if ( i == 1) {
                     result = cm.dataSearch(Checkboardservice, model, num1, data1, i);
                     if(result == "false") {
                        int number = num1-1;
                        return "redirect:/checkboard/admindataSearch/" + num0 + "/" + number+ "/" + num2 +"/"+ data0 +"/"+ data1 +"/"+ data2 + "/2";
                     }
                  } else if ( i == 2 ) {
                     result = cm.dataSearch(Checkboardservice, model, num2, data2, i);
                     if(result == "false") {
                        int number = num2-1;
                        return "redirect:/checkboard/admindataSearch/" + num0 + "/" + num1 + "/" + number +"/"+ data0 +"/"+ data1 +"/"+ data2 + "/2";
                     }
                  } 
               }  
    	  }
    	 
      
    	  return "adminCheck/adminCheck";
   
   }
   
      // 점검이력관리 검색
      @RequestMapping(value = "/checkManagesearch" +"/{num0}"+"/{num1}"+"/{num2}"+ "/{searchType}" + "/{keyword}", method = RequestMethod.GET)
      public String checkManagesearch(@PathVariable int num0, @PathVariable int num1, @PathVariable int num2, @PathVariable String searchType, @PathVariable String keyword,
            Model model) {

         String result = "";
         CheckboardManage cm = new CheckboardManage();
         for(int i=0; i < 3 ; i++) {
            if( i == 0) {
               result = cm.checkSearch(Checkboardservice, model, num0, searchType, keyword, i);
               if(result == "false") {
                  int number =num0-1;
                  try {
                     keyword = URLEncoder.encode(keyword, "UTF-8");
                  } catch (UnsupportedEncodingException e) {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
                  }
                  return "redirect:/checkboard/checkManagesearch/"+number+"/"+num1+"/"+num2+"/"+searchType+"/"+keyword+"";
               }
            } else if ( i == 1) {
               result = cm.checkSearch(Checkboardservice, model, num1, searchType, keyword, i);
               if(result == "false") {
                  int number = num1-1;
                  try {
                     keyword = URLEncoder.encode(keyword, "UTF-8");
                  } catch (UnsupportedEncodingException e) {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
                  }
                  return "redirect:/checkboard/checkManagesearch/"+num0+"/"+number+"/"+num2+"/"+searchType+"/"+keyword+"";
               }
            } else if ( i == 2 ) {
               result = cm.checkSearch(Checkboardservice, model, num2, searchType, keyword, i);
               if(result == "false") {
                  int number = num2-1;
                  try {
                     keyword = URLEncoder.encode(keyword, "UTF-8");
                  } catch (UnsupportedEncodingException e) {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
                  }
                  return "redirect:/checkboard/checkManagesearch/"+num0+"/"+num1+"/"+number+"/"+searchType+"/"+keyword+"";
               }
            }
            
         }
         return "adminCheck/adminCheck";
      }

      
      //점검이력 fixed로 업데이트
      @RequestMapping(value = "/fixedUpdate/"+"{board_no}" , method = RequestMethod.GET)
      public String fixedUpdate(@PathVariable int board_no, Locale locale, Model model,HttpServletResponse response,HttpSession session) throws IOException {
    	 int a = Checkboardservice.fixedUpdate(board_no);
    	 
    	 if( a == 1 ) {
    		 response.setContentType("text/html; charset=UTF-8");
             PrintWriter out = response.getWriter();
             out.println("<script langauge='javascript'>");
             out.println("alert(처리완료로 수정 완료)");
             
             out.println("</script>");
    	 }
    	 
         return "redirect:/check/1";
      }
      
      //점검이력 close로 업데이트
      @RequestMapping(value = "/closeUpdate/"+"{board_no}" , method = RequestMethod.GET)
      public String closeUpdate(@PathVariable int board_no, Locale locale, Model model,HttpServletResponse response,HttpSession session) throws IOException {
    	 int a = Checkboardservice.closeUpdate(board_no); 
    	 
    	 if( a == 1 ) {
    		 response.setContentType("text/html; charset=UTF-8");
             PrintWriter out = response.getWriter();
             out.println("<script langauge='javascript'>");
             out.println("alert(완료확인으로 수정 완료)");
             
             out.println("</script>");
    	 }
         return "redirect:/check/1";
      }
      
      //점검이력 close로 업데이트
      @RequestMapping(value = "/sitecloseUpdate/"+"{board_no}" + "/" + "{site_id}" , method = RequestMethod.GET)
      public String repaircloseUpdate(@PathVariable int board_no, @PathVariable int site_id, Locale locale, Model model,HttpServletResponse response,HttpSession session) throws IOException {
    	 int a = Checkboardservice.closeUpdate(board_no); 
    	 
    	 if( a == 1 ) {
    		 response.setContentType("text/html; charset=UTF-8");
             PrintWriter out = response.getWriter();
             out.println("<script langauge='javascript'>");
             out.println("alert(완료확인으로 수정 완료)");
             
             out.println("</script>");
    	 }
    	   return "redirect:/site/"+site_id+"/siterepair/1";
      }
      
      //점검이력 fixed로 업데이트
      @RequestMapping(value = "/sitefixed/"+"{board_no}" + "/" + "{site_id}" , method = RequestMethod.GET)
      public String repairfixedUpdate(@PathVariable int board_no, @PathVariable int site_id, Locale locale, Model model,HttpServletResponse response,HttpSession session) throws IOException {
    	 int a = Checkboardservice.fixedUpdate(board_no);
    	 
    	 if( a == 1 ) {
    		 response.setContentType("text/html; charset=UTF-8");
             PrintWriter out = response.getWriter();
             out.println("<script langauge='javascript'>");
             out.println("alert(처리완료로 수정 완료)");
             
             out.println("</script>");
    	 }
    	 
         return "redirect:/site/"+site_id+"/siterepair/1";
      }
      
}