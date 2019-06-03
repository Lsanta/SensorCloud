package com.wda.sc.domain;

import lombok.Data;

@Data
public class AlarmMemberVO {
	private int alarm_m_no;
	private int site_id;
	private String tel;
	private String name;
	private String company;
	
	private String user_id;
}
