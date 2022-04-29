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
import com.itsp.dao.FacilityDao;
import com.itsp.vo.FacilityVO;
import com.itsp.vo.ResponseHeaderVO;
import com.itsp.vo.ResponseVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class FacilityConfigController {

	@Autowired
	FacilityDao FacilityDao;

	private static final Logger logger = LoggerFactory.getLogger(FacilityConfigController.class);

	@RequestMapping(headers = "Content-Type=application/json", value = "/iWORK/Facility/Config/JsonList.json", method = RequestMethod.POST)
	public @ResponseBody ResponseVO JsonList(@RequestBody HashMap<String, Object> hashMap, HttpSession session)
			throws Exception {

		ResponseVO response = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();
		List<FacilityVO> dataList = new ArrayList<FacilityVO>();

		try {
			hashMap.put("useYn", "Y");
			dataList = FacilityDao.selectConfigList(hashMap);
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
	@RequestMapping("/iWORK/Facility/ConfigList")
	public String ConfigList(HttpServletRequest request, Model model, HttpSession session,
			@RequestParam(defaultValue = "all") String searchBy, @RequestParam(defaultValue = "") String searchValue,
			@RequestParam(defaultValue = "15") String curPage, @RequestParam(defaultValue = "1") String pageNum,
			@RequestParam(defaultValue = "facility") String flag) {

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("searchBy", searchBy);
		params.put("searchValue", searchValue);

		int totalCount = FacilityDao.selectCountConfig(params);

		HashMap<String, Object> pageMap = PageUtil.pageMap(totalCount, pageNum, curPage);

		params.put("startNum", pageMap.get("startNum"));
		params.put("endNum", pageMap.get("endNum"));

		List<FacilityVO> FacilityVOList = FacilityDao.selectConfigList(params);
		model.addAttribute("dataList", FacilityVOList);

		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pageMap", pageMap);

		model.addAttribute("searchValue", searchValue);
		model.addAttribute("searchBy", searchBy);
		model.addAttribute("curPage", curPage);

		model.addAttribute("flag", flag);
		logger.info("ddddd");
		return "facility/config/list";
	}

	@RequestMapping("/iWORK/Facility/Config")
	public String Config(HttpSession session, HttpServletRequest request, Model model,
			@RequestParam(defaultValue = "0") String seq, @RequestParam(defaultValue = "") String searchBy,
			@RequestParam(defaultValue = "") String searchValue, @RequestParam(defaultValue = "15") String curPage,
			@RequestParam(defaultValue = "1") String pageNum, @RequestParam(defaultValue = "facility") String flag) {

		Map<String, Object> params = new HashMap<String, Object>();

		if (!"0".equals(seq)) {
			params.put("seq", seq);
			FacilityVO FacilityVO = FacilityDao.selectConfig(params);
			if (!CommUtils.isEmpty(FacilityVO)) {
				session.setAttribute("FACILITY_CONFIG_SEQ", FacilityVO.getSeq());
				model.addAttribute("data", FacilityVO);
			}
		}

		model.addAttribute("pageNum", pageNum);
		model.addAttribute("searchValue", searchValue);
		model.addAttribute("searchBy", searchBy);
		model.addAttribute("curPage", curPage);

		model.addAttribute("flag", flag);
		return "facility/config/edit";
	}

	@RequestMapping(value = "/iWORK/Facility/Config/saveProcData", method = RequestMethod.POST)
	public @ResponseBody ResponseVO saveProcData(@ModelAttribute FacilityVO FacilityVO, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ResponseVO resVO = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		try {
			boolean isInsert = ("".equals(CommUtils.checkNull(FacilityVO.getSeq())));
			if (isInsert) {
				String seq = FacilityDao.seqNextVal();
				FacilityVO.setSeq(seq);
			}

			FacilityDao.saveProcConfig(FacilityVO);
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

	@RequestMapping(headers = "Content-Type=application/json", value = "/iWORK/Facility/Config/deleteProcData.json", method = RequestMethod.POST)
	public @ResponseBody ResponseVO deleteProcData(@RequestBody HashMap<String, Object> hashMap) throws Exception {
		ResponseVO response = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		try {
			String[] seqArr = ((String) hashMap.get("seq")).split(",");
			for (String seq : seqArr) {
				FacilityDao.deleteProcConfig(seq);
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
