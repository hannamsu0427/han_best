package com.itsp.iwork.controller;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.itsp.common.CommUtils;
import com.itsp.common.CustomGenericException;
import com.itsp.common.MainScreen;
import com.itsp.common.Menu;
import com.itsp.common.PageUtil;
import com.itsp.dao.AttachFileDao;
import com.itsp.dao.EtcDao;
import com.itsp.dao.FacilityDao;
import com.itsp.dao.MenuDao;
import com.itsp.dao.PopUpDao;
import com.itsp.dao.VisualDao;
import com.itsp.vo.AttachFiles;
import com.itsp.vo.EditorVO;
import com.itsp.vo.FacilityVO;
import com.itsp.vo.MenuVO;
import com.itsp.vo.ResponseHeaderVO;
import com.itsp.vo.ResponseVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class FacilityController {
	
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
	FacilityDao FacilityDao;
	
	private static final Logger logger = LoggerFactory.getLogger(FacilityController.class);

	/***
	 * 리스트
	 ***/
	@RequestMapping("/Facility")
	public String Facility(HttpServletRequest request, Model model, HttpSession session,
			@RequestParam(defaultValue = "") String menuSeq, @RequestParam(defaultValue = "") String configSeq,
			@RequestParam(defaultValue = "") String seq, @RequestParam(defaultValue = "N") String delYn,
			@RequestParam(defaultValue = "Y") String useYn, @RequestParam(defaultValue = "facility") String flag) {

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
		FacilityVO configVO = FacilityDao.selectConfig(params);
		if (CommUtils.isEmpty(configVO)) {
			throw new CustomGenericException("BIZ_ERR", "접속 권한이 없습니다.");
		} else {
			model.addAttribute("dataConfig", configVO);
		}
		
		params.clear();
		params.put("configSeq", configVO.getSeq());
		List<FacilityVO> recordListVO = FacilityDao.selectRecordList(params);
		if(recordListVO.size() > 0) {
			model.addAttribute("dataList", recordListVO);
			if("".equals(seq)) {
				seq = recordListVO.get(0).getSeq();
			}
			params.put("seq", seq);
			FacilityVO recordVO = FacilityDao.selectRecord(params);
			model.addAttribute("data", recordVO);
		}
		

		model.addAttribute("configSeq", configSeq);
		model.addAttribute("flag", flag);
		return "iWORK/conservatory/facility/list";
	}

}
