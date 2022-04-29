package com.itsp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.itsp.vo.ScheduleVO;

public class ScheduleImp extends SqlMapClientDaoSupport implements ScheduleDao {

	@Override
	public String seqNextVal() {
		return (String) getSqlMapClientTemplate().queryForObject("record.seqNextVal");
	}

	/*
	 * Config
	 */
	@Override
	public Integer selectCountConfig(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("scheduleConfig.selectCount", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ScheduleVO> selectConfigList(Map<String, Object> params) {
		return (List<ScheduleVO>) getSqlMapClientTemplate().queryForList("scheduleConfig.selectDataList", params);
	}

	@Override
	public ScheduleVO selectConfig(Map<String, Object> params) {
		return (ScheduleVO) getSqlMapClientTemplate().queryForObject("scheduleConfig.selectData", params);
	}

	public void saveProcConfig(ScheduleVO ScheduleVO) {
		getSqlMapClientTemplate().insert("scheduleConfig.saveProcData", ScheduleVO);
	}

	public void deleteProcConfig(String seq) {
		getSqlMapClientTemplate().delete("scheduleConfig.deletePrcoData", seq);
	}

	/*
	 * Record
	 */
	@Override
	public Integer selectCountRecord(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("scheduleRecord.selectCount", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ScheduleVO> selectRecordList(Map<String, Object> params) {
		return (List<ScheduleVO>) getSqlMapClientTemplate().queryForList("scheduleRecord.selectListData", params);
	}

	@Override
	public ScheduleVO selectRecord(Map<String, Object> params) {
		return (ScheduleVO) getSqlMapClientTemplate().queryForObject("scheduleRecord.selectData", params);
	}

	public void saveProcRecord(ScheduleVO ScheduleVO) {
		getSqlMapClientTemplate().insert("scheduleRecord.saveProcData", ScheduleVO);
	}

	public void deleteProcRecord(String seq) {
		getSqlMapClientTemplate().delete("scheduleRecord.deleteProcData", seq);
	}

	public void useProcRecord(Map<String, Object> params) {
		getSqlMapClientTemplate().update("scheduleRecord.useProcData", params);
	}

}
