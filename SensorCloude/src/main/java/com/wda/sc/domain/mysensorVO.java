package com.wda.sc.domain;

import lombok.Data;

@Data
public class mysensorVO {
	private String sensor_sn;
	private String model_name;
	private String sensor_kind;
	private String manufacturer;
	private String voltage;
}