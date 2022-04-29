package com.itsp.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itsp.vo.AttachFiles;
import com.itsp.vo.EtcVO;
import com.itsp.vo.FacilityVO;

public interface FacilityDao {

	public String seqNextVal();

	// config
	public Integer selectCountConfig(Map<String, Object> params);

	public List<FacilityVO> selectConfigList(Map<String, Object> params);

	public FacilityVO selectConfig(Map<String, Object> params);

	public void saveProcConfig(FacilityVO FacilityVO);

	public void deleteProcConfig(String seq);

	// record
	public Integer selectCountRecord(Map<String, Object> params);

	public List<FacilityVO> selectRecordList(Map<String, Object> params);

	public FacilityVO selectRecord(Map<String, Object> params);

	public void saveProcRecord(FacilityVO FacilityVO);
	
	@SuppressWarnings("rawtypes")
	public void orderNumProcRecord(ArrayList<HashMap> dataList) throws Exception;

	public void deleteProcRecord(String seq);

	public void useProcRecord(Map<String, Object> params);

	public List<AttachFiles> selectFileList(String parentSeq);

	public List<EtcVO> selectListData(String recordSeq);
}
