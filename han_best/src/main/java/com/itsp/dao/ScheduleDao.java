package com.itsp.dao;

import java.util.List;
import java.util.Map;

import com.itsp.vo.ScheduleVO;

public interface ScheduleDao {

	public String seqNextVal();

	// config
	public Integer selectCountConfig(Map<String, Object> params);

	public List<ScheduleVO> selectConfigList(Map<String, Object> params);

	public ScheduleVO selectConfig(Map<String, Object> params);

	public void saveProcConfig(ScheduleVO ScheduleVO);

	public void deleteProcConfig(String seq);

	// record
	public Integer selectCountRecord(Map<String, Object> params);

	public List<ScheduleVO> selectRecordList(Map<String, Object> params);

	public ScheduleVO selectRecord(Map<String, Object> params);

	public void saveProcRecord(ScheduleVO ScheduleVO);

	public void deleteProcRecord(String seq);

	public void useProcRecord(Map<String, Object> params);

}
