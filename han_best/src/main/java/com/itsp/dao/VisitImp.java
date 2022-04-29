package com.itsp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.itsp.vo.VisitVO;

public class VisitImp extends SqlMapClientDaoSupport implements VisitDao {

	@Override
	public String seqNextVal() {
		return (String) getSqlMapClientTemplate().queryForObject("record.seqNextVal");
	}

	@Override
	public int totalCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (Integer) getSqlMapClientTemplate().queryForObject("visit.totalCount", params);
	}

	@Override
	public void insertDataProc(Map<String, Object> params) {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().insert("visit.insertDataProc", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VisitVO> selectDataList(Map<String, Object> params) {
		return getSqlMapClientTemplate().queryForList("visit.selectDataList", params);
	}

	@Override
	public VisitVO selectData(Map<String, Object> params) {
		return (VisitVO) getSqlMapClientTemplate().queryForObject("visit.selectData", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VisitVO> selectVisitListData(Map<String, Object> params) {
		return getSqlMapClientTemplate().queryForList("visit.selectVisitListData", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VisitVO> selectVisitListMonthData(Map<String, Object> params) {
		return getSqlMapClientTemplate().queryForList("visit.selectVisitListMonthData", params);
	}

}
