package com.itsp.controller;

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

import com.itsp.common.CommUtils;
import com.itsp.common.CustomGenericException;
import com.itsp.common.PageUtil;
import com.itsp.dao.EditorDao;
import com.itsp.vo.BoardVO;
import com.itsp.vo.EditorVO;
import com.itsp.vo.ResponseHeaderVO;
import com.itsp.vo.ResponseVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class EditorRecordController {

	@Autowired
	EditorDao EditorDao;

	private static final Logger logger = LoggerFactory.getLogger(EditorRecordController.class);
	
	@RequestMapping(headers = "Content-Type=application/json", value = "/Editor/Record/JsonList.json", method = RequestMethod.POST)
	public @ResponseBody ResponseVO JsonList(@RequestBody HashMap<String, Object> hashMap, HttpSession session)
			throws Exception {

		ResponseVO response = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();
		List<EditorVO> dataList = new ArrayList<EditorVO>();

		try {
			dataList = EditorDao.selectEditorRecordList(hashMap);
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
	@RequestMapping("/iWORK/Editor/Record")
	public String RecordList(HttpServletRequest request, Model model, HttpSession session,
			@RequestParam String configSeq, @RequestParam(defaultValue = "1") String pageNum,
			@RequestParam(defaultValue = "editor") String flag) {

		Map<String, Object> params = new HashMap<String, Object>();

		String ssConfigSeq = session.getAttribute("EDITOR_CONFIG_SEQ").toString();
		params.put("seq", ssConfigSeq);
		EditorVO EditorConfigVO = EditorDao.selectEditorConfig(params);
		if (CommUtils.isEmpty(EditorConfigVO)) {
			throw new CustomGenericException("BIZ_ERR", "접속 권한이 없습니다.");
		} else {
			model.addAttribute("dataConfig", EditorConfigVO);
		}
		params.clear();

		params.put("configSeq", ssConfigSeq);
		int totalCount = EditorDao.selectCountEditorRecord(params);

		HashMap<String, Object> pageMap = PageUtil.pageMap(totalCount, pageNum);

		params.put("startNum", pageMap.get("startNum"));
		params.put("endNum", pageMap.get("endNum"));

		List<EditorVO> EditorVOList = EditorDao.selectEditorRecordList(params);
		model.addAttribute("dataList", EditorVOList);

		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pageMap", pageMap);
		
		model.addAttribute("flag", flag);
		return "editor/record/edit";
	}


	@RequestMapping(value = "/Editor/Record/insertProcData", method = RequestMethod.POST)
	public @ResponseBody ResponseVO insertProcData(@ModelAttribute EditorVO EditorVO, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ResponseVO resVO = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		try {
			String ip = Inet4Address.getLocalHost().getHostAddress();
			String seq = EditorDao.seqNextVal();
			EditorVO.setSeq(seq);
			EditorVO.setRegIp(ip);

			EditorDao.insertProcEditorRecord(EditorVO);
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

}
