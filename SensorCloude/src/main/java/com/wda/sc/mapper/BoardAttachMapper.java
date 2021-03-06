package com.wda.sc.mapper;

import java.util.List;

import com.wda.sc.domain.CheckBoardFileVO;

public interface BoardAttachMapper {
	
	public void insert(CheckBoardFileVO vo);
	
	public List<CheckBoardFileVO> findByBno(int board_no);
	
	public void deleteAll(int board_no);
	
	public List<CheckBoardFileVO> findByBnomain(int board_no);
	
	public void mfileinsert(CheckBoardFileVO checkboardfilevo);
	
	 public List<String> findFilenameByBno(int board_no);

}
