package com.itsp.dao;

import java.util.Map;

public interface FuncMemberDao {

	public String functionCheck(Map<String, Object> params);

	public void functionUpdate(Map<String, Object> params);

}
