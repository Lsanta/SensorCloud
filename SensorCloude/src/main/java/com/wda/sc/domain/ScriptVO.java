package com.wda.sc.domain;

import java.util.Date;

import lombok.Data;

@Data
public class ScriptVO {
	private String script_id;
	private int site_id;
	private Date script_date;
	private String script_content;
	private String file_path;
}
