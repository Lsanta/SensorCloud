package com.wda.sc;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wda.sc.domain.AttachFileVO;
import com.wda.sc.domain.CheckBoardFileVO;
import com.wda.sc.domain.CheckBoardVO;
import com.wda.sc.service.CheckboardService;
import lombok.AllArgsConstructor;


@Controller
@AllArgsConstructor
@RequestMapping("/app/checklist")
public class mChecklistController {
   private static final Logger logger = LoggerFactory.getLogger(mChecklistController.class);
   private CheckboardService Checkboardservice;
   
   
   //checkview 점검이력 글만 읽어오기
   @CrossOrigin(origins = "*", maxAge = 3600)
   @RequestMapping(value = "/mgetAttachList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
   @ResponseBody
   public ResponseEntity<List<CheckBoardFileVO>> mgetAttachList(@RequestBody String no) {
      System.out.println("getAttachList" + no);
      int board_no = Integer.parseInt(no);
      System.out.println(Checkboardservice.mgetAttachList(board_no));
      return new ResponseEntity<>(Checkboardservice.mgetAttachList(board_no), HttpStatus.OK);
   }
   
   
   
   //checkview , checkviewmodfiy 원본이미지 + 썸네일 불러오기 
   @CrossOrigin(origins = "*", maxAge = 3600)   
   @ResponseBody
   @RequestMapping(value="/mdisplay", method = RequestMethod.GET)
   public ResponseEntity<byte[]> mdisplay(String fileName){
      
      System.out.println("/display경로로 들어오나");
      File file = new File("c:\\upload\\" + fileName);
      System.out.println("fileCallPath"+"c:\\upload\\" + fileName );
      ResponseEntity<byte[]> result = null;
      
      try {
         HttpHeaders header = new HttpHeaders();
         
         header.add("Content-Type", Files.probeContentType(file.toPath()));
         result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file),header,HttpStatus.OK);
      }catch(IOException e) {
         e.printStackTrace();
      }
      return result;
   }
   
   //checkviewmodfiy 에서 파일만 삭제 시 
   @CrossOrigin(origins = "*", maxAge = 3600)
   @PostMapping("/mdeleteFile")
   @ResponseBody
   public ResponseEntity<String> mdeleteFile(String fileName, String type, String boardno ,String uuid){
      int board_no = Integer.parseInt(boardno);
         
     System.out.println("mdeleteFile에 넘어오는 board_no"+board_no);
     System.out.println("mdeleteFile에 넘어오는 uuid"+uuid);
      //해당 board_no에 해당하는 파일 db에서 삭제
     Map<String,Object> map = new HashMap<>();
     map.put("board_no",board_no);
     map.put("uuid",uuid);
      Checkboardservice.mfiledelete(map);
      File file;
      
      try {
         
         System.out.println(fileName);
         file = new File("c:\\upload\\"+URLDecoder.decode(fileName , "UTF-8"));
         
         file.delete();
         
         if(type.equals("image")) {
            //이미지의 경우 섬네일이 존재. 그래서 파일이름의 중간에 s_ 가 들어잇다. 이부분을 변경해서 원본 이미지 파일도 같이삭제
            
            String largeFileName = file.getAbsolutePath().replace("s_" ,"");
            
            System.out.println("largeFileName:"+largeFileName);
            
            file = new File(largeFileName);
            
            file.delete();
            
         }
         
      }catch(UnsupportedEncodingException e) {
         e.printStackTrace();
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
         
      return new ResponseEntity<String>("deleted" , HttpStatus.OK);
      
   }
   
   
// 점검이력에서 해당 글을 클릭후 삭제버튼을 누른 후
   @CrossOrigin(origins = "*", maxAge = 3600)
   @PostMapping("/mdeletepost")
   @ResponseBody
   public String mdeletepost(String boardno) {
      
         
         int board_no = Integer.parseInt(boardno);
      
         System.out.println(board_no);
         
         //c:경로 파일 삭제를 위해 해당 board_no의  file_name을 받아옴
//         Checkboardservice.findFilename(board_no);
//         List<String> list = Checkboardservice.findFilename(board_no);
//         System.out.println(list);
//         
//         System.out.println(list.size());
//       File file;
//         
//       for(int i=0; i<list.size(); i++) {
//          System.out.println(list.get(i));
//         try {
//            
//            file = new File("c:\\upload\\"+URLDecoder.decode(list.get(i) , "UTF-8"));
//            
//            file.delete();
//            
//               //이미지의 경우 섬네일이 존재. 그래서 파일이름의 중간에 s_ 가 들어잇다. 이부분을 변경해서 원본 이미지 파일도 같이삭제
//               String largeFileName = file.getAbsolutePath().replace("s_" ,"");
//               
//               System.out.println("largeFileName:"+largeFileName);
//               
//               file = new File(largeFileName);
//               
//               file.delete();
//               
//            
//            
//         }catch(UnsupportedEncodingException e) {
//            e.printStackTrace();
//            System.out.println("삭제 클릭시 전체 c경로 사진삭제 실패");
//         }
//         
//         }
//         
         
         
//       board_no를 통해 이미지파일 전체 삭제
      Checkboardservice.filedelete(board_no);
      
      
      
      // board_no를 통해 게시글 삭제
      Checkboardservice.checkboardDelete(board_no);

      

      System.out.println("삭제 완료 점검이력화면으로");

      return "result";
   }
   
   //checkmodfiy에서 수정완료 버튼을 눌렀을 시 
   
// 점검이력에서 글쓰기를 누른 후 수정버튼을 누른 후 수정
   @CrossOrigin(origins = "*", maxAge = 3600)
   @RequestMapping(value = "/mcheckmodify", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
   @ResponseBody
   public String mcheckmodify(@RequestBody CheckBoardVO vo) {

     
      System.out.println("수정할 때 vo : " + vo);

      int i = Checkboardservice.updateCheckBoard(vo);

      System.out.println(i);
      if(i==1) {
         System.out.println("수정 성공 점검이력으로");
      }
      else {
         System.out.println("수정 다시");
      }
      
      
      return "success";
   }
   
   
   
    

   // 점검이력
   @CrossOrigin(origins = "*", maxAge = 3600)
   @RequestMapping(value = "/writecheck", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
   @ResponseBody
   public int insertchecklist(@RequestBody CheckBoardVO vo) {
     
    //글쓰기 컨텐트 db 저장 
         Checkboardservice.register(vo);
         //insert 직후 해당 열의 primary키를 뽑아내 vo에 저장후  찍어봄 
         //CheckBoardVO vo1 = new CheckBoardVO();
         int board_no  = vo.getBoard_no();
         System.out.println("보드넘버" + board_no);
                  
      return board_no;

   }
   
   // file_transfer 데이터 받기
   
   @SuppressWarnings("null")
   @CrossOrigin(origins = "*", maxAge = 3600)
   @RequestMapping(value = "/insertfile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
   @ResponseBody
   public void insertfile(@RequestParam Map<String,String> allRequestParams, MultipartFile file, Model model) throws Exception {
       
     System.out.println("오나?");
      String uploadPath = "C:\\upload";
      
      Iterator<String> keys = allRequestParams.keySet().iterator();
       while( keys.hasNext() ){
           String key = keys.next();
           System.out.println( String.format("키 : %s, 값 : %s", key, allRequestParams.get(key)) );
       } 
       
       System.out.println("originalname:"+ file.getOriginalFilename());
       System.out.println("size:"+ file.getSize());
       System.out.println("contentType:"+ file.getContentType());
       System.out.println("file.getBytes():"+ file.getBytes());
       String savedName = uploadFile(uploadPath,file.getOriginalFilename(), file.getBytes());
       
//       <insert id="insert">
//      insert into check_board_file(uuid,file_path,file_name,filetype,board_no) values(#{uuid},#{file_Path},#{file_name},#{fileType},#{board_no})
//      </insert>
       
       System.out.println("savename"+savedName);
       
       
             //2019/05/31/s_71728aa0-9d4d-4b2c-a50a-22039e4b8827_.Pic.jpg
       
//       CheckBoardVO checkboardvo = null;
//       CheckBoardFileVO checkboardfilevo = null;
             
       
//         int board_no = checkboardvo.getBoard_no();
//             System.out.println(board_no);
             String[] array = savedName.split("/");
           
            String year = array[1];
            String month = array[2];
            String day = array[3];
            String uploadPath2 = year +"/"+ month +"/"+ day;
            System.out.println("경로" +year+"/"+month+"/"+day);
            
            
            String[] array2 = savedName.split("_");
            
            String uuid = array2[1];
           
           String realname = array2[2];
            System.out.println("db에 저장되는 realname:"+realname);
            System.out.println("db에 저장되는 uuid:"+uuid);
            int boardno = Integer.parseInt(allRequestParams.get("value2"));
            System.out.println("넘어온 키값"+boardno);
            
            
            CheckBoardFileVO checkboardfilevo = new CheckBoardFileVO();
            
            checkboardfilevo.setBoard_no(boardno);
            checkboardfilevo.setUuid(uuid);
            checkboardfilevo.setFile_name(realname);
            checkboardfilevo.setFile_Path(uploadPath2);
            checkboardfilevo.setFileType(true);
            
            Checkboardservice.mfileinsert(checkboardfilevo);
           
   }
   
   
   public String uploadFile(String uploadPath, String originalName, byte[] fileData) //별도의 데이터가 보관될 필요가없기에 static
         throws Exception {
      
      
      UUID uid = UUID.randomUUID();
      String savedName = uid.toString()+"_"+originalName;
      
      String savedPath = calcPath(uploadPath);
      
      File target = new File(uploadPath + savedPath , savedName);
      
      System.out.println("uid" + uid);
      System.out.println("upload메소드안의 savedname"+savedName);
      
      FileCopyUtils.copy(fileData, target);
      
      String formatName = originalName.substring(originalName.lastIndexOf(".")+1);
      
      String uploadedFileName = null;
      
      if(MediaUtils.getMediaType(formatName) != null) {
         uploadedFileName = makeThumbnail(uploadPath , savedPath , savedName);
      }
      else {
         uploadedFileName = makeIcon(uploadPath , savedPath , savedName);
      }
      
      System.out.println("uploadedFileName" + uploadedFileName);
      return uploadedFileName;
      
   }
   
   private static String makeIcon(String uploadPath ,String path , String fileName)throws Exception{
      
      String iconName = uploadPath+path+File.separator+fileName;
      
      return iconName.substring(
               uploadPath.length()).replace(File.separatorChar, '/');
   }
   
   private static String calcPath(String uploadPath) {
         
      Calendar cal = Calendar.getInstance();
      
      String yearPath = File.separator+cal.get(Calendar.YEAR);
      
      String monthPath = yearPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH)+1);
      
      String datePath = monthPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));
      
      makeDir(uploadPath , yearPath, monthPath , datePath);
      
      logger.info(datePath);
      
      return datePath;
      
      
   }
   
   //날짜정보와 기본경로와함께 전달되어 폴더가 생성
   private static void makeDir(String uploadPath , String ...paths) {
      
      if(new File(uploadPath + paths[paths.length -1]).exists())
         return;
      
   
   for (String path : paths) {
      
      File dirPath = new File(uploadPath +path);
      
      if(! dirPath.exists()) {
         dirPath.mkdir();
      }
   }
   }
   
   //썸네일 이미지 생성
   
   private static String makeThumbnail (String uploadPath, 
         String path, String fileName) throws Exception {
      
      
      BufferedImage sourceImg = ImageIO.read(new File(uploadPath + path , fileName));
      BufferedImage destImg = Scalr.resize(sourceImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT,100);
      
      String thumbnailName = uploadPath + path + File.separator + "s_" + fileName;
      
      File newFile = new File(thumbnailName);
      String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
      
      ImageIO.write(destImg, formatName.toUpperCase(), newFile);
      
      return thumbnailName.substring(
            uploadPath.length()).replace(File.separatorChar , '/');
            
   }
   
   
   
   
   
//   private String uploadFile(String orginalName , byte[] fileData ) throws Exception {
//      
//      String uploadPath = "C:\\upload";
//      
//      UUID uid = UUID.randomUUID();//randomUUID() 메소드를 사용해서 유일한 식별자를 생성합니다.
//     // 반한되는 객체가 UUID 객체이므로 문자열 표현을 얻기 위해 toString() 메소드로 출력
//
//      String savedName = uid.toString()+"_"+orginalName;
//      
//      File target = new File(uploadPath,savedName);
//      
//      FileCopyUtils.copy(fileData , target);//파일 데이터를 파일로 처리하거나 복사  filedata의 byte를  특정한 target으로 전송
//      
//      return savedName;
//   }
}