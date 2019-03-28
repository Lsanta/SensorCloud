package com.wda.sc.domain;

import java.sql.Timestamp;


import lombok.Data;

@Data
public class TimelineVO {
	private String timeline_n;
	private String user_id;
	private String content;
	private String time;
	private String name; // member 테이블 join 컬럼
}
