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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itsp.common.CommUtils;
import com.itsp.common.MainScreen;
import com.itsp.common.Menu;
import com.itsp.dao.AttachFileDao;
import com.itsp.dao.MajorDao;
import com.itsp.dao.MenuDao;
import com.itsp.dao.PopUpDao;
import com.itsp.dao.VisualDao;
import com.itsp.vo.MajorVO;
import com.itsp.vo.MenuVO;
import com.itsp.vo.ResponseHeaderVO;
import com.itsp.vo.ResponseVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MajorController {

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

	private static final Logger logger = LoggerFactory.getLogger(MajorController.class);

	/***
	 * 리스트
	 ***/
	@RequestMapping("/Major")
	public String Major(HttpServletRequest request, Model model, HttpSession session,
			@RequestParam(defaultValue = "") String menuSeq, @RequestParam(defaultValue = "") String configSeq,
			@RequestParam(defaultValue = "") String categorySeq, @RequestParam(defaultValue = "N") String delYn,
			@RequestParam(defaultValue = "Y") String useYn, @RequestParam(defaultValue = "major") String flag) {

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
		MajorVO about = MajorDao.selectAbout(params);
		if(!CommUtils.isEmpty(about)) {
			model.addAttribute("about", about);
			
			params.clear();
			params.put("useYn", useYn);
			params.put("aboutSeq", about.getSeq());
			List<MajorVO> introList = MajorDao.selectListIntro(params);
			model.addAttribute("introList", introList);
			
			List<MajorVO> goalList = MajorDao.selectListGoal(params);
			model.addAttribute("goalList", goalList);
			
			List<MajorVO> fieldList = MajorDao.selectListField(params);
			model.addAttribute("fieldList", fieldList);
			
			List<MajorVO> curriculumList = MajorDao.selectListCurriculum(params);
			model.addAttribute("curriculumList", curriculumList);
			
			List<MajorVO> courseList = MajorDao.selectListCourse(params);
			if(courseList.size() > 0){
				MajorVO course = courseList.get(0);
				model.addAttribute("course", course);
			}
			
			List<MajorVO> facilitiesList = MajorDao.selectListFacilities(params);
			model.addAttribute("facilitiesList", facilitiesList);
			
			List<MajorVO> professorList = MajorDao.selectListProfessor(params);
			model.addAttribute("professorList", professorList);
		}
		

		model.addAttribute("configSeq", configSeq);

		model.addAttribute("flag", flag);
		return "iWORK/conservatory/major/list";
	}
	
	@RequestMapping(headers = "Content-Type=application/json", value = "/Major/Professor/selectData.json", method = RequestMethod.POST)
	public @ResponseBody ResponseVO selectData(@RequestBody HashMap<String, Object> hashMap, HttpSession session,
			ModelMap model) throws Exception {

		ResponseVO response = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();
		
		List<MajorVO> professorList  = new ArrayList();
		try {
			MajorVO MajorVO = MajorDao.selectProfessor(hashMap);
			if(!CommUtils.isEmpty(MajorVO)) {
				if(MajorVO.getAcademicBackground() != null) {
					MajorVO.setAcademicBackground(MajorVO.getAcademicBackground().replaceAll("(\\r\\n|\\r|\\n|\\n\\r)", "||"));
				}
				if(MajorVO.getCareer() != null) {
					MajorVO.setCareer(MajorVO.getCareer().replaceAll("(\\r\\n|\\r|\\n|\\n\\r)", "||"));
				}
				professorList.add(MajorVO);
			}
			responseHeader.setCode(ResponseHeaderVO.SUCCESS);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		response.setHeader(responseHeader);
		response.setBody(professorList);
		return response;
	}

}
