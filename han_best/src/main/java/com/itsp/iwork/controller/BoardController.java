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
import com.itsp.dao.BoardDao;
import com.itsp.dao.MenuDao;
import com.itsp.dao.PopUpDao;
import com.itsp.dao.VisualDao;
import com.itsp.vo.BoardVO;
import com.itsp.vo.MenuVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class BoardController {

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
	BoardDao BoardDao;

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	/***
	 * 리스트
	 ***/
	@RequestMapping("/BoardList")
	public String BoardList(HttpServletRequest request, Model model, HttpSession session,
			@RequestParam(defaultValue = "") String menuSeq, @RequestParam(defaultValue = "") String configSeq,
			@RequestParam(defaultValue = "") String categorySeq, @RequestParam(defaultValue = "N") String delYn,
			@RequestParam(defaultValue = "Y") String useYn, @RequestParam(defaultValue = "all") String searchBy,
			@RequestParam(defaultValue = "") String searchValue, @RequestParam(defaultValue = "15") String curPage,
			@RequestParam(defaultValue = "1") String pageNum, @RequestParam(defaultValue = "board") String flag) {

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
			session.setAttribute("BOARD_CONFIG_SEQ", configSeq);
		}
		params.put("seq", configSeq);
		BoardVO BoardConfigVO = BoardDao.selectBoardConfig(params);
		if (CommUtils.isEmpty(BoardConfigVO)) {
			throw new CustomGenericException("BIZ_ERR", "접속 권한이 없습니다.");
		} else {
			model.addAttribute("dataConfig", BoardConfigVO);
			params.put("configSeq", configSeq);
			List<BoardVO> BoardCategoryVOList = BoardDao.selectBoardCategoryList(params);
			if (CommUtils.isEmpty(BoardCategoryVOList)) {
				model.addAttribute("dataCategoryList", BoardCategoryVOList);
			}
			params.clear();
		}

		params.put("configSeq", configSeq);
		params.put("categorySeq", categorySeq);
		params.put("delYn", delYn);
		params.put("searchBy", searchBy);
		params.put("searchValue", searchValue);

		int totalCount = BoardDao.selectCountBoardRecord(params);

		HashMap<String, Object> pageMap = PageUtil.pageMap(totalCount, pageNum, curPage);

		params.put("startNum", pageMap.get("startNum"));
		params.put("endNum", pageMap.get("endNum"));

		List<BoardVO> BoardVOList = BoardDao.selectBoardRecordList(params);
		model.addAttribute("dataList", BoardVOList);

		params.put("noticeYn", "Y");
		List<BoardVO> BoardVONoticeList = BoardDao.selectBoardRecordList(params);
		model.addAttribute("noticeList", BoardVONoticeList);

		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pageMap", pageMap);

		model.addAttribute("searchValue", searchValue);
		model.addAttribute("searchBy", searchBy);
		model.addAttribute("curPage", curPage);
		model.addAttribute("pageNum", pageNum);

		model.addAttribute("configSeq", configSeq);
		model.addAttribute("categorySeq", categorySeq);

		model.addAttribute("flag", flag);
		
		String jspPath = "iWORK/conservatory/board/list";
		if("pdf".equals(BoardConfigVO.getType())) {
			if(BoardVOList.size() > 0) {
				BoardVO BoardVO = BoardVOList.get(0);
				model.addAttribute("data", BoardVO);
			}
			jspPath =  "iWORK/conservatory/board/pdf";
		}else if("album".equals(BoardConfigVO.getType()) || "movie".equals(BoardConfigVO.getType())) {
			jspPath =  "iWORK/conservatory/board/album";
		}
		return jspPath;
	}

	@RequestMapping("/BoardView")
	public String BoardView(HttpServletRequest request, Model model, HttpSession session,
			@RequestParam(defaultValue = "") String menuSeq, @RequestParam(defaultValue = "") String configSeq,
			@RequestParam(defaultValue = "") String categorySeq, @RequestParam(defaultValue = "") String seq,
			@RequestParam(defaultValue = "N") String delYn, @RequestParam(defaultValue = "Y") String useYn,
			@RequestParam(defaultValue = "all") String searchBy, @RequestParam(defaultValue = "") String searchValue,
			@RequestParam(defaultValue = "15") String curPage, @RequestParam(defaultValue = "1") String pageNum,
			@RequestParam(defaultValue = "board") String flag) {

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
			session.setAttribute("BOARD_CONFIG_SEQ", configSeq);
		}
		params.put("seq", configSeq);
		BoardVO BoardConfigVO = BoardDao.selectBoardConfig(params);
		if (CommUtils.isEmpty(BoardConfigVO)) {
			throw new CustomGenericException("BIZ_ERR", "접속 권한이 없습니다.");
		} else {
			model.addAttribute("dataConfig", BoardConfigVO);
			params.put("configSeq", configSeq);
			List<BoardVO> BoardCategoryVOList = BoardDao.selectBoardCategoryList(params);
			model.addAttribute("dataCategoryList", BoardCategoryVOList);
		}

		if (!"0".equals(seq)) {
			params.clear();
			params.put("configSeq", configSeq);
			params.put("categorySeq", categorySeq);
			params.put("seq", seq);
			params.put("delYn", delYn);
			BoardVO BoardVO = BoardDao.selectBoardRecord(params);
			if(!CommUtils.isEmpty(BoardVO)) {
				model.addAttribute("data", BoardVO);
				session.setAttribute("BOARD_RECORD_SEQ", BoardVO.getSeq());
				
				BoardDao.increaseViewCount(BoardVO.getSeq());
				BoardVO replyData = BoardDao.selectReply(BoardVO.getSeq());
				model.addAttribute("replyData", replyData);
			}
		}

		model.addAttribute("searchValue", searchValue);
		model.addAttribute("searchBy", searchBy);
		model.addAttribute("curPage", curPage);
		model.addAttribute("pageNum", pageNum);

		model.addAttribute("configSeq", configSeq);
		model.addAttribute("categorySeq", categorySeq);

		model.addAttribute("flag", flag);
		return "iWORK/conservatory/board/view";
	}

	@RequestMapping("/BoardEdit")
	public String BoardEdit(HttpServletRequest request, Model model, HttpSession session,
			@RequestParam(defaultValue = "") String menuSeq, @RequestParam(defaultValue = "") String configSeq,
			@RequestParam(defaultValue = "") String categorySeq, @RequestParam(defaultValue = "0") String seq,
			@RequestParam(defaultValue = "N") String delYn, @RequestParam(defaultValue = "Y") String useYn,
			@RequestParam(defaultValue = "all") String searchBy, @RequestParam(defaultValue = "") String searchValue,
			@RequestParam(defaultValue = "15") String curPage, @RequestParam(defaultValue = "1") String pageNum,
			@RequestParam(defaultValue = "board") String flag) {

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
			session.setAttribute("BOARD_CONFIG_SEQ", configSeq);
		}
		params.put("seq", configSeq);
		BoardVO BoardConfigVO = BoardDao.selectBoardConfig(params);
		if (CommUtils.isEmpty(BoardConfigVO)) {
			throw new CustomGenericException("BIZ_ERR", "접속 권한이 없습니다.");
		} else {
			model.addAttribute("dataConfig", BoardConfigVO);
			params.put("configSeq", configSeq);
			List<BoardVO> BoardCategoryVOList = BoardDao.selectBoardCategoryList(params);
			model.addAttribute("dataCategoryList", BoardCategoryVOList);
		}

		if (!"0".equals(seq)) {
			params.clear();
			params.put("configSeq", configSeq);
			params.put("categorySeq", categorySeq);
			params.put("seq", seq);
			params.put("delYn", delYn);
			BoardVO BoardVO = BoardDao.selectBoardRecord(params);
			model.addAttribute("data", BoardVO);
		}

		model.addAttribute("searchValue", searchValue);
		model.addAttribute("searchBy", searchBy);
		model.addAttribute("curPage", curPage);
		model.addAttribute("pageNum", pageNum);

		model.addAttribute("configSeq", configSeq);
		model.addAttribute("categorySeq", categorySeq);

		model.addAttribute("flag", flag);
		return "iWORK/conservatory/board/edit";
	}
	
	@RequestMapping("/PdfView")
	public String PdfView(HttpServletRequest request, Model model, HttpSession session,
			@RequestParam(defaultValue = "") String fileSeq) {

		model.addAttribute("fileSeq", fileSeq);
		return "iWORK/conservatory/board//pdfV";
	}

}
