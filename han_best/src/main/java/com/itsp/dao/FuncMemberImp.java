package com.itsp.dao;

import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class FuncMemberImp extends SqlMapClientDaoSupport implements FuncMemberDao {

	@Override
	public String functionCheck(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (String) getSqlMapClientTemplate().queryForObject("funcDb.functionCheck", params);
	}

	@Override
	public void functionUpdate(Map<String, Object> params) {
		getSqlMapClientTemplate().update("funcDb.functionUpdate", params);
	}

}
