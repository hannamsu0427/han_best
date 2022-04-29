package com.itsp.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.itsp.vo.HistoryVO;

public class HistoryImp extends SqlMapClientDaoSupport implements HistoryDao {

	@Override
	public String seqNextVal() {
		return (String) getSqlMapClientTemplate().queryForObject("record.seqNextVal");
	}

	/*
	 * Config
	 */
	@Override
	public Integer selectCountConfig(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("historyConfig.selectCount", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HistoryVO> selectConfigList(Map<String, Object> params) {
		return (List<HistoryVO>) getSqlMapClientTemplate().queryForList("historyConfig.selectDataList", params);
	}

	@Override
	public HistoryVO selectConfig(Map<String, Object> params) {
		return (HistoryVO) getSqlMapClientTemplate().queryForObject("historyConfig.selectData", params);
	}

	public void saveProcConfig(HistoryVO HistoryVO) {
		getSqlMapClientTemplate().insert("historyConfig.saveProcData", HistoryVO);
	}

	public void deleteProcConfig(String seq) {
		getSqlMapClientTemplate().delete("historyConfig.deletePrcoData", seq);
	}

	/*
	 * Category
	 */
	@Override
	public Integer selectCountCategory(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("historyCategory.selectCount", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HistoryVO> selectCategoryList(Map<String, Object> params) {
		return (List<HistoryVO>) getSqlMapClientTemplate().queryForList("historyCategory.selectListData", params);
	}

	@Override
	public HistoryVO selectCategory(Map<String, Object> params) {
		return (HistoryVO) getSqlMapClientTemplate().queryForObject("historyCategory.selectData", params);
	}

	public void saveProcCategory(HistoryVO HistoryVO) {
		getSqlMapClientTemplate().insert("historyCategory.saveProcData", HistoryVO);
	}

	public void deleteProcCategory(String categorySeq) {
		getSqlMapClientTemplate().delete("historyCategory.deleteProcData", categorySeq);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void orderNumProcCategory(ArrayList<HashMap> dataList) throws Exception {
		try {
			int inx;
			for (inx = 0; inx < dataList.size(); inx++) {
				HashMap<String, String> map = (HashMap) dataList.get(inx);
				getSqlMapClientTemplate().update("historyCategory.orderNumProc", map);
			}
		} catch (Throwable t) {
			throw (Exception) t;
		}
		return;
	}
	
	/*
	 *  Record
	 */
	@Override
	public Integer selectCountRecord(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("historyRecord.selectCount", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HistoryVO> selectRecordList(Map<String, Object> params) {
		return (List<HistoryVO>) getSqlMapClientTemplate().queryForList("historyRecord.selectListData", params);
	}

	@Override
	public HistoryVO selectRecord(Map<String, Object> params) {
		return (HistoryVO) getSqlMapClientTemplate().queryForObject("historyRecord.selectData", params);
	}

	public void saveProcRecord(HistoryVO HistoryVO) {
		getSqlMapClientTemplate().insert("historyRecord.saveProcData", HistoryVO);
	}

	public void deleteProcRecord(String seq) {
		getSqlMapClientTemplate().delete("historyRecord.deleteProcData", seq);
	}
	
	public void useProcRecord(Map<String, Object> params) {
		getSqlMapClientTemplate().update("historyRecord.useProcData", params);
	}

}
