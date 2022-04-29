package com.itsp.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itsp.vo.AttachFiles;
import com.itsp.vo.PopUpVO;

public interface PopUpDao {

	public String seqNextVal();
	
	/*
	 * data
	 */
	
	public int totalCount(Map<String, Object> params);
	
	public List<PopUpVO> selectDataList(Map<String, Object> params);

	public PopUpVO selectData(Map<String, Object> params);

	public void insertDataProc(PopUpVO vo);
	
	public void updateDataProc(PopUpVO vo);

	public void deleteDataProc(Map<String, Object> params);
	
	@SuppressWarnings("rawtypes")
	public void orderNumProc(ArrayList<HashMap> dataList) throws Exception;
	
	
	/*
	 * file
	 */
	public List<AttachFiles> selectFileList(String parentSeq);

	public AttachFiles selectFile(String seq);
}
