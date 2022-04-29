package com.itsp.dao;

import java.util.List;
import java.util.Map;

import com.itsp.vo.EditorVO;

public interface EditorDao {

	public String seqNextVal();

	// config
	public Integer selectCountEditorConfig(Map<String, Object> params);

	public List<EditorVO> selectEditorConfigList(Map<String, Object> params);

	public EditorVO selectEditorConfig(Map<String, Object> params);

	public void saveProcEditorConfig(EditorVO EditorVO);

	public void deleteProcEditorConfig(String seq);

	// record
	public Integer selectCountEditorRecord(Map<String, Object> params);

	public List<EditorVO> selectEditorRecordList(Map<String, Object> params);

	public EditorVO selectEditorRecord(Map<String, Object> params);

	public void insertProcEditorRecord(EditorVO EditorVO);

	public void deleteProcEditorRecord(String seq);
}
