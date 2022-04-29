package com.itsp.dao;

import java.util.Map;

import com.itsp.vo.SwearWordVO;

public interface SwearWordDao {

	public String seqNextVal();

	public int totalCount(Map<String, Object> params);

	public SwearWordVO selectData(Map<String, Object> params);

	public void insertDataProc(SwearWordVO vo);

	public void updateDataProc(SwearWordVO vo);
}
