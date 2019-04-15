package com.wda.sc.domain;


import java.util.List;

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
	private String board_file_id;
	private String file_path;
	private String file_name;
	
	private List<CheckBoardFileVO> attachList;
}
