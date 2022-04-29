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

import com.itsp.common.MainScreen;
import com.itsp.common.Menu;
import com.itsp.dao.AttachFileDao;
import com.itsp.dao.BoardDao;
import com.itsp.dao.MenuDao;
import com.itsp.dao.PopUpDao;
import com.itsp.dao.ScheduleDao;
import com.itsp.dao.VisualDao;
import com.itsp.vo.BoardVO;
import com.itsp.vo.ScheduleVO;
import com.itsp.vo.VisualVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MainController {

	@Autowired
	BoardDao BoardDao;
	@Autowired
	VisualDao VisualDao;
	@Autowired
	PopUpDao PopUpDao;
	@Autowired
	ScheduleDao ScheduleDao;

	@Autowired
	AttachFileDao AttachFileDao;

	@Autowired
	MenuDao menuDao;
	Menu menu = new Menu();
	MainScreen mainScreen = new MainScreen();

	private static final Logger logger = LoggerFactory.getLogger(MainController.class);

	@RequestMapping("/main")
	public String Main(HttpServletRequest request, Model model, HttpSession session,
			@RequestParam(defaultValue = "Y") String useYn, @RequestParam(defaultValue = "main") String flag) {
	
		request.setAttribute("useYn", useYn);
		menu.getMenuList(request, model, menuDao);
		
		mainScreen.getToday(request, model);
		mainScreen.getPopUp(request, model, PopUpDao);

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("useYn", useYn);
		List<VisualVO> VisualVO_List = VisualDao.selectDataList(params);
		model.addAttribute("VisualVO_List", VisualVO_List);
		
		// 게시판 갯수
		params.put("startNum", "1");
		params.put("endNum", "5");
		
		//공지사항
		params.put("configSeq", "49");
		List<BoardVO> BoardVOList49 = BoardDao.selectBoardRecordList(params);
		model.addAttribute("BoardVOList49", BoardVOList49);
		
		//행사안내
		params.put("configSeq", "51");
		List<BoardVO> BoardVOList51 = BoardDao.selectBoardRecordList(params);
		model.addAttribute("BoardVOList51", BoardVOList51);

		//일정
		params.put("configSeq", "263");
		List<ScheduleVO> ScheduleVOList = ScheduleDao.selectRecordList(params);
		model.addAttribute("ScheduleList", ScheduleVOList);
		
		params.clear();
		params.put("startNum", "1");
		params.put("endNum", "6");
		ArrayList inConfigSeqArray = new ArrayList();
		inConfigSeqArray.add("170");
		inConfigSeqArray.add("310");
		params.put("inConfigSeqArray", inConfigSeqArray);
		List<BoardVO> BoardVOList = BoardDao.selectBoardRecordList(params);
		model.addAttribute("BoardVOList", BoardVOList);
		
		model.addAttribute("flag", flag);
		return "iWORK/conservatory/main";
	}

}
