package com.itsp.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.itsp.vo.AttachFiles;
import com.itsp.vo.PopUpVO;

public class PopUpImp extends SqlMapClientDaoSupport implements PopUpDao {

	@Override
	public String seqNextVal() {
		return (String) getSqlMapClientTemplate().queryForObject("record.seqNextVal");
	}

	// data

	@Override
	public int totalCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (Integer) getSqlMapClientTemplate().queryForObject("popUp.totalCount", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PopUpVO> selectDataList(Map<String, Object> params) {
		List<PopUpVO> voList = getSqlMapClientTemplate().queryForList("popUp.selectDataList", params);
		List<PopUpVO> resultList = new ArrayList<PopUpVO>();

		for (PopUpVO vo : voList) {
			List<AttachFiles> attachFile = selectFileList(vo.getSeq());
			vo.setAttachFileList(attachFile);
			resultList.add(vo);
		}
		return resultList;
	}

	@Override
	public PopUpVO selectData(Map<String, Object> params) {
		PopUpVO vo = (PopUpVO) getSqlMapClientTemplate().queryForObject("popUp.selectData", params);
		if (vo != null) {
			List<AttachFiles> attachFile = selectFileList(vo.getSeq());
			vo.setAttachFileList(attachFile);
		}
		return vo;
	}

	@Override
	public void insertDataProc(PopUpVO vo) {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().insert("popUp.insertDataProc", vo);
	}

	@Override
	public void updateDataProc(PopUpVO vo) {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().update("popUp.updateDataProc", vo);
	}

	@Override
	public void deleteDataProc(Map<String, Object> params) {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().delete("popUp.deleteDataProc", params);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void orderNumProc(ArrayList<HashMap> dataList) throws Exception {
		try {
			int inx;
			for (inx = 0; inx < dataList.size(); inx++) {
				HashMap<String, String> map = (HashMap) dataList.get(inx);
				getSqlMapClientTemplate().update("popUp.orderNumProc", map);
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
