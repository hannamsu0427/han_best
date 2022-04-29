package com.itsp.dao;

import java.util.List;
import java.util.Map;

import com.itsp.vo.MemberVO;

public interface MemberDao {

	public void insertDataProc(Map<String, Object> params);

	public void deleteDataProc(String user_id);

	public int totalCount(Map<String, Object> params);

	public MemberVO selectData(Map<String, Object> params);

	public List<MemberVO> selectDataList(Map<String, Object> params);

	public void insertRegActionProc(Map<String, Object> params);

	public String failCount(Map<String, Object> params);

	public void insertFailCountProc(Map<String, Object> params);

	public void updateFailCountProc(Map<String, Object> params);

}
