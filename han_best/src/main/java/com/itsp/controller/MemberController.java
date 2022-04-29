package com.itsp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itsp.common.CommUtils;
import com.itsp.dao.HjMemberDao;
import com.itsp.dao.MemberDao;
import com.itsp.vo.MemberVO;
import com.itsp.vo.ResponseHeaderVO;
import com.itsp.vo.ResponseVO;

@Controller
public class MemberController {

	@Autowired
	MemberDao MemberDao;

	@Autowired
	HjMemberDao HjMemberDao;

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(headers = "Content-Type=application/json", value = "/iWORK/Member/dataSaveProc.json", method = RequestMethod.POST)
	public @ResponseBody ResponseVO dataSaveProc(@RequestBody HashMap<String, Object> map) throws Exception {
		ResponseVO response = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		try {
			ArrayList<HashMap> dataList = (ArrayList<HashMap>) map.get("data");
			int inx;
			for (inx = 0; inx < dataList.size(); inx++) {
				HashMap<String, Object> dataMap = (HashMap) dataList.get(inx);
				MemberDao.insertDataProc(dataMap);
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

	@RequestMapping(headers = "Content-Type=application/json", value = "/iWORK/Member/deleteDataProc.json", method = RequestMethod.POST)
	public @ResponseBody ResponseVO deleteDataProc(@RequestBody HashMap<String, Object> hashMap) throws Exception {
		ResponseVO response = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		try {
			logger.info(hashMap.toString());
			String[] dataArr = ((String) hashMap.get("data")).split(",");
			for (String user_id : dataArr) {
				MemberDao.deleteDataProc(user_id);
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

	@SuppressWarnings("unchecked")
	@RequestMapping(headers = "Content-Type=application/json", value = "/iWORK/Member/JsonList.json", method = RequestMethod.POST)
	public @ResponseBody ResponseVO MemberJsonList(@RequestBody HashMap<String, Object> map) throws Exception {

		ResponseVO response = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		ArrayList<HashMap<String, Object>> body = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> dataInfo = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();

		try {
			ObjectMapper m = new ObjectMapper();

			List<MemberVO> ajaxList = MemberDao.selectDataList(map);
			if (ajaxList != null && ajaxList.size() > 0) {
				dataList = m.convertValue(ajaxList, ArrayList.class);
			}
			dataInfo.put("dataList", dataList);
			body.add(dataInfo);

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

	@SuppressWarnings("unchecked")
	@RequestMapping(headers = "Content-Type=application/json", value = "/iWORK/hjMember/JsonList.json", method = RequestMethod.POST)
	public @ResponseBody ResponseVO HjMemberJsonList(@RequestBody HashMap<String, Object> map) throws Exception {

		ResponseVO response = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		ArrayList<HashMap<String, Object>> body = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> dataInfo = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();

		try {
			ObjectMapper m = new ObjectMapper();

			List<MemberVO> ajaxList = HjMemberDao.selectDataList(map);
			if (ajaxList != null && ajaxList.size() > 0) {
				dataList = m.convertValue(ajaxList, ArrayList.class);
			}
			dataInfo.put("dataList", dataList);

			int count = HjMemberDao.totalCount(map);
			body.add(dataInfo);

			responseHeader.setCode(ResponseHeaderVO.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(CommUtils.stackTraceToString(e));
			responseHeader.setCode(ResponseHeaderVO.FAIL);
			responseHeader.setMessage(ResponseHeaderVO.FAIL_MESSAGE);
		}

		response.setHeader(responseHeader);
		response.setBody(dataList);
		logger.info("map = " + map);
		return response;
	}

	@RequestMapping("/iWORK/MemberList")
	public String MemberList(Model model, HttpServletRequest request, HttpSession session,
			@RequestParam(defaultValue = "member") String flag) {

		model.addAttribute("flag", flag);
		return "member/list";
	}

}
