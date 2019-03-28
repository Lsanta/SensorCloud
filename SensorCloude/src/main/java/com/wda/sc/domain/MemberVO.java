package com.wda.sc.domain;

import lombok.Data;

@Data
public class MemberVO {
	private String user_id;
	private String name;
	private String password;
	private String phone;
	private String email;
	private int m_level;
}
