package com.wda.sc;

import java.util.ArrayList;
import java.util.Map;

import com.wda.sc.domain.Paging;

public class PagingDAO {

//	 public Map<String, ArrayList<Object>> getPageList(String num, Object obj) {
//		 
//			Paging page = new Paging();
//			int pageNum = 0;
//			Object o = obj+".getPageNum()";
//			ArrayList<Integer> arr = new ArrayList<Integer>();
//			int realNum = Integer.parseInt(num);
//			
//			page.setTotalNum(o);
//			
//			if(page.getTotalNum() < page.getOnePageBoard() ) {
//				pageNum = 1;
//			}else {
//				pageNum = page.getTotalNum()/page.getOnePageBoard();
//			}
//
//			for(int i = 0; i < pageNum; i ++) {
//				arr.add(i+1);
//			}
//
//			page.setEndnum((realNum*10)+1);
//			page.setStartnum(page.getEndnum()-10);
//
//		return ;
//	}
}
