package com.wda.sc.domain;

import lombok.Data;

@Data
public class CheckBoardFileVO {
	private int board_no;
	private String uuid;
	private String file_Path;
	private String file_name;
	private boolean fileType;
}
