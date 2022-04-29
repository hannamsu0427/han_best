package com.itsp.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

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

import com.itsp.common.CommUtils;
import com.itsp.common.Menu;
import com.itsp.dao.MenuDao;
import com.itsp.vo.MenuVO;
import com.itsp.vo.ResponseHeaderVO;
import com.itsp.vo.ResponseVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MenuController {

	@Autowired
	MenuDao menuDao;
	Menu menu = new Menu();

	private static final Logger logger = LoggerFactory.getLogger(MenuController.class);

	/***
	 * 리스트
	 ***/
	@RequestMapping("/iWORK/Menu")
	public String menu(HttpServletRequest request, Model model, HttpSession session,
			@RequestParam(defaultValue = "0") String seq, @RequestParam(defaultValue = "") String parentSeq,
			@RequestParam(defaultValue = "") String depth, @RequestParam(defaultValue = "menu") String flag) {

		menu.getMenuList(request, model, menuDao);

		if (!"0".equals(seq) && !"".equals(seq)) {
			MenuVO menuVO = menuDao.selectMenu(seq);
			if(!CommUtils.isEmpty(menuVO)) {
				model.addAttribute("data", menuVO);
				menu.getCurMenuPath(model, menuDao, menuVO.getSeq());
			}
		}
		
		model.addAttribute("seq", seq);
		model.addAttribute("parentSeq", parentSeq);
		model.addAttribute("depth", depth);

		logger.info("parentSeq = " + parentSeq);
		logger.info("depth = " + depth);
		
		model.addAttribute("flag", flag);
		return "menu/edit";
	}

	@RequestMapping(value = "/iWORK/Menu/saveDataProc", method = RequestMethod.POST)
	public @ResponseBody ResponseVO saveDataProc(@ModelAttribute MenuVO menuVO, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ResponseVO resVO = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		boolean isInsert = ("".equals(CommUtils.checkNull(menuVO.getSeq())));
		if (isInsert) {
			String seq = menuDao.seqNextVal();
			menuVO.setSeq(seq);
		}

		String parentSeq = menuVO.getParentSeq();
		String parentSortOrder = "";

		if (parentSeq == null || "".equals(parentSeq) || "0".equals(parentSeq)) {
			menuVO.setParentSeq("0");
			menuVO.setDepth("1");
			parentSeq = "0";
		} else {
			MenuVO parentMenuVO = menuDao.selectMenu(parentSeq);
			parentSortOrder = parentMenuVO.getSortOrder();
			menuVO.setDepth(Integer.toString(Integer.parseInt(parentMenuVO.getDepth()) + 1));
		}
	
		// 001:001:001
		String maxSortOrder = menuDao.menuMaxSiblingSortOder(parentSeq);
		if (maxSortOrder == null || "".equals(maxSortOrder)) {
			if (!"".equals(parentSortOrder)) {
				maxSortOrder += parentSortOrder + ":";
			}
			maxSortOrder += "000";
		}

		String[] sortOrderArray = maxSortOrder.split(":");
		int intMaxSortOder = Integer.parseInt(sortOrderArray[sortOrderArray.length - 1]) + 1;
		String strMaxSortOrder = "";
		if (!"".equals(parentSortOrder)) {
			strMaxSortOrder += parentSortOrder + ":";
		}

		DecimalFormat fmt = new DecimalFormat("000");
		strMaxSortOrder += fmt.format(intMaxSortOder);
		menuVO.setSortOrder(strMaxSortOrder);
		
		String depth = menuVO.getDepth();
		/*
		if(3 == Integer.parseInt(depth)) {
			menuVO.setTreeType("tabMenu");
		}else {
			menuVO.setTreeType("menu");
		}
		*/
		try {
			menuDao.saveDataProc(menuVO);
			responseHeader.setCode(ResponseHeaderVO.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(CommUtils.stackTraceToString(e));
			responseHeader.setCode(ResponseHeaderVO.FAIL);
			responseHeader.setMessage(ResponseHeaderVO.FAIL_MESSAGE);
		}

		resVO.setHeader(responseHeader);
		return resVO;
	}

	@RequestMapping(headers = "Content-Type=application/json", value = "/iWORK/Menu/deleteDataProc.json", method = RequestMethod.POST)
	public @ResponseBody ResponseVO deleteDataProc(@RequestBody HashMap<String, Object> hashMap) throws Exception {
		ResponseVO response = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		try {
			String[] seqArr = ((String) hashMap.get("seq")).split(",");
			for (String seq : seqArr) {
				menuDao.deleteDataProc(seq);
			}
			responseHeader.setCode(ResponseHeaderVO.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			responseHeader.setCode(ResponseHeaderVO.FAIL);
			responseHeader.setMessage(ResponseHeaderVO.FAIL_MESSAGE);
		}

		response.setHeader(responseHeader);
		return response;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(headers = "Content-Type=application/json", value = "/iWORK/Menu/sortOrderProc.json", method = RequestMethod.POST)
	public @ResponseBody ResponseVO sortOrderProc(@RequestBody HashMap<String, Object> map, Model model)
			throws Exception {

		ResponseVO response = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		try {
			ArrayList<HashMap> dataList = (ArrayList<HashMap>) map.get("data");
			menuDao.sortOrderUpdate(dataList);
			responseHeader.setCode(ResponseHeaderVO.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			responseHeader.setCode(ResponseHeaderVO.FAIL);
			responseHeader.setMessage(ResponseHeaderVO.FAIL_MESSAGE);
		}

		response.setHeader(responseHeader);
		return response;
	}

}
