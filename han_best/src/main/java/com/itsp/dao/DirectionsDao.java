package com.itsp.dao;

import java.util.List;
import java.util.Map;

import com.itsp.vo.DirectionsVO;

public interface DirectionsDao {

	public String seqNextVal();

	public Integer selectCountRecord(Map<String, Object> params);

	public List<DirectionsVO> selectRecordList(Map<String, Object> params);

	public DirectionsVO selectRecord(Map<String, Object> params);

	public void saveProcRecord(DirectionsVO DirectionsVO);

	public void deleteProcRecord(String seq);

}
