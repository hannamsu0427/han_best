package com.itsp.dao;

import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.itsp.vo.GoogleAnalyticsVO;

public class GoogleAnalyticsImp extends SqlMapClientDaoSupport implements GoogleAnalyticsDao {

	@Override
	public String seqNextVal() {
		return (String) getSqlMapClientTemplate().queryForObject("record.seqNextVal");
	}

	@Override
	public int totalCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (Integer) getSqlMapClientTemplate().queryForObject("googleAnalytics.totalCount", params);
	}

	@Override
	public void insertDataProc(GoogleAnalyticsVO vo) {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().insert("googleAnalytics.insertDataProc", vo);
	}

	@Override
	public void updateDataProc(GoogleAnalyticsVO vo) {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().update("googleAnalytics.updateDataProc", vo);
	}

	@Override
	public GoogleAnalyticsVO selectData(Map<String, Object> params) {
		return (GoogleAnalyticsVO) getSqlMapClientTemplate().queryForObject("googleAnalytics.selectData", params);
	}
}
