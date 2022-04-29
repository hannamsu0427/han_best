package com.itsp.dao;

import java.util.List;
import java.util.Map;

import com.itsp.vo.VisitVO;

public interface VisitDao {

	public String seqNextVal();

	public int totalCount(Map<String, Object> params);

	public List<VisitVO> selectDataList(Map<String, Object> params);

	public VisitVO selectData(Map<String, Object> params);

	public void insertDataProc(Map<String, Object> params);

	public List<VisitVO> selectVisitListData(Map<String, Object> params);

	public List<VisitVO> selectVisitListMonthData(Map<String, Object> params);

}
