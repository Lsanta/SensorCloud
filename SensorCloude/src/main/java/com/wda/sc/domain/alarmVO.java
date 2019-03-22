package com.wda.sc.domain;

import java.util.Date;

import lombok.Data;

@Data
public class alarmVO {
	private int alarm_no;
	private String site_id;
	private String alarm_content;
	private Date alarm_time;
	private String send_user;
}
