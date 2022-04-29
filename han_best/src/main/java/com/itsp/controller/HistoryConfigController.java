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
import com.itsp.common.PageUtil;
import com.itsp.dao.HistoryDao;
import com.itsp.vo.HistoryVO;
import com.itsp.vo.ResponseHeaderVO;
import com.itsp.vo.ResponseVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HistoryConfigController {

	@Autowired
	HistoryDao HistoryDao;

	private static final Logger logger = LoggerFactory.getLogger(HistoryConfigController.class);

	@RequestMapping(headers = "Content-Type=application/json", value = "/iWORK/History/Config/JsonList.json", method = RequestMethod.POST)
	public @ResponseBody ResponseVO JsonList(@RequestBody HashMap<String, Object> hashMap, HttpSession session)
			throws Exception {

		ResponseVO response = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();
		List<HistoryVO> dataList = new ArrayList<HistoryVO>();

		try {
			hashMap.put("useYn", "Y");
			dataList = HistoryDao.selectConfigList(hashMap);
			responseHeader.setCode(ResponseHeaderVO.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(CommUtils.stackTraceToString(e));
			responseHeader.setCode(ResponseHeaderVO.FAIL);
			responseHeader.setMessage(ResponseHeaderVO.FAIL_MESSAGE);
		}

		response.setHeader(responseHeader);
		response.setBody(dataList);
		return response;
	}

	/***
	 * 리스트
	 ***/
	@RequestMapping("/iWORK/History/ConfigList")
	public String ConfigList(HttpServletRequest request, Model model, HttpSession session,
			@RequestParam(defaultValue = "1") String pageNum, @RequestParam(defaultValue = "history") String flag) {

		Map<String, Object> params = new HashMap<String, Object>();

		int totalCount = HistoryDao.selectCountConfig(params);

		HashMap<String, Object> pageMap = PageUtil.pageMap(totalCount, pageNum);

		params.put("startNum", pageMap.get("startNum"));
		params.put("endNum", pageMap.get("endNum"));

		List<HistoryVO> HistoryVOList = HistoryDao.selectConfigList(params);
		model.addAttribute("dataList", HistoryVOList);

		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pageMap", pageMap);
		model.addAttribute("flag", flag);
		return "history/config/list";
	}

	@RequestMapping("/iWORK/History/Config")
	public String Config(HttpSession session, HttpServletRequest request, Model model,
			@RequestParam(defaultValue = "0") String seq, @RequestParam(defaultValue = "1") String pageNum,
			@RequestParam(defaultValue = "history") String flag) {

		Map<String, Object> params = new HashMap<String, Object>();

		if (!"0".equals(seq)) {
			params.put("seq", seq);
			HistoryVO HistoryVO = HistoryDao.selectConfig(params);
			if (!CommUtils.isEmpty(HistoryVO)) {
				session.setAttribute("HISTORY_CONFIG_SEQ", HistoryVO.getSeq());
				model.addAttribute("data", HistoryVO);
			}
		}

		model.addAttribute("pageNum", pageNum);
		model.addAttribute("flag", flag);
		return "history/config/edit";
	}

	@RequestMapping(value = "/iWORK/History/Config/saveProcData", method = RequestMethod.POST)
	public @ResponseBody ResponseVO saveProcData(@ModelAttribute HistoryVO HistoryVO, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ResponseVO resVO = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		try {
			boolean isInsert = ("".equals(CommUtils.checkNull(HistoryVO.getSeq())));
			if (isInsert) {
				String seq = HistoryDao.seqNextVal();
				HistoryVO.setSeq(seq);
			}			
			HistoryDao.saveProcConfig(HistoryVO);
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

	@RequestMapping(headers = "Content-Type=application/json", value = "/iWORK/History/Config/deleteProcData.json", method = RequestMethod.POST)
	public @ResponseBody ResponseVO deleteProcData(@RequestBody HashMap<String, Object> hashMap) throws Exception {
		ResponseVO response = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		try {
			String[] seqArr = ((String) hashMap.get("seq")).split(",");
			for (String seq : seqArr) {
				HistoryDao.deleteProcConfig(seq);
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

}
