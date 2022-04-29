package com.itsp.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itsp.vo.AttachFiles;
import com.itsp.vo.BoardVO;

public interface BoardDao {

	public String seqNextVal();
	
	public List<AttachFiles> selectFileList(String parentSeq);

	// board config
	public Integer selectCountBoardConfig(Map<String, Object> params);

	public List<BoardVO> selectBoardConfigList(Map<String, Object> params);

	public BoardVO selectBoardConfig(Map<String, Object> params);

	public void saveProcBoardConfig(BoardVO boardVO);

	public void deleteProcBoardConfig(String seq);

	// board category
	public Integer selectCountBoardCategory(Map<String, Object> params);

	public List<BoardVO> selectBoardCategoryList(Map<String, Object> params);

	public BoardVO selectBoardCategory(Map<String, Object> params);

	public void saveProcBoardCategory(BoardVO boardVO);

	public void deleteProcBoardCategory(String categorySeq);

	@SuppressWarnings("rawtypes")
	public void orderNumProcBoardCategory(ArrayList<HashMap> dataList) throws Exception;

	// board record
	
	public void increaseViewCount(String seq);
	
	public Integer selectCountBoardRecord(Map<String, Object> params);

	public List<BoardVO> selectBoardRecordList(Map<String, Object> params);

	public BoardVO selectBoardRecord(Map<String, Object> params);

	public void saveProcBoardRecord(BoardVO boardVO);

	public void deleteProcBoardRecord(String seq);
	
	public void useProcBoardRecord(Map<String, Object> params);
	
	//board Comments
	public Integer selectCountComments(String recordSeq);

	public List<BoardVO> selectListComments(String recordSeq);

	public BoardVO selectComments(Map<String, Object> params);

	public void saveProcComments(BoardVO boardVO);

	public void deleteProcComments(String seq);

	public void updateProcComments(Map<String, Object> params);
	
	// board Reply
	public Integer selectCountReply(String recordSeq);

	public BoardVO selectReply(String recordSeq);

	public void saveProcReply(BoardVO boardVO);

	public void deleteProcReply(String seq);
}
