package com.itsp.dao;

import java.util.List;
import java.util.Map;

import com.itsp.vo.MemberVO;

public interface HjMemberDao {

	public int totalCount(Map<String, Object> params);

	public List<MemberVO> selectDataList(Map<String, Object> params);

	public MemberVO selectData(Map<String, Object> params);

}
