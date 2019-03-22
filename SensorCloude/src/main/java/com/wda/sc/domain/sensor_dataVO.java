package com.wda.sc.domain;

import java.util.Date;

import lombok.Data;

@Data
public class sensor_dataVO {
	private int sensor_data_n;
	private int sensor_info_n;
	private String sensor_id;
	private Date rx_time;
	private String sensing_data;
}
