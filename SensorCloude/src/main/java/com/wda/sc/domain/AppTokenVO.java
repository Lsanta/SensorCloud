package com.wda.sc.domain;

import lombok.Data;

@Data
public class AppTokenVO {
	private String token_id;
	private String user_id;
	private String ex_date;
}
