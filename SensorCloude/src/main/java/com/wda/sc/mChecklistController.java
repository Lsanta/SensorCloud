package com.wda.sc;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.wda.sc.domain.CheckBoardVO;
import com.wda.sc.service.CheckboardService;
import lombok.AllArgsConstructor;


@Controller
@AllArgsConstructor
@RequestMapping("/app/checklist")
public class mChecklistController {
   private CheckboardService checkboardservice;

   // 점검이력
   @CrossOrigin(origins = "*", maxAge = 3600)
   @RequestMapping(value = "/writecheck", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
   @ResponseBody
   public String insertchecklist(@RequestBody CheckBoardVO vo) {

      checkboardservice.insertcheckboard(vo);
      System.out.println(checkboardservice.insertcheckboard(vo));

      return "success";

   }
   
   // file_transfer 데이터 받기
   @CrossOrigin(origins = "*", maxAge = 3600)
   @RequestMapping(value = "/insertfile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
   @ResponseBody
   public void insertfile(@RequestBody String file_path ) {

      System.out.println(file_path);

   }
}