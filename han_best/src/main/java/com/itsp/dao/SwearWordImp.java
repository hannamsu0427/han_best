package com.itsp.dao;

import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.itsp.vo.SwearWordVO;

public class SwearWordImp extends SqlMapClientDaoSupport implements SwearWordDao {

	@Override
	public String seqNextVal() {
		return (String) getSqlMapClientTemplate().queryForObject("record.seqNextVal");
	}

	@Override
	public int totalCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (Integer) getSqlMapClientTemplate().queryForObject("swearWord.totalCount", params);
	}

	@Override
	public void insertDataProc(SwearWordVO vo) {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().insert("swearWord.insertDataProc", vo);
	}

	@Override
	public void updateDataProc(SwearWordVO vo) {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().update("swearWord.updateDataProc", vo);
	}

	@Override
	public SwearWordVO selectData(Map<String, Object> params) {
		return (SwearWordVO) getSqlMapClientTemplate().queryForObject("swearWord.selectData", params);
	}
}
