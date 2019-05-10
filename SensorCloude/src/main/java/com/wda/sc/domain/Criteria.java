package com.wda.sc.domain;

public class Criteria {
	   private int totalcount;   //전체 게시물 개수
	   private int pagenum;   //현재 페이지 번호
	   private int contentnum=10;   //한 페이지에 몇개를 표시하는가
	   private int startPage=1;   //현재 페이지 블록의 시작 페이지
	   private int endPage=5;   //현재 페이지 블록의 마지막 페이지
	   private int startnum;   //현재 페이지의 컨텐츠 시작 번호
	   private int endnum;   //현재 페이지의 컨텐츠 끝 번호
	   private boolean prev=false;   //이전 페이지로 가는 화살표
	   private boolean next;   //다음 페이지로 가는 화살표
	   private int currentblock;   //현재 페이지 블록
	   private int lastblock;   //마지막 페이지 블록
	   
	   public void prevnext(int pagenum) {
	      if(getLastblock()==1&&pagenum<6) {
	         setPrev(false);
	         setNext(false);
	      }
	      else if(getLastblock()>1&&pagenum<6) {
	         setPrev(false);
	         setNext(true);
	      }
	      else if(getLastblock()==getCurrentblock()) {
	         setPrev(true);
	         setNext(false);
	      }else {
	         setPrev(true);
	         setNext(true);
	      }
	   }
	   
	   //전체 페이지 수를 계산하는 함수
	   public int calcpage(int totalcount, int contentnum) {
	      //예) 총 게시물 125, 한 페이지에 표시 10 -> 125/10 -> 12.5(if해서 13페이지)
	      int totalpage = totalcount / contentnum;
	      if(totalcount%contentnum>0) {
	         totalpage++;
	      }
	      return totalpage;
	   }
	   
	   //컨텐츠 시작번호 설정 -> (현재페이지-1)*보여줄 컨텐츠 수 +1
	   public int getStartnum() {
	      return startnum;
	   }
	   public void setStartnum(int pagenum) {
	      this.startnum = (pagenum-1)*this.contentnum+1;
	   }
	   
	   //컨텐츠 끝번호 설정 -> 현재페이지 * 보여줄 컨텐츠 수
	   public int getEndnum() {
	      return endnum;
	   }
	   public void setEndnum(int pagenum) {
	      this.endnum = pagenum*this.contentnum;
	   }
	   
	   //전체 게시물 수를 구하는 함수 : 사용하는 이유 -> 전체 게시물 수를 구하여 전체 페이지 수를 구하고 페이지블록을 계산하기 위해
	   public int getTotalcount() {
	      return totalcount;
	   }
	   public void setTotalcount(int totalcount) {
	      this.totalcount = totalcount;
	   }
	   
	   //현재 페이지를 구하는 함수
	   public int getPagenum() {
	      return pagenum;
	   }
	   public void setPagenum(int pagenum) {
	      this.pagenum = pagenum;
	   }
	   
	   //한 페이지에 몇개를 출력할것인지 설정하는 함수
	   public int getContentnum() {
	      return contentnum;
	   }
	   public void setContentnum(int contentnum) {
	      this.contentnum = contentnum;
	   }
	   
	   //시작 페이지를 구하는 함수
	   public int getStartPage() {
	      return startPage;
	   }
	   public void setStartPage(int currentblock) {
	      //10은 페이지 블록당 보여줄 페이지 수
	      //페이지 블록 1 -> 1*10-9 -> 1페이지
	      //페이지 블록 2 -> 2*10-9 -> 11페이지
	      this.startPage = (currentblock*5)-4;
	   }
	   
	   //끝 페이지를 구하는 함수
	   public int getEndPage() {
	      return endPage;
	   }
	   public void setEndPage(int getlastblock,int getcurrentblock) {
	      //getlastblock 마지막 페이지 블록 번호 / getcurrentblock 현재 페이지 블록 번호
	      if(getlastblock == getcurrentblock) {
	         this.endPage = calcpage(getTotalcount(),getContentnum());
	      }
	      else {
	         this.endPage = getStartPage()+4;
	      }
	   }
	   
	   //이전 페이지 설정
	   public boolean isPrev() {
	      return prev;
	   }
	   public void setPrev(boolean prev) {
	      this.prev = prev;
	   }
	   
	   //다음 페이지 설정
	   public boolean isNext() {
	      return next;
	   }
	   public void setNext(boolean next) {
	      this.next = next;
	   }
	   
	   //현재 페이지 블록 : 사용하는 이유 -> 현재 페이지 블록을 구하여 이전,다음 화살표를 제어하기 위해
	   public int getCurrentblock() {
	      return currentblock;
	   }
	   public void setCurrentblock(int pagenum) {
	      //페이지 번호를 통해 구한다.
	      //현재 페이지 번호 1 -> 1/10 -> 0 페이지 블록 번호는 1이 된다. 
	      //현재 페이지 번호 11 -> 11/10 -> 1 페이지 블록 번호는 2가 된다.
	      this.currentblock = pagenum/5;
	      if(pagenum%5>0) {
	         this.currentblock++;
	      }
	   }
	   
	   //마지막 페이지 블록   : 사용하는 이유 -> 현재 페이지 블록이 마지막 페이지 블록이면 다음 화살표를 보이지 않도록 하기 위해
	   public int getLastblock() {
	      return lastblock;
	   }
	   public void setLastblock(int totalcount) {
	      //10 , 10 -> 10*10 -> 100
	      //125/100
	      //1
	      this.lastblock = totalcount / (5*this.contentnum);
	      if(totalcount %(5*this.contentnum)>0) {
	         this.lastblock++;
	      }
	   }
	}