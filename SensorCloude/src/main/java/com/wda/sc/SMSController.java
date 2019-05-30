package com.wda.sc;

import java.io.*;
import java.net.*;
import java.security.*;
import java.util.Locale;
import javax.net.ssl.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/SMS")
public class SMSController {
	
	@SuppressWarnings("restriction")
	@RequestMapping(value = "/SMSTest", method = RequestMethod.POST)
	public String SMSMS(Locale locale, Model model) {
		System.out.println("문자 전송 컨트롤러");
		
		/*
		 * 뿌리오 발송API 경로 - 서버측 인코딩과 응답형태에 따라 선택
		 */
		//String api_url = "https://www.ppurio.com/api/send_euckr_text.php";    // EUC-KR 인코딩과 TEXT 응답용 호출 페이지
		// String api_url = "https://www.ppurio.com/api/send_euckr_json.php"; // EUC-KR 인코딩과 JSON 응답용 호출 페이지
		// String api_url = "https://www.ppurio.com/api/send_euckr_xml.php";  // EUC-KR 인코딩과 XML 응답용 호출 페이지
		// String api_url = "https://www.ppurio.com/api/send_utf8_text.php";  // UTF-8 인코딩과 TEXT 응답용 호출 페이지
		 String api_url = "https://www.ppurio.com/api/send_utf8_json.php";  // UTF-8 인코딩과 JSON 응답용 호출 페이지
		// String api_url = "https://www.ppurio.com/api/send_utf8_xml.php";   // UTF-8 인코딩과 XML 응답용 호출 페이지


		/*
		 * 요청값
		 */
		String userid = "gkgkrk123";           // [필수] 뿌리오 아이디
		String callback = "01012345678";    // [필수] 발신번호 - 숫자만
		String phone = "01046037101";       // [필수] 수신번호 - 여러명일 경우 |로 구분 "010********|010********|010********"
		String msg = "테스트 발송입니다";   // [필수] 문자내용 - 이름(names)값이 있다면 [*이름*]가 치환되서 발송됨
		String names = "홍길동";            // [선택] 이름 - 여러명일 경우 |로 구분 "홍길동|이순신|김철수"
		// String appdate = "20190502093000";  // [선택] 예약발송 (현재시간 기준 10분이후 예약가능)
		String subject = "테스트";          // [선택] 제목 (30byte)


		try {

		    String str = "userid="+userid+"&callback="+callback+"&phone="+phone+"&msg="+msg+"&names="+names+"&subject="+subject;
		    System.out.println(str);
		    URL url = new URL(api_url);
		    System.out.println(api_url);
		    Security.addProvider(
		      new com.sun.net.ssl.internal.ssl.Provider());

		    SSLSocketFactory factory = 
		      (SSLSocketFactory)SSLSocketFactory.getDefault();
		    SSLSocket socket = 
		      (SSLSocket)factory.createSocket(url.getHost(),443);

		    PrintWriter out = new PrintWriter(
		        new OutputStreamWriter(
		            socket.getOutputStream()));
		    out.println("POST "+ api_url +" HTTP/1.1");
		    out.println("Content-type: application/x-www-form-urlencoded");
		    out.println("Content-length: "+ str.getBytes().length +"\r\n");
		    out.println(str);
		    out.flush();

		    BufferedReader in = new BufferedReader(
		        new InputStreamReader(
		        socket.getInputStream()));

		    String line;

		    while ((line = in.readLine()) != null) {
		        System.out.println(line);
		    }

		    out.close();
		    in.close();

		} catch (IOException localIOException) {
		    // out.println(localIOException.toString());
		    System.out.println("에러" + localIOException.toString());
		}


		/*
		 * 응답값
		 *
		 *  <성공시>
		 *    "ok|sms|123456789|1"           - 전송결과|발송형태(단문은 sms 장문은 lms 포토문자는 mms)|발송 msgid (예약취소시 필요)|발송건수
		 *
		 *  <실패시>
		 *    "invalid_member"               - 연동서비스 신청이 안 됐거나 없는 아이디
		 *    "under_maintenance"            - 요청시간에 서버점검중인 경우
		 *    "allow_https_only"             - http 요청인 경우
		 *    "invalid_ip"                   - 등록된 접속가능 IP가 아닌 경우
		 *    "invalid_msg"                  - 문자내용에 오류가 있는 경우
		 *    "invalid_names"                - 이름에 오류가 있는 경우
		 *    "invalid_subject"              - 제목에 오류가 있는 경우
		 *    "invalid_sendtime"             - 예약발송 시간에 오류가 있는 경우 (10분이후부터 다음해말까지 가능)
		 *    "invalid_sendtime_maintenance" - 예약발송 시간에 서버점검 예정인 경우
		 *    "invalid_phone"                - 수신번호에 오류가 있는 경우
		 *    "invalid_msg_over_max"         - 문자내용이 너무 긴 경우
		 *    "invalid_callback"             - 발신번호에 오류가 있는 경우
		 *    "once_limit_over"              - 1회 최대 발송건수 초과한 경우
		 *    "daily_limit_over"             - 1일 최대 발송건수 초과한 경우
		 *    "not_enough_point"             - 잔액이 부족한 경우
		 *    "over_use_limit"               - 한달 사용금액을 초과한 경우
		 *    "server_error"                 - 기타 서버 오류
		 */

		
		return "site/sitealarm";
	}
}
