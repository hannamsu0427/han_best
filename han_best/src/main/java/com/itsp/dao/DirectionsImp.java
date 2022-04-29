package com.itsp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.itsp.vo.DirectionsVO;

public class DirectionsImp extends SqlMapClientDaoSupport implements DirectionsDao {

	@Override
	public String seqNextVal() {
		return (String) getSqlMapClientTemplate().queryForObject("record.seqNextVal");
	}

	@Override
	public Integer selectCountRecord(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("directions.selectCount", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DirectionsVO> selectRecordList(Map<String, Object> params) {
		return (List<DirectionsVO>) getSqlMapClientTemplate().queryForList("directions.selectListData", params);
	}

	@Override
	public DirectionsVO selectRecord(Map<String, Object> params) {
		return (DirectionsVO) getSqlMapClientTemplate().queryForObject("directions.selectData", params);
	}

	public void saveProcRecord(DirectionsVO DirectionsVO) {
		getSqlMapClientTemplate().insert("directions.saveProcData", DirectionsVO);
	}

	public void deleteProcRecord(String seq) {
		getSqlMapClientTemplate().delete("directions.deleteProcData", seq);
	}

}
