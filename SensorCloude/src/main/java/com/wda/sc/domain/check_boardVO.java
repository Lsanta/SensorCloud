package com.wda.sc.domain;

import java.util.Date;

import lombok.Data;

@Data
public class check_boardVO {
	private int board_no;
	private String user_id;
	private Date reg_date;
	private String title;
	private String board_content;
	private int board_status;
	private String site_id;
}
