package com.itsp.iwork.controller;

import java.util.ArrayList;
import java.util.Calendar;
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
import com.itsp.dao.AttachFileDao;
import com.itsp.dao.MenuDao;
import com.itsp.dao.PopUpDao;
import com.itsp.dao.ScheduleDao;
import com.itsp.dao.VisualDao;
import com.itsp.vo.MenuVO;
import com.itsp.vo.ScheduleVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class ScheduleController {

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
	ScheduleDao ScheduleDao;

	private static final Logger logger = LoggerFactory.getLogger(ScheduleController.class);

	/***
	 * 리스트
	 ***/
	@RequestMapping("/Schedule")
	public String Schedule(HttpServletRequest request, Model model, HttpSession session,
			@RequestParam(defaultValue = "") String menuSeq, @RequestParam(defaultValue = "") String configSeq,
			@RequestParam(defaultValue = "") String year, @RequestParam(defaultValue = "Y") String useYn,
			@RequestParam(defaultValue = "schedule") String flag) {

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
			session.setAttribute("SCHEDULE_CONFIG_SEQ", configSeq);
		}

		String ssConfigSeq = session.getAttribute("SCHEDULE_CONFIG_SEQ").toString();
		params.put("seq", ssConfigSeq);
		ScheduleVO ConfigVO = ScheduleDao.selectConfig(params);
		if (CommUtils.isEmpty(ConfigVO)) {
			throw new CustomGenericException("BIZ_ERR", "접속 권한이 없습니다.");
		} else {
			model.addAttribute("dataConfig", ConfigVO);
		}
		params.clear();

		params.put("configSeq", ssConfigSeq);
		Calendar c = Calendar.getInstance(); // 객체 생성 및 현재 일시분초...셋팅
		String nowYear = String.valueOf(c.get(Calendar.YEAR));
		if ("".equals(year)) {
			year = nowYear;
		}
		params.put("startYear", year);
		List<ScheduleVO> ScheduleVOList = ScheduleDao.selectRecordList(params);
		model.addAttribute("dataList", ScheduleVOList);

		model.addAttribute("year", year);
		model.addAttribute("configSeq", configSeq);
		model.addAttribute("flag", flag);
		return "iWORK/conservatory/schedule/list";
	}
	
	
	@RequestMapping("/Schedule/Record")
	public String ScheduleRecord(HttpServletRequest request, Model model, HttpSession session,
			@RequestParam(defaultValue = "") String menuSeq, @RequestParam(defaultValue = "") String configSeq,
			@RequestParam(defaultValue = "") String year, @RequestParam(defaultValue = "Y") String useYn,
			@RequestParam(defaultValue = "") String seq,
			@RequestParam(defaultValue = "schedule") String flag) {

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
		if (!"0".equals(seq)) {
			params.put("configSeq", configSeq);
			params.put("seq", seq);
			ScheduleVO ScheduleVO = ScheduleDao.selectRecord(params);
			if (!CommUtils.isEmpty(ScheduleVO)) {
				model.addAttribute("data", ScheduleVO);
			}
		}
		
		model.addAttribute("year", year);
		model.addAttribute("configSeq", configSeq);
		model.addAttribute("flag", flag);
		return "iWORK/conservatory/schedule/edit";
	}

}
