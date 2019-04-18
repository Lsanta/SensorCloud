package com.wda.sc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wda.sc.domain.AttachFileVO;

import net.coobird.thumbnailator.Thumbnailator;

@Controller
public class UploadController {
	private boolean checkImageType(File file) {

		try {
			String contentType = Files.probeContentType(file.toPath());

			return contentType.startsWith("image");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}
	
	private String getFolder() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Date date = new Date();

		String str = sdf.format(date);

		return str.replace("-", File.separator);
	}
	
	@PostMapping(value = "/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<AttachFileVO>> uploadAjaxPost(MultipartFile[] uploadFile) {
		
		List<AttachFileVO> list = new ArrayList<>();
		String uploadFolder = "C:\\upload";
		
		String uploadFolderPath = getFolder();
		
		//make folder-----------------
		File uploadPath = new File(uploadFolder, uploadFolderPath);
		
		if(uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}
		//make yyyy/mm/dd folder
		
		for(MultipartFile multipartFile : uploadFile) {
			
			AttachFileVO attachVO = new AttachFileVO();
			String uploadFileName = multipartFile.getOriginalFilename();
			
			//IE has file path
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
			attachVO.setFileName(uploadFileName);
			UUID uuid = UUID.randomUUID();
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			
		
			
			
			try {
				File saveFile = new File(uploadPath, uploadFileName);
				multipartFile.transferTo(saveFile);
				attachVO.setUuid(uuid.toString());
				attachVO.setUploadPath(uploadFolderPath);
				//check image type file
				if(checkImageType(saveFile)) {
					attachVO.setImage(true);
					FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));
					Thumbnailator.createThumbnail(multipartFile.getInputStream(),thumbnail,100,100);
					thumbnail.close();
				}
				
				//add to List
				list.add(attachVO);
				
			}catch(Exception e) {
				e.printStackTrace();
			}//end catch
		}//end for
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	@GetMapping("/display")
	@ResponseBody
	public ResponseEntity<byte[]> getFile(String fileName){
		File file = new File("c:\\upload\\" + fileName);
		
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
	 @GetMapping(value="/download" ,
		 produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
		 @ResponseBody
		 public ResponseEntity<Resource>
		 downloadFile(@RequestHeader("User-Agent")String userAgent, String fileName){
		
		 Resource resource = new FileSystemResource("c:\\upload\\" + fileName);
		
		 if(resource.exists() == false) {
		 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		 }
		
		 String resourceName = resource.getFilename();
		 String resourceOriginalName = resourceName.substring(resourceName.indexOf("_")+1);
		 HttpHeaders headers = new HttpHeaders();
		try{
		String downloadName = null;
		if( userAgent.contains("Trident")){
		downloadName = URLEncoder.encode(resourceOriginalName, "UTF-8").replaceAll("\\+"," ");
		}else if(userAgent.contains("Edge")){
		downloadName = URLEncoder.encode(resourceOriginalName,"UTF-8");
		}else{
		downloadName = new String(resourceOriginalName.getBytes("UTF-8"),"ISO-8859-1");
		}

		 headers.add("Content-Disposition", "attachment; filename=" + downloadName);
		 } catch (UnsupportedEncodingException e) {
		 e.printStackTrace();
		 }
		
		 return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
		 }
	 
	 @PostMapping("/deleteFile")
		@ResponseBody
		public ResponseEntity<String> deleteFile(String fileName, String type) {

			File file;

			try {
				file = new File("c:\\upload\\" + URLDecoder.decode(fileName, "UTF-8"));

				file.delete();

				if (type.equals("image")) {

					String largeFileName = file.getAbsolutePath().replace("s_", "");


					file = new File(largeFileName);

					file.delete();
				}

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<String>("deleted", HttpStatus.OK);

		}
	
}
