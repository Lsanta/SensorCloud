package com.wda.sc.domain;


import lombok.Data;

@Data
public class MemberFileVO {
	private String uuid;
	private String file_path;
	private String file_name;
	private String user_id;
	private boolean filetype;
	

}	
