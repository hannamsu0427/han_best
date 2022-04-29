package com.itsp.controller;

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

import com.itsp.common.CommUtils;
import com.itsp.common.CustomGenericException;
import com.itsp.dao.HistoryDao;
import com.itsp.vo.HistoryVO;
import com.itsp.vo.ResponseHeaderVO;
import com.itsp.vo.ResponseVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HistoryCategoryController {

	@Autowired
	HistoryDao HistoryDao;

	private static final Logger logger = LoggerFactory.getLogger(HistoryCategoryController.class);
	
	/***
	 * 리스트
	 ***/
	@RequestMapping("/iWORK/History/CategoryList")
	public String CategoryList(HttpServletRequest request, Model model, HttpSession session,
			@RequestParam String configSeq, @RequestParam(defaultValue = "history") String flag) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("configSeq", configSeq);

		String ssHistoryConfigSeq = session.getAttribute("HISTORY_CONFIG_SEQ").toString();
		if (!configSeq.equals(ssHistoryConfigSeq)) {
			throw new CustomGenericException("BIZ_ERR", "접속 권한이 없습니다.");
		}

		params.put("seq", ssHistoryConfigSeq);
		HistoryVO HistoryConfigVO = HistoryDao.selectConfig(params);
		if (CommUtils.isEmpty(HistoryConfigVO)) {
			throw new CustomGenericException("BIZ_ERR", "접속 권한이 없습니다.");
		} else {
			model.addAttribute("dataConfig", HistoryConfigVO);
		}

		List<HistoryVO> HistoryVOList = HistoryDao.selectCategoryList(params);
		model.addAttribute("dataList", HistoryVOList);

		model.addAttribute("flag", flag);
		return "history/category/list";
	}

	@RequestMapping("/iWORK/History/Category")
	public String Category(HttpSession session, HttpServletRequest request, Model model,
			@RequestParam(defaultValue = "0") String categorySeq, @RequestParam(defaultValue = "history") String flag) {

		Map<String, Object> params = new HashMap<String, Object>();
		String ssHistoryConfigSeq = session.getAttribute("HISTORY_CONFIG_SEQ").toString();

		params.put("seq", ssHistoryConfigSeq);
		HistoryVO HistoryConfigVO = HistoryDao.selectConfig(params);
		if (CommUtils.isEmpty(HistoryConfigVO)) {
			throw new CustomGenericException("BIZ_ERR", "접속 권한이 없습니다.");
		} else {
			model.addAttribute("dataConfig", HistoryConfigVO);
		}

		if (!"0".equals(categorySeq)) {
			params.put("configSeq", ssHistoryConfigSeq);
			params.put("categorySeq", categorySeq);
			HistoryVO HistoryVO = HistoryDao.selectCategory(params);
			if (!CommUtils.isEmpty(HistoryVO)) {
				session.setAttribute("HISTORY_CATEGORY_SEQ", HistoryVO.getCategorySeq());
				model.addAttribute("data", HistoryVO);
			}
		}
		model.addAttribute("flag", flag);
		return "history/category/edit";
	}

	@RequestMapping(value = "/iWORK/History/Category/saveProcData", method = RequestMethod.POST)
	public @ResponseBody ResponseVO saveProcData(@ModelAttribute HistoryVO HistoryVO, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ResponseVO resVO = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		boolean isInsert = ("".equals(CommUtils.checkNull(HistoryVO.getCategorySeq())));
		if (isInsert) {
			String seq = HistoryDao.seqNextVal();
			HistoryVO.setCategorySeq(seq);

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("configSeq", HistoryVO.getConfigSeq());
			int orderNum = HistoryDao.selectCountCategory(params);
			orderNum = orderNum + 1;
			HistoryVO.setOrderNum(String.valueOf(orderNum));
		}

		try {
			HistoryDao.saveProcCategory(HistoryVO);
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

	@RequestMapping(headers = "Content-Type=application/json", value = "/iWORK/History/Category/deleteProcData.json", method = RequestMethod.POST)
	public @ResponseBody ResponseVO deleteProcData(@RequestBody HashMap<String, Object> hashMap) throws Exception {
		ResponseVO response = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		try {
			String[] seqArr = ((String) hashMap.get("seq")).split(",");
			for (String categorySeq : seqArr) {
				HistoryDao.deleteProcCategory(categorySeq);
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
	@RequestMapping(headers = "Content-Type=application/json", value = "/iWORK/History/Category/orderNumProc.json", method = RequestMethod.POST)
	public @ResponseBody ResponseVO orderNumProc(@RequestBody HashMap<String, Object> Map) throws Exception {

		ResponseVO response = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		try {
			ArrayList<HashMap> dataList = (ArrayList<HashMap>) Map.get("data");
			HistoryDao.orderNumProcCategory(dataList);
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
