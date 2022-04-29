package com.itsp.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.itsp.vo.AttachFiles;

public class AttachFileImp extends SqlMapClientDaoSupport implements AttachFileDao {

	@Override
	public String seqNextVal() {
		return (String) getSqlMapClientTemplate().queryForObject("attachFile.seqNextVal");
	}

	@Override
	public void insertFileProc(AttachFiles attachFile) {
		getSqlMapClientTemplate().insert("attachFile.insertDataProc", attachFile);
	}

	@Override
	public void updateFileProc(AttachFiles attachFile) {
		getSqlMapClientTemplate().update("attachFile.updateDataProc", attachFile);
	}

	@Override
	public void deleteFileProc(String seq) {
		getSqlMapClientTemplate().delete("attachFile.deleteDataProc", seq);
	}

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
