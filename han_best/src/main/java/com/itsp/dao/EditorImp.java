package com.itsp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.itsp.vo.EditorVO;

public class EditorImp extends SqlMapClientDaoSupport implements EditorDao {

	@Override
	public String seqNextVal() {
		return (String) getSqlMapClientTemplate().queryForObject("record.seqNextVal");
	}

	/*
	 * EditorConfig
	 */
	@Override
	public Integer selectCountEditorConfig(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("editorConfig.selectCount", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EditorVO> selectEditorConfigList(Map<String, Object> params) {
		return (List<EditorVO>) getSqlMapClientTemplate().queryForList("editorConfig.selectDataList", params);
	}

	@Override
	public EditorVO selectEditorConfig(Map<String, Object> params) {
		return (EditorVO) getSqlMapClientTemplate().queryForObject("editorConfig.selectData", params);
	}

	public void saveProcEditorConfig(EditorVO EditorVO) {
		getSqlMapClientTemplate().insert("editorConfig.saveProcData", EditorVO);
	}

	public void deleteProcEditorConfig(String seq) {
		getSqlMapClientTemplate().delete("editorConfig.deletePrcoData", seq);
	}

	/*
	 * Editor Record
	 */
	@Override
	public Integer selectCountEditorRecord(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("editorRecord.selectCount", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EditorVO> selectEditorRecordList(Map<String, Object> params) {
		return (List<EditorVO>) getSqlMapClientTemplate().queryForList("editorRecord.selectListData", params);
	}

	@Override
	public EditorVO selectEditorRecord(Map<String, Object> params) {
		return (EditorVO) getSqlMapClientTemplate().queryForObject("editorRecord.selectData", params);
	}

	public void insertProcEditorRecord(EditorVO EditorVO) {
		getSqlMapClientTemplate().insert("editorRecord.insertProcData", EditorVO);
	}

	public void deleteProcEditorRecord(String seq) {
		getSqlMapClientTemplate().delete("editorRecord.deleteProcData", seq);
	}

}
