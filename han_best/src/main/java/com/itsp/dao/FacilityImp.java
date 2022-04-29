package com.itsp.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.itsp.vo.AttachFiles;
import com.itsp.vo.EtcVO;
import com.itsp.vo.FacilityVO;

public class FacilityImp extends SqlMapClientDaoSupport implements FacilityDao {

	@Override
	public String seqNextVal() {
		return (String) getSqlMapClientTemplate().queryForObject("record.seqNextVal");
	}

	/*
	 * Config
	 */
	@Override
	public Integer selectCountConfig(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("facilityConfig.selectCount", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FacilityVO> selectConfigList(Map<String, Object> params) {
		return (List<FacilityVO>) getSqlMapClientTemplate().queryForList("facilityConfig.selectDataList", params);
	}

	@Override
	public FacilityVO selectConfig(Map<String, Object> params) {
		return (FacilityVO) getSqlMapClientTemplate().queryForObject("facilityConfig.selectData", params);
	}

	public void saveProcConfig(FacilityVO FacilityVO) {
		getSqlMapClientTemplate().insert("facilityConfig.saveProcData", FacilityVO);
	}

	public void deleteProcConfig(String seq) {
		getSqlMapClientTemplate().delete("facilityConfig.deletePrcoData", seq);
	}

	/*
	 * Record
	 */
	@Override
	public Integer selectCountRecord(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("facilityRecord.selectCount", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FacilityVO> selectRecordList(Map<String, Object> params) {
		List<FacilityVO> voList = getSqlMapClientTemplate().queryForList("facilityRecord.selectListData", params);
		List<FacilityVO> resultList = new ArrayList<FacilityVO>();

		for (FacilityVO vo : voList) {
			List<AttachFiles> attachFile = selectFileList(vo.getSeq());
			vo.setAttachFileList(attachFile);
			
			List<EtcVO> etc = selectListData(vo.getSeq());
			vo.setEtcList(etc);
			
			resultList.add(vo);
		}

		return resultList;
	}

	@Override
	public FacilityVO selectRecord(Map<String, Object> params) {
		FacilityVO vo = (FacilityVO) getSqlMapClientTemplate().queryForObject("facilityRecord.selectData", params);
		if (vo != null) {
			List<AttachFiles> attachFile = selectFileList(vo.getSeq());
			vo.setAttachFileList(attachFile);
			
			List<EtcVO> etc = selectListData(vo.getSeq());
			vo.setEtcList(etc);
		}
		return vo;
	}

	public void saveProcRecord(FacilityVO FacilityVO) {
		getSqlMapClientTemplate().insert("facilityRecord.saveProcData", FacilityVO);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void orderNumProcRecord(ArrayList<HashMap> dataList) throws Exception {
		try {
			int inx;
			for (inx = 0; inx < dataList.size(); inx++) {
				HashMap<String, String> map = (HashMap) dataList.get(inx);
				getSqlMapClientTemplate().update("facilityRecord.orderNumProc", map);
			}
		} catch (Throwable t) {
			throw (Exception) t;
		}
		return;
	}

	public void deleteProcRecord(String seq) {
		getSqlMapClientTemplate().delete("facilityRecord.deleteProcData", seq);
	}

	public void useProcRecord(Map<String, Object> params) {
		getSqlMapClientTemplate().update("facilityRecord.useProcData", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AttachFiles> selectFileList(String parentSeq) {
		return getSqlMapClientTemplate().queryForList("attachFile.selectDataList", parentSeq);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EtcVO> selectListData(String recordSeq) {
		return getSqlMapClientTemplate().queryForList("etc.selectListData", recordSeq);
	}

}
