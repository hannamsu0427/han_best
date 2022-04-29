package com.itsp.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.itsp.vo.AttachFiles;
import com.itsp.vo.VisualVO;

public class VisualImp extends SqlMapClientDaoSupport implements VisualDao {

	@Override
	public String seqNextVal() {
		return (String) getSqlMapClientTemplate().queryForObject("record.seqNextVal");
	}

	// data

	@Override
	public int totalCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (Integer) getSqlMapClientTemplate().queryForObject("visual.totalCount", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VisualVO> selectDataList(Map<String, Object> params) {
		List<VisualVO> voList = getSqlMapClientTemplate().queryForList("visual.selectDataList", params);
		List<VisualVO> resultList = new ArrayList<VisualVO>();

		for (VisualVO vo : voList) {
			String parentSeq = vo.getSeq();
			List<AttachFiles> attachFile = selectFileList(parentSeq);
			vo.setAttachFileList(attachFile);
			resultList.add(vo);
		}
		return resultList;
	}

	@Override
	public VisualVO selectData(Map<String, Object> params) {
		VisualVO vo = (VisualVO) getSqlMapClientTemplate().queryForObject("visual.selectData", params);
		if (vo != null) {
			String parentSeq = vo.getSeq();
			List<AttachFiles> attachFile = selectFileList(parentSeq);
			vo.setAttachFileList(attachFile);
		}
		return vo;
	}

	@Override
	public void insertDataProc(VisualVO vo) {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().insert("visual.insertDataProc", vo);
	}

	@Override
	public void updateDataProc(VisualVO vo) {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().update("visual.updateDataProc", vo);
	}

	@Override
	public void deleteDataProc(Map<String, Object> params) {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().delete("visual.deleteDataProc", params);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void orderNumProc(ArrayList<HashMap> dataList) throws Exception {
		try {
			int inx;
			for (inx = 0; inx < dataList.size(); inx++) {
				HashMap<String, String> map = (HashMap) dataList.get(inx);
				getSqlMapClientTemplate().update("visual.orderNumProc", map);
			}
		} catch (Throwable t) {
			throw (Exception) t;
		}
		return;
	}

	/*
	 * file
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AttachFiles> selectFileList(String parentSeq) {
		return getSqlMapClientTemplate().queryForList("attachFile.selectDataList", parentSeq);
	}

	@Override
	public AttachFiles selectFile(String seq) {
		return (AttachFiles) getSqlMapClientTemplate().queryForObject("attachFile.selectData", seq);
	}
}
