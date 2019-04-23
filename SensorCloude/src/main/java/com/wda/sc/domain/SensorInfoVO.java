package com.wda.sc.domain;

import lombok.Data;

@Data
public class SensorInfoVO {
	private int sensor_info_n;
	private String sensor_sn;
	private String data_type;
	private String sensor_name;
	private String sensor_mark;
	
	private String sensor_kind;
}
