package com.wda.sc.mapper;

import java.util.List;

import com.wda.sc.domain.MemberFileVO;

public interface MemberAttachMapper {

	public int insert(MemberFileVO vo);
	
	public List<MemberFileVO> findByui(String user_id);
}
