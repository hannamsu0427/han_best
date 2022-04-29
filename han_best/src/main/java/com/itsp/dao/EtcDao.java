package com.itsp.dao;

import java.util.List;
import java.util.Map;

import com.itsp.vo.EtcVO;

public interface EtcDao {

	public void insertProcData(Map<String, Object> params);

	public void deleteProcData(String recordSeq);

	public List<EtcVO> selectListData(String recordSeq);
}
