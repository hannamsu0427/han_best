package com.itsp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.itsp.vo.MemberVO;

public class HjMemberImp extends SqlMapClientDaoSupport implements HjMemberDao {

	@Override
	public int totalCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (Integer) getSqlMapClientTemplate().queryForObject("SQL_Hjdb.totalCount", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemberVO> selectDataList(Map<String, Object> params) {
		return getSqlMapClientTemplate().queryForList("SQL_Hjdb.selectDataList", params);
	}

	@Override
	public MemberVO selectData(Map<String, Object> params) {
		return (MemberVO) getSqlMapClientTemplate().queryForObject("SQL_Hjdb.selectData", params);
	}

}
