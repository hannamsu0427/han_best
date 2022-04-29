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
import com.itsp.common.MainScreen;
import com.itsp.common.Menu;
import com.itsp.dao.ApplicationDao;
import com.itsp.dao.AttachFileDao;
import com.itsp.dao.MajorDao;
import com.itsp.dao.MenuDao;
import com.itsp.dao.PopUpDao;
import com.itsp.dao.VisualDao;
import com.itsp.vo.MajorVO;
import com.itsp.vo.MenuVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class ApplicationController {

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
	MajorDao MajorDao;
	@Autowired
	ApplicationDao ApplicationDao;

	private static final Logger logger = LoggerFactory.getLogger(ApplicationController.class);

	/***
	 * 리스트
	 ***/
	@RequestMapping("/Application")
	public String Application(HttpServletRequest request, Model model, HttpSession session,
			@RequestParam(defaultValue = "") String menuSeq, @RequestParam(defaultValue = "") String configSeq,
			@RequestParam(defaultValue = "") String categorySeq, @RequestParam(defaultValue = "N") String delYn,
			@RequestParam(defaultValue = "Y") String useYn, @RequestParam(defaultValue = "application") String flag) {

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
		MajorVO aboutData = MajorDao.selectAbout(params);
		model.addAttribute("aboutData", aboutData);
		
		params.put("aboutSeq", configSeq);
		MajorVO majorApplicationData = MajorDao.selectApplication(params);
		model.addAttribute("majorApplicationData", majorApplicationData);

		MajorVO data = ApplicationDao.selectData(params);
		model.addAttribute("data", data);

		model.addAttribute("configSeq", configSeq);
		model.addAttribute("flag", flag);
		return "iWORK/conservatory/application/application";
	}

	@RequestMapping("/ApplicationConfirm")
	public String ApplicationConfirm(HttpServletRequest request, Model model, HttpSession session,
			@RequestParam(defaultValue = "") String menuSeq, @RequestParam(defaultValue = "") String configSeq,
			@RequestParam(defaultValue = "") String categorySeq, @RequestParam(defaultValue = "N") String delYn,
			@RequestParam(defaultValue = "Y") String useYn, @RequestParam(defaultValue = "application") String flag) {

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
		MajorVO aboutData = MajorDao.selectAbout(params);
		model.addAttribute("aboutData", aboutData);
		
		params.put("aboutSeq", configSeq);
		MajorVO majorApplicationData = MajorDao.selectApplication(params);
		model.addAttribute("majorApplicationData", majorApplicationData);

		MajorVO data = ApplicationDao.selectData(params);
		model.addAttribute("data", data);

		model.addAttribute("configSeq", configSeq);
		model.addAttribute("flag", flag);
		return "iWORK/conservatory/application/applicationConfirm";
	}

	@RequestMapping("/SuccessfulConfirm")
	public String SuccessfulConfirm(HttpServletRequest request, Model model, HttpSession session,
			@RequestParam(defaultValue = "") String menuSeq, @RequestParam(defaultValue = "") String configSeq,
			@RequestParam(defaultValue = "") String categorySeq, @RequestParam(defaultValue = "N") String delYn,
			@RequestParam(defaultValue = "Y") String useYn, @RequestParam(defaultValue = "application") String flag) {

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
		MajorVO aboutData = MajorDao.selectAbout(params);
		model.addAttribute("aboutData", aboutData);
		
		params.put("aboutSeq", configSeq);
		MajorVO majorApplicationData = MajorDao.selectApplication(params);
		model.addAttribute("majorApplicationData", majorApplicationData);

		MajorVO data = ApplicationDao.selectData(params);
		model.addAttribute("data", data);

		model.addAttribute("configSeq", configSeq);
		model.addAttribute("flag", flag);
		return "iWORK/conservatory/application/successfulConfirm";
	}

}
