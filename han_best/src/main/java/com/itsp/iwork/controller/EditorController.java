package com.itsp.iwork.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itsp.common.CommUtils;
import com.itsp.common.CustomGenericException;
import com.itsp.common.MainScreen;
import com.itsp.common.Menu;
import com.itsp.common.PageUtil;
import com.itsp.dao.AttachFileDao;
import com.itsp.dao.EditorDao;
import com.itsp.dao.MenuDao;
import com.itsp.dao.PopUpDao;
import com.itsp.dao.VisualDao;
import com.itsp.vo.EditorVO;
import com.itsp.vo.MenuVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class EditorController {

	@Autowired
	VisualDao VisualDao;
	@Autowired
	PopUpDao PopUpDao;

	@Autowired
	AttachFileDao AttachFileDao;

	@Autowired
	MenuDao menuDao;
	Menu menu = new Menu();
	MainScreen mainScreen = new MainScreen();

	@Autowired
	EditorDao EditorDao;

	private static final Logger logger = LoggerFactory.getLogger(EditorController.class);

	/***
	 * 리스트
	 ***/
	@RequestMapping("/Editor")
	public String Editor(HttpServletRequest request, Model model, HttpSession session,
			@RequestParam(defaultValue = "") String menuSeq, @RequestParam(defaultValue = "") String configSeq,
			@RequestParam(defaultValue = "") String categorySeq, @RequestParam(defaultValue = "N") String delYn,
			@RequestParam(defaultValue = "Y") String useYn, @RequestParam(defaultValue = "editor") String flag) {

		request.setAttribute("useYn", useYn);
		boolean isMenu = ("".equals(CommUtils.checkNull(menuSeq)));
		if (isMenu) {
			menu.getMenuList(request, model, menuDao);
		} else {
			List<MenuVO> leftMenu = menu.getLeftMenu(model, menuDao, menuSeq);
			List<MenuVO> menuList = menu.getMenuList(request, model, menuDao);
			ArrayList<String> curMenuSeqPath = menu.getCurMenuSeqPathOnly(menuDao, menuSeq);
			String newMenuSeq = menu.getNotEmptyUrlSeq(menuList, curMenuSeqPath, 0);

			menu.getCurMenuPath(model, menuDao, newMenuSeq);
			menu.getCurMenuTab(model, menuDao, newMenuSeq);
			model.addAttribute("menuSeq", newMenuSeq);

			MenuVO menu = menuDao.selectMenu(newMenuSeq);
			model.addAttribute("menu", menu);
			if (!menuSeq.equals(newMenuSeq)) {
				model.addAttribute("replaceUrl", "/");
				return "replace";
			}
		}
		model.addAttribute("menuSeq", menuSeq);

		mainScreen.getToday(request, model);
		mainScreen.getPopUp(request, model, PopUpDao);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("useYn", useYn);
		params.put("seq", configSeq);
		logger.info("configSeq = " + configSeq);

		EditorVO EditorConfigVO = EditorDao.selectEditorConfig(params);
		logger.info("EditorConfigVO = " + EditorConfigVO);

		if (CommUtils.isEmpty(EditorConfigVO)) {
			throw new CustomGenericException("BIZ_ERR", "접속 권한이 없습니다.");
		} else {
			model.addAttribute("dataConfig", EditorConfigVO);
		}

		logger.info("clear");

		params.clear();
		params.put("configSeq", EditorConfigVO.getSeq());
		List<EditorVO> EditorVOList = EditorDao.selectEditorRecordList(params);
		model.addAttribute("dataList", EditorVOList);

		model.addAttribute("configSeq", configSeq);
		model.addAttribute("categorySeq", categorySeq);

		model.addAttribute("flag", flag);
		return "iWORK/conservatory/editor/list";
	}

	@RequestMapping("/Editor/Record")
	public String RecordList(HttpServletRequest request, Model model, HttpSession session, @RequestParam String menuSeq, @RequestParam(defaultValue = "Y") String useYn,
			@RequestParam String configSeq, @RequestParam(defaultValue = "1") String pageNum,
			@RequestParam(defaultValue = "editor") String flag) {
		
		request.setAttribute("useYn", useYn);
		boolean isMenu = ("".equals(CommUtils.checkNull(menuSeq)));
		if (isMenu) {
			menu.getMenuList(request, model, menuDao);
		} else {
			List<MenuVO> leftMenu = menu.getLeftMenu(model, menuDao, menuSeq);
			List<MenuVO> menuList = menu.getMenuList(request, model, menuDao);
			ArrayList<String> curMenuSeqPath = menu.getCurMenuSeqPathOnly(menuDao, menuSeq);
			String newMenuSeq = menu.getNotEmptyUrlSeq(menuList, curMenuSeqPath, 0);

			menu.getCurMenuPath(model, menuDao, newMenuSeq);
			menu.getCurMenuTab(model, menuDao, newMenuSeq);
			model.addAttribute("menuSeq", newMenuSeq);

			MenuVO menu = menuDao.selectMenu(newMenuSeq);
			model.addAttribute("menu", menu);
			if (!menuSeq.equals(newMenuSeq)) {
				model.addAttribute("replaceUrl", "/");
				return "replace";
			}
		}
		model.addAttribute("menuSeq", menuSeq);

		mainScreen.getToday(request, model);
		mainScreen.getPopUp(request, model, PopUpDao);

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("seq", configSeq);
		EditorVO EditorConfigVO = EditorDao.selectEditorConfig(params);
		if (CommUtils.isEmpty(EditorConfigVO)) {
			throw new CustomGenericException("BIZ_ERR", "접속 권한이 없습니다.");
		} else {
			model.addAttribute("dataConfig", EditorConfigVO);
		}
		params.clear();

		params.put("configSeq", configSeq);
		int totalCount = EditorDao.selectCountEditorRecord(params);

		HashMap<String, Object> pageMap = PageUtil.pageMap(totalCount, pageNum);

		params.put("startNum", pageMap.get("startNum"));
		params.put("endNum", pageMap.get("endNum"));

		List<EditorVO> EditorVOList = EditorDao.selectEditorRecordList(params);
		model.addAttribute("dataList", EditorVOList);

		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pageMap", pageMap);

		model.addAttribute("configSeq", configSeq);
		model.addAttribute("menuSeq", menuSeq);
		model.addAttribute("flag", flag);
		return "iWORK/conservatory/editor/edit";
	}

}
