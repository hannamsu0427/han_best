package com.itsp.dao;

import java.util.Map;

import com.itsp.vo.GoogleAnalyticsVO;

public interface GoogleAnalyticsDao {

	public String seqNextVal();

	public int totalCount(Map<String, Object> params);

	public GoogleAnalyticsVO selectData(Map<String, Object> params);

	public void insertDataProc(GoogleAnalyticsVO vo);

	public void updateDataProc(GoogleAnalyticsVO vo);
}
