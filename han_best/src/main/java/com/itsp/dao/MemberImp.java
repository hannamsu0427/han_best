package com.itsp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.itsp.vo.MemberVO;

public class MemberImp extends SqlMapClientDaoSupport implements MemberDao {

	@Override
	public void insertDataProc(Map<String, Object> params) {
		getSqlMapClientTemplate().insert("member.insertDataProc", params);
	}

	@Override
	public void deleteDataProc(String user_id) {
		getSqlMapClientTemplate().delete("member.deleteDataProc", user_id);
	}

	@Override
	public int totalCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (Integer) getSqlMapClientTemplate().queryForObject("member.totalCount", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemberVO> selectDataList(Map<String, Object> params) {
		return getSqlMapClientTemplate().queryForList("member.selectDataList", params);
	}

	@Override
	public MemberVO selectData(Map<String, Object> params) {
		return (MemberVO) getSqlMapClientTemplate().queryForObject("member.selectData", params);
	}

	@Override
	public void insertRegActionProc(Map<String, Object> params) {
		getSqlMapClientTemplate().insert("member.insertRegActionProc", params);
	}

	@Override
	public String failCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (String) getSqlMapClientTemplate().queryForObject("member.failCount", params);
	}

	@Override
	public void insertFailCountProc(Map<String, Object> params) {
		getSqlMapClientTemplate().insert("member.insertFailCountProc", params);
	}

	@Override
	public void updateFailCountProc(Map<String, Object> params) {
		getSqlMapClientTemplate().update("member.updateFailCountProc", params);
	}

}
