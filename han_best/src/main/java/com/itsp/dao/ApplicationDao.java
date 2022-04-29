package com.itsp.dao;

import java.util.Map;

import com.itsp.vo.MajorVO;

public interface ApplicationDao {

	public String seqNextVal();

	public Integer selectCountData(Map<String, Object> params);

	public MajorVO selectData(Map<String, Object> params);

	public void saveProcData(MajorVO MajorVO);

	public void deleteProcData(String seq);
		
}
