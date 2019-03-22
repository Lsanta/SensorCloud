package com.wda.sc.domain;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class timelineVO {
	private String timeline_n;
	private String user_id;
	private String content;
	private Timestamp time;
}
