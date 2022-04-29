package com.itsp.dao;

import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.itsp.vo.MajorVO;

public class ApplicationImp extends SqlMapClientDaoSupport implements ApplicationDao {

	@Override
	public String seqNextVal() {
		return (String) getSqlMapClientTemplate().queryForObject("record.seqNextVal");
	}

	@Override
	public Integer selectCountData(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("application.selectCount", params);
	}

	@Override
	public MajorVO selectData(Map<String, Object> params) {
		return (MajorVO) getSqlMapClientTemplate().queryForObject("application.selectData", params);
	}

	public void saveProcData(MajorVO MajorVO) {
		getSqlMapClientTemplate().insert("application.saveProcData", MajorVO);
	}

	public void deleteProcData(String seq) {
		getSqlMapClientTemplate().delete("application.deletePrcoData", seq);
	}
}
