package com.wda.sc;

import java.io.IOException;

import javax.servlet.http.HttpServlet;

public class TestService extends HttpServlet{
	
	public TestService() throws IOException, InterruptedException {
		String[] args = null;
		main(args);
	}
	
	public static void main(String[] args) throws IOException, InterruptedException{
        // 5개의 쓰레드를 생성하는 서버를 생성한다.
		Process p = null;
		try {
		    p =Runtime.getRuntime().exec("C:\\Users\\user\\Desktop\\센서측정 exe\\ConsoleApp1.exe");
		    
		    System.out.println("실행됬음");

		  } catch (Exception e) {
		    System.err.println(e);
		  }finally {
			p.waitFor();
		}
    }
}

