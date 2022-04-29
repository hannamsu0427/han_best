package com.itsp.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itsp.vo.AttachFiles;
import com.itsp.vo.VisualVO;

public interface VisualDao {

	public String seqNextVal();

	/*
	 * data
	 */

	public int totalCount(Map<String, Object> params);

	public List<VisualVO> selectDataList(Map<String, Object> params);

	public VisualVO selectData(Map<String, Object> params);

	public void insertDataProc(VisualVO vo);

	public void updateDataProc(VisualVO vo);

	public void deleteDataProc(Map<String, Object> params);
	
	@SuppressWarnings("rawtypes")
	public void orderNumProc(ArrayList<HashMap> dataList) throws Exception;

	/*
	 * file
	 */
	public List<AttachFiles> selectFileList(String parentSeq);

	public AttachFiles selectFile(String seq);
}
