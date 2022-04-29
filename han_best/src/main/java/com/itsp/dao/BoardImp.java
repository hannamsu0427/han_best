package com.itsp.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.itsp.vo.AttachFiles;
import com.itsp.vo.BoardVO;

public class BoardImp extends SqlMapClientDaoSupport implements BoardDao {

	@Override
	public String seqNextVal() {
		return (String) getSqlMapClientTemplate().queryForObject("record.seqNextVal");
	}

	// file
	@SuppressWarnings("unchecked")
	@Override
	public List<AttachFiles> selectFileList(String parentSeq) {
		return getSqlMapClientTemplate().queryForList("attachFile.selectDataList", parentSeq);
	}

	/*
	 * boardConfig
	 */
	@Override
	public Integer selectCountBoardConfig(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("boardConfig.selectCount", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BoardVO> selectBoardConfigList(Map<String, Object> params) {
		return (List<BoardVO>) getSqlMapClientTemplate().queryForList("boardConfig.selectDataList", params);
	}

	@Override
	public BoardVO selectBoardConfig(Map<String, Object> params) {
		return (BoardVO) getSqlMapClientTemplate().queryForObject("boardConfig.selectData", params);
	}

	public void saveProcBoardConfig(BoardVO boardVO) {
		getSqlMapClientTemplate().insert("boardConfig.saveProcData", boardVO);
	}

	public void deleteProcBoardConfig(String seq) {
		getSqlMapClientTemplate().delete("boardConfig.deletePrcoData", seq);
	}

	/*
	 * boardCategory
	 */
	@Override
	public Integer selectCountBoardCategory(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("boardCategory.selectCount", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BoardVO> selectBoardCategoryList(Map<String, Object> params) {
		return (List<BoardVO>) getSqlMapClientTemplate().queryForList("boardCategory.selectListData", params);
	}

	@Override
	public BoardVO selectBoardCategory(Map<String, Object> params) {
		return (BoardVO) getSqlMapClientTemplate().queryForObject("boardCategory.selectData", params);
	}

	public void saveProcBoardCategory(BoardVO boardVO) {
		getSqlMapClientTemplate().insert("boardCategory.saveProcData", boardVO);
	}

	public void deleteProcBoardCategory(String categorySeq) {
		getSqlMapClientTemplate().delete("boardCategory.deleteProcData", categorySeq);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void orderNumProcBoardCategory(ArrayList<HashMap> dataList) throws Exception {
		try {
			int inx;
			for (inx = 0; inx < dataList.size(); inx++) {
				HashMap<String, String> map = (HashMap) dataList.get(inx);
				getSqlMapClientTemplate().update("boardCategory.orderNumProc", map);
			}
		} catch (Throwable t) {
			throw (Exception) t;
		}
		return;
	}

	/*
	 * board Record
	 */
	@Override
	public Integer selectCountBoardRecord(Map<String, Object> params) {
		return (Integer) getSqlMapClientTemplate().queryForObject("boardRecord.selectCount", params);
	}
	
	@Override
	public void increaseViewCount(String seq) {
		getSqlMapClientTemplate().update("boardRecord.increaseViewCount", seq);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BoardVO> selectBoardRecordList(Map<String, Object> params) {
		List<BoardVO> voList = getSqlMapClientTemplate().queryForList("boardRecord.selectListData", params);
		List<BoardVO> resultList = new ArrayList<BoardVO>();

		for (BoardVO vo : voList) {
			List<AttachFiles> attachFile = selectFileList(vo.getSeq());
			vo.setAttachFileList(attachFile);

			int commentlCount = selectCountComments(vo.getSeq());
			String commentCnt = String.valueOf(commentlCount);
			vo.setCommentCnt(commentCnt);

			int replyCount = selectCountReply(vo.getSeq());
			String replyCnt = String.valueOf(replyCount);
			vo.setReplyCnt(replyCnt);

			resultList.add(vo);
		}

		return resultList;
	}

	@Override
	public BoardVO selectBoardRecord(Map<String, Object> params) {
		BoardVO vo = (BoardVO) getSqlMapClientTemplate().queryForObject("boardRecord.selectData", params);
		if (vo != null) {
			List<AttachFiles> attachFile = selectFileList(vo.getSeq());
			vo.setAttachFileList(attachFile);

			int commentlCount = selectCountComments(vo.getSeq());
			String commentCnt = String.valueOf(commentlCount);
			vo.setCommentCnt(commentCnt);

			if (commentlCount > 0) {
				List<BoardVO> commentsList = selectListComments(vo.getSeq());
				vo.setCommentList(commentsList);
			}
			
			int replyCount = selectCountReply(vo.getSeq());
			String replyCnt = String.valueOf(replyCount);
			vo.setReplyCnt(replyCnt);
		}
		return vo;
	}

	public void saveProcBoardRecord(BoardVO boardVO) {
		getSqlMapClientTemplate().insert("boardRecord.saveProcData", boardVO);
	}

	public void deleteProcBoardRecord(String seq) {
		getSqlMapClientTemplate().delete("boardRecord.deleteProcData", seq);
	}

	public void useProcBoardRecord(Map<String, Object> params) {
		getSqlMapClientTemplate().update("boardRecord.useProcData", params);
	}

	// board Comments
	@Override
	public Integer selectCountComments(String recordSeq) {
		return (Integer) getSqlMapClientTemplate().queryForObject("boardComments.selectCount", recordSeq);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BoardVO> selectListComments(String recordSeq) {
		return getSqlMapClientTemplate().queryForList("boardComments.selectListData", recordSeq);
	}

	@Override
	public BoardVO selectComments(Map<String, Object> params) {
		return (BoardVO) getSqlMapClientTemplate().queryForObject("boardComments.selectData", params);
	}

	public void saveProcComments(BoardVO boardVO) {
		getSqlMapClientTemplate().insert("boardComments.saveProcData", boardVO);
	}

	public void deleteProcComments(String seq) {
		getSqlMapClientTemplate().delete("boardComments.deleteProcData", seq);
	}

	public void updateProcComments(Map<String, Object> params) {
		getSqlMapClientTemplate().update("boardComments.updateProcData", params);
	}

	// board Reply
	@Override
	public Integer selectCountReply(String recordSeq) {
		return (Integer) getSqlMapClientTemplate().queryForObject("boardReply.selectCount", recordSeq);
	}

	@Override
	public BoardVO selectReply(String recordSeq) {
		return (BoardVO) getSqlMapClientTemplate().queryForObject("boardReply.selectData", recordSeq);
	}

	public void saveProcReply(BoardVO boardVO) {
		getSqlMapClientTemplate().insert("boardReply.saveProcData", boardVO);
	}

	public void deleteProcReply(String seq) {
		getSqlMapClientTemplate().delete("boardReply.deleteProcData", seq);
	}
}
