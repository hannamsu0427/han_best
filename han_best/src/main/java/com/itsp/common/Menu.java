package com.itsp.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import com.itsp.dao.MenuDao;
import com.itsp.vo.MenuVO;

public class Menu {
	public static final String TREE_TYPE_TAB_MENU = "tabMenu";

	// getMenuList 시작
	private int makeMenuTreeSub(List rawMenuList, MenuVO parentMenuVO, int startMenuInx) {
		ArrayList<MenuVO> menuList = new ArrayList<MenuVO>();

		int menuInx = startMenuInx;
		String inParentSeq = parentMenuVO.getSeq();

		for (; menuInx < rawMenuList.size(); menuInx++) {
			MenuVO menuVO = (MenuVO) rawMenuList.get(menuInx);
			String parentSeq = menuVO.getParentSeq();
			if (inParentSeq == null || inParentSeq.equals(parentSeq)) {
				menuList.add(menuVO);
				menuInx = makeMenuTreeSub(rawMenuList, menuVO, menuInx + 1);
			} else {
				menuInx--;
				break;
			}
		}

		parentMenuVO.setChildren(menuList);
		return menuInx;
	}

	private List<MenuVO> makeMenuTree(List<MenuVO> rawMenuList) {
		MenuVO rootMenuVO = new MenuVO();
		makeMenuTreeSub(rawMenuList, rootMenuVO, 0);
		return rootMenuVO.getChildren();
	}

	public List<MenuVO> getMenuList(HttpServletRequest request, Model model, MenuDao menuDao) {
		HttpSession session = request.getSession();
		Map<String, Object> params = new HashMap<String, Object>();
		
		String useYn = (String) request.getAttribute("useYn");
		if(!"".equals(useYn)) {
			params.put("useYn", useYn);
		}
		
		List<MenuVO> rawMenuList = menuDao.selectMenuList(params);
		List<MenuVO> menuList = makeMenuTree(rawMenuList);
		model.addAttribute("menuList", menuList);
		return menuList;
	}
	// getMenuList 끝

	public List<MenuVO> getLeftMenu(Model model, MenuDao menuDao, String menuSeq) {

		List<MenuVO> leftMenu = null;
		Map<String, Object> map = new HashMap<String, Object>();

		String parentSortOrder = "";
		MenuVO inParentMenuVO = new MenuVO();
		MenuVO parentMenuVO = menuDao.selectMenu(menuSeq);
		if (parentMenuVO != null) {
			parentSortOrder = parentMenuVO.getSortOrder();
			String[] sortOrderArray = parentSortOrder.split(":");

			String tempLnbSortOrder = "";
			ArrayList whereSortOrderArray = new ArrayList();
			for (int inx = 0; inx < sortOrderArray.length; inx++) {
				tempLnbSortOrder = new String(sortOrderArray[0]);
			}
			map.put("sortOrder", tempLnbSortOrder);
			List<MenuVO> rawMenuList = menuDao.lnbSelect(map);
			leftMenu = makeMenuTree(rawMenuList);
			model.addAttribute("leftMenu", leftMenu);
			model.addAttribute("visualSortOrder", tempLnbSortOrder);
		}
		return leftMenu;
	}
	// getLeftMenu 끝

	public List<MenuVO> getParentPath(MenuDao menuDao, String parentSeq) {

		String parentSortOrder = "";
		MenuVO inParentMenuVO = new MenuVO();
		MenuVO parentMenuVO = menuDao.selectMenu(parentSeq);
		parentSortOrder = parentMenuVO.getSortOrder();
		String[] sortOrderArray = parentSortOrder.split(":");

		String tempWhereSortOrder = "";
		ArrayList whereSortOrderArray = new ArrayList();
		HashMap paramMap = new HashMap();

		for (int inx = 0; inx < sortOrderArray.length; inx++) {
			if (inx > 0) {
				tempWhereSortOrder += ":";
			}
			tempWhereSortOrder += sortOrderArray[inx];
			whereSortOrderArray.add(new String(tempWhereSortOrder));
		}
		paramMap.put("whereSortOrderArray", whereSortOrderArray);
		return menuDao.menuSelectTitle(paramMap);
	}

