package com.wda.sc;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
   public void insertfile(@RequestParam Map<String,String> allRequestParams, MultipartFile file, Model model) throws Exception {
       
      
     
      
      Iterator<String> keys = allRequestParams.keySet().iterator();
       while( keys.hasNext() ){
           String key = keys.next();
           System.out.println( String.format("키 : %s, 값 : %s", key, allRequestParams.get(key)) );
       } 
       
       System.out.println("originalname:"+ file.getOriginalFilename());
       System.out.println("size:"+ file.getSize());
       System.out.println("contentType:"+ file.getContentType());
       
       String savedName = uploadFile(file.getOriginalFilename(), file.getBytes());
       
       model.addAttribute("savedName" , savedName);
       
       
   }
   
   private String uploadFile(String orginalName , byte[] fileData ) throws Exception {
      
      String uploadPath = "C:\\upload";
      
      UUID uid = UUID.randomUUID();//randomUUID() 메소드를 사용해서 유일한 식별자를 생성합니다.
     // 반한되는 객체가 UUID 객체이므로 문자열 표현을 얻기 위해 toString() 메소드로 출력

      String savedName = uid.toString()+"_"+orginalName;
      
      File target = new File(uploadPath,savedName);
      
      FileCopyUtils.copy(fileData , target);//파일 데이터를 파일로 처리하거나 복사  filedata의 byte를  특정한 target으로 전송
      
      return savedName;
   }
}