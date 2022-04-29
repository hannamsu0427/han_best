package com.itsp.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.itsp.vo.MenuVO;

public class MenuImp extends SqlMapClientDaoSupport implements MenuDao {

	@Override
	public String seqNextVal() {
		// TODO Auto-generated method stub
		return (String) getSqlMapClientTemplate().queryForObject("record.seqNextVal");
	}

	@Override
	public MenuVO selectMenu(String seq) {
		// TODO Auto-generated method stub
		return (MenuVO) getSqlMapClientTemplate().queryForObject("menu.selectMenu", seq);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MenuVO> selectMenuList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return (List<MenuVO>) getSqlMapClientTemplate().queryForList("menu.selectMenuList", params);
	}

	@Override
	public String menuMaxSiblingSortOder(String parentSeq) {
		// TODO Auto-generated method stub
		return (String) getSqlMapClientTemplate().queryForObject("menu.menuMaxSiblingSortOder", parentSeq);
	}
	
	@Override
	public void insertDataProc(MenuVO menuVO) {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().insert("menu.insertDataProc", menuVO);
	}
	
	@Override
	public void updateDataProc(MenuVO menuVO) {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().update("menu.updateDataProc", menuVO);
	}

	@Override
	public void saveDataProc(MenuVO menuVO) {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().insert("menu.saveDataProc", menuVO);
	}

	@Override
	public void deleteDataProc(String seq) {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().delete("menu.deleteDataProc", seq);
	}

	// 페이지 타이틀
	@SuppressWarnings("unchecked")
	@Override
	public List<MenuVO> menuSelectTitle(HashMap paramMap) {
		return (List<MenuVO>) getSqlMapClientTemplate().queryForList("menu.menuSelectTitle", paramMap);
	}

	// lnb
	@SuppressWarnings("unchecked")
	@Override
	public List<MenuVO> lnbSelect(Map<String, Object> params) {
		return (List<MenuVO>) getSqlMapClientTemplate().queryForList("menu.lnbSelect", params);
	}

	// 탭메뉴
	@Override
	public String tabSortOrder(String seq) {
		// TODO Auto-generated method stub
		return (String) getSqlMapClientTemplate().queryForObject("menu.tabSortOrder", seq);
	}

	public String tabSortOrderSelectIn(HashMap paramMap) {
		// TODO Auto-generated method stub
		return (String) getSqlMapClientTemplate().queryForObject("menu.tabSortOrderSelectIn", paramMap);
	}

	// 탭메뉴
	@SuppressWarnings("unchecked")
	@Override
	public List<MenuVO> menuTabSelect(Map<String, Object> params) {
		return (List<MenuVO>) getSqlMapClientTemplate().queryForList("menu.menuTabSelect", params);
	}

	@Override
	public String selectFindMenuSeq(Map<String, Object> params) {
		return (String) getSqlMapClientTemplate().queryForObject("menu.selectFindMenuSeq", params);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void sortOrderUpdate(ArrayList<HashMap> dataList) throws Exception {
		try {
			int inx;
			for (inx = 0; inx < dataList.size(); inx++) {
				HashMap<String, String> dataMap = (HashMap) dataList.get(inx);
				getSqlMapClientTemplate().update("menu.sortOrderUpdate", dataMap);
			}
			// transactionManager.commit(status);
		} catch (Throwable t) {
			// transactionManager.rollback(status);
			throw (Exception) t;
		}

		return;
	}

}
