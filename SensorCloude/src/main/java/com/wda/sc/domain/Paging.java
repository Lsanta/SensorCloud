package com.wda.sc.domain;

import lombok.Data;

@Data
public class Paging {

	private int totalNum;
	private int onePageBoard = 10;
	private int nowPageNum;
	
	private int startnum;
	private int endnum;

}
