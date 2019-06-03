package com.wda.sc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Cmd {

	public ArrayList<String> exeCmd(String cmd) {
		ArrayList<String> arr = new ArrayList<String>();
		String commend[] = new String[1]; // DOS명령어 String 배열로 입력
        commend[0] = cmd;
        
        runCommandAsAdmin(commend[0]);
        
        ArrayList<String> output = new ArrayList<>();
		String query = "tasklist /fi \"imagename eq ConsoleApp1.exe\"";
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("cmd /c "+query).getInputStream(), "EUC-KR"));
			String i; 
			
			while((i=br.readLine())!=null){ 
				output.add(i); 
			}
			
			for(int x = 0; x < output.size(); x++) {		
				if(x > 2) {
					String pid = output.get(x).trim().replaceAll(" +", " ").split(" ")[1];
					arr.add(pid);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
        return arr;
	}
	public void runCommandAsAdmin(String command) {
		Elevator.executeAsAdmin("c:\\windows\\system32\\cmd.exe", "/C " + command);
	}
}
