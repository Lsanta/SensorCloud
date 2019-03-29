package com.wda.sc.domain;


import lombok.Data;

@Data
public class CheckBoardVO {
	private int board_no;
	private String user_id;
	private String reg_date;
	private String title;
	private String board_content;
	private int board_status;
	private String site_id;
	private String site_name; // site join 컬럼
	private String name;
}