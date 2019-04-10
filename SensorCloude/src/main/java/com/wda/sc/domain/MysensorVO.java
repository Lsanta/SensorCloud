package com.wda.sc.domain;

import lombok.Data;

@Data
public class MysensorVO {
	private String sensor_sn;
	private String model_name;
	private String sensor_kind;
	private String manufacturer;
	private String voltage;
	private String site_name;
	private String create_day;
	//임시
	private int count;
}
