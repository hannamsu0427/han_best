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
import org.springframework.web.servlet.ModelAndView;

import com.itsp.common.CommUtils;
import com.itsp.common.CustomGenericException;
import com.itsp.common.MainScreen;
import com.itsp.common.Menu;
import com.itsp.dao.AttachFileDao;
import com.itsp.dao.HistoryDao;
import com.itsp.dao.MenuDao;
import com.itsp.dao.PopUpDao;
import com.itsp.dao.VisualDao;
import com.itsp.vo.HistoryVO;
import com.itsp.vo.MenuVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HistoryController {

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
	HistoryDao HistoryDao;

	private static final Logger logger = LoggerFactory.getLogger(HistoryController.class);

	/***
	 * 리스트
	 ***/
	@RequestMapping("/History")
	public String History(HttpServletRequest request, Model model, HttpSession session,
			@RequestParam(defaultValue = "") String menuSeq, @RequestParam(defaultValue = "") String configSeq,
			@RequestParam(defaultValue = "") String categorySeq, @RequestParam(defaultValue = "Y") String useYn,
			@RequestParam(defaultValue = "history") String flag) {

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
		if (!"".equals(configSeq)) {
			session.setAttribute("HISTORY_CONFIG_SEQ", configSeq);
		}

		String ssHistoryConfigSeq = session.getAttribute("HISTORY_CONFIG_SEQ").toString();
		params.put("seq", ssHistoryConfigSeq);
		HistoryVO HistoryConfigVO = HistoryDao.selectConfig(params);
		if (CommUtils.isEmpty(HistoryConfigVO)) {
			throw new CustomGenericException("BIZ_ERR", "접속 권한이 없습니다.");
		} else {
			model.addAttribute("dataConfig", HistoryConfigVO);
			params.put("configSeq", ssHistoryConfigSeq);
			List<HistoryVO> HistoryCategoryVOList = HistoryDao.selectCategoryList(params);
			if (!CommUtils.isEmpty(HistoryCategoryVOList)) {
				model.addAttribute("categoryList", HistoryCategoryVOList);
				if ("".equals(categorySeq)) {
					categorySeq = HistoryCategoryVOList.get(0).getCategorySeq();
				}
			}

		}
		params.clear();

		params.put("configSeq", ssHistoryConfigSeq);
		params.put("categorySeq", categorySeq);
		List<HistoryVO> HistoryVOList = HistoryDao.selectRecordList(params);
		model.addAttribute("dataList", HistoryVOList);

		model.addAttribute("categorySeq", categorySeq);

		model.addAttribute("flag", flag);
		return "iWORK/conservatory/history/list";
	}

}
