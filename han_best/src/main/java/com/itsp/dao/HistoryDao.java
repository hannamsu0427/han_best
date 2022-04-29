package com.itsp.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itsp.vo.HistoryVO;

public interface HistoryDao {

	public String seqNextVal();

	// config
	public Integer selectCountConfig(Map<String, Object> params);

	public List<HistoryVO> selectConfigList(Map<String, Object> params);

	public HistoryVO selectConfig(Map<String, Object> params);

	public void saveProcConfig(HistoryVO HistoryVO);

	public void deleteProcConfig(String seq);

	// category
	public Integer selectCountCategory(Map<String, Object> params);

	public List<HistoryVO> selectCategoryList(Map<String, Object> params);

	public HistoryVO selectCategory(Map<String, Object> params);

	public void saveProcCategory(HistoryVO HistoryVO);

	public void deleteProcCategory(String categorySeq);

	@SuppressWarnings("rawtypes")
	public void orderNumProcCategory(ArrayList<HashMap> dataList) throws Exception;

	// record
	public Integer selectCountRecord(Map<String, Object> params);

	public List<HistoryVO> selectRecordList(Map<String, Object> params);

	public HistoryVO selectRecord(Map<String, Object> params);

	public void saveProcRecord(HistoryVO HistoryVO);

	public void deleteProcRecord(String seq);
	
	public void useProcRecord(Map<String, Object> params);

}
