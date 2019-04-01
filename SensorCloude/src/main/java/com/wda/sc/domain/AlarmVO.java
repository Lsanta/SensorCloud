package com.wda.sc.domain;

import lombok.Data;

@Data
public class AlarmVO {
	private int alarm_no;
	private String site_id;
	private String alarm_content;
	private String alarm_time;
	private String send_user;
}