	public ArrayList<String> getCurMenuSeqPathOnly(MenuDao menuDao, @RequestParam String parentSeq) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<MenuVO> curMenuPath = getParentPath(menuDao, parentSeq);
		ArrayList<String> curMenuSeqPath = new ArrayList<String>();
		for (int inx = 0; inx < curMenuPath.size(); inx++) {
			curMenuSeqPath.add((String) ((MenuVO) curMenuPath.get(inx)).getSeq());
		}
		return curMenuSeqPath;
	}

	public String getNotEmptyUrlSeq(List<MenuVO> menuList, ArrayList<String> curMenuSeqPath, int depth) {
		int maxDepth = curMenuSeqPath.size() - 1;

		for (int inx = 0; inx < menuList.size(); inx++) {
			MenuVO tempMenuVO = menuList.get(inx);
			String inMenuSeq = (String) curMenuSeqPath.get(depth);
			String tempMenuSeq = tempMenuVO.getSeq();
			if (inMenuSeq.equals(tempMenuSeq)) {
				if (maxDepth > depth) {
					String newMenuSeq = getNotEmptyUrlSeq(tempMenuVO.getChildren(), curMenuSeqPath, depth + 1);
					if (!"".equals(newMenuSeq)) {
						return newMenuSeq;
					}
				} else {
					String treeType = tempMenuVO.getTreeType();
					if (treeType != null && TREE_TYPE_TAB_MENU.equals(treeType)) {
						String newMenuSeq = getNotEmptyUrlSeqSub(tempMenuVO.getChildren());
						if (!"".equals(newMenuSeq)) {
							return newMenuSeq;
						}
					}
				}
				break;
			}
		}

		return curMenuSeqPath.get(maxDepth);
	}

	public String getNotEmptyUrlSeqSub(List<MenuVO> menuList) {
		for (int inx = 0; inx < menuList.size(); inx++) {
			MenuVO tempMenuVO = menuList.get(inx);
			String treeType = tempMenuVO.getTreeType();
			if (treeType == null || !TREE_TYPE_TAB_MENU.equals(treeType)) {
				return tempMenuVO.getSeq();
			}

			String newMenuSeq = getNotEmptyUrlSeqSub(tempMenuVO.getChildren());
			if (!"".equals(newMenuSeq)) {
				return newMenuSeq;
			}
		}

		return "";
	}

	public void getCurMenuPath(Model model, MenuDao menuDao, @RequestParam String parentSeq) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("seq", parentSeq);
		List<MenuVO> curMenuPath = getParentPath(menuDao, parentSeq);
		ArrayList<String> curMenuSeqPath = new ArrayList<String>();
		for (int inx = 0; inx < curMenuPath.size(); inx++) {
			curMenuSeqPath.add((String) ((MenuVO) curMenuPath.get(inx)).getSeq());
		}
		model.addAttribute("curMenuPath", curMenuPath);
		model.addAttribute("curMenuSeqPath", curMenuSeqPath);
	}

	// getCurMenuTab 시작
	private int makeMenuTabSub(List rawMenuTab, MenuVO parentMenuVO, int startMenuInx) {
		ArrayList<MenuVO> menuTab = new ArrayList<MenuVO>();

		int menuInx = startMenuInx;
		String inParentSeq = parentMenuVO.getSeq();
		for (; menuInx < rawMenuTab.size(); menuInx++) {
			MenuVO menuVO = (MenuVO) rawMenuTab.get(menuInx);
			String parentSeq = menuVO.getParentSeq();
			if (inParentSeq == null || inParentSeq.equals(parentSeq)) {
				menuTab.add(menuVO);
				menuInx = makeMenuTabSub(rawMenuTab, menuVO, menuInx + 1);
			} else {
				menuInx--;
				break;
			}
		}

		parentMenuVO.setChildren(menuTab);
		return menuInx;
	}

	private List<MenuVO> makeMenuTab(List<MenuVO> rawMenuTab) {
		MenuVO rootMenuVO = new MenuVO();
		makeMenuTabSub(rawMenuTab, rootMenuVO, 0);
		return rootMenuVO.getChildren();
	}

	public void getCurMenuTab(Model model, MenuDao menuDao, @RequestParam String menuSeq) {
		String curSortOrder = menuDao.tabSortOrder(menuSeq);
		String[] curOrderArray = curSortOrder.split(":");
		String tempinSortOrder = "";
		ArrayList inSortOrderArray = new ArrayList();
		HashMap paramMap = new HashMap();
		for (int inx = 0; inx < curOrderArray.length; inx++) {
			if (inx > 0) {
				tempinSortOrder += ":";
			}
			tempinSortOrder += curOrderArray[inx];
			inSortOrderArray.add(new String(tempinSortOrder));
		}
		paramMap.put("inSortOrderArray", inSortOrderArray);
		String tabSortOrderSelectIn = menuDao.tabSortOrderSelectIn(paramMap);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sortOrder", tabSortOrderSelectIn);

		List<MenuVO> rawMenuTab = menuDao.menuTabSelect(map);
		List<MenuVO> curMenuTab = makeMenuTab(rawMenuTab);
		System.out.print("curMenuTab = " + curMenuTab);
		model.addAttribute("curMenuTab", curMenuTab);
	}
	// getCurMenuTab 끝

}
