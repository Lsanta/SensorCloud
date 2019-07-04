package com.wda.sc.domain;

import java.util.List;

import lombok.Data;

@Data
public class MemberVO {
	private String user_id;
	private String name;
	private String password;
	private String phone;
	private String email;
	private int m_level;
	private int re_level;
	private String key;
	private String company;
	

	private int company_num;
	private String cname;
	
	private String uuid;
	private String file_path;
	private String file_name;
	private boolean filetype;
	
	private List<MemberFileVO> attachList;
	
}
