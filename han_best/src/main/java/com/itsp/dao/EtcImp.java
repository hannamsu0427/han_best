package com.itsp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.itsp.vo.EtcVO;

public class EtcImp extends SqlMapClientDaoSupport implements EtcDao {

	@Override
	public void insertProcData(Map<String, Object> params) {
		getSqlMapClientTemplate().insert("etc.insertProcData", params);
	}

	@Override
	public void deleteProcData(String seq) {
		getSqlMapClientTemplate().delete("etc.deleteProcData", seq);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EtcVO> selectListData(String recordSeq) {
		return getSqlMapClientTemplate().queryForList("etc.selectListData", recordSeq);
	}

}
