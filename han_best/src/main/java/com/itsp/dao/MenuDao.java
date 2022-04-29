package com.itsp.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itsp.vo.MenuVO;

public interface MenuDao {

	public String seqNextVal();

	public MenuVO selectMenu(String seq);

	public List<MenuVO> selectMenuList(Map<String, Object> params);

	public String menuMaxSiblingSortOder(String parentSeq);

	public void saveDataProc(MenuVO menuVO);

	public void deleteDataProc(String seq);
	
	public void insertDataProc(MenuVO menuVO);
	
	public void updateDataProc(MenuVO menuVO);
	

	// 네비게이션
	public List<MenuVO> menuSelectTitle(HashMap paramMap);

	// lnb
	public List<MenuVO> lnbSelect(Map<String, Object> params);

	public String tabSortOrder(String seq);

	public String tabSortOrderSelectIn(HashMap paramMap);

	public List<MenuVO> menuTabSelect(Map<String, Object> params);

	public String selectFindMenuSeq(Map<String, Object> params);

	@SuppressWarnings("rawtypes")
	public void sortOrderUpdate(ArrayList<HashMap> dataList) throws Exception;
}
