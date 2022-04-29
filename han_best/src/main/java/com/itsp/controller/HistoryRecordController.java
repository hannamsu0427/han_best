package com.itsp.controller;

import java.net.Inet4Address;
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
import com.itsp.dao.AttachFileDao;
import com.itsp.dao.HistoryDao;
import com.itsp.vo.HistoryVO;
import com.itsp.vo.ResponseHeaderVO;
import com.itsp.vo.ResponseVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HistoryRecordController {

	@Autowired
	HistoryDao HistoryDao;

	@Autowired
	AttachFileDao AttachFileDao;

	private static final Logger logger = LoggerFactory.getLogger(HistoryRecordController.class);

	/***
	 * 리스트
	 ***/
	@RequestMapping("/iWORK/History/RecordList")
	public String RecordList(HttpServletRequest request, Model model, HttpSession session,
			@RequestParam(defaultValue = "") String configSeq,
			@RequestParam(defaultValue = "") String categorySeq, @RequestParam(defaultValue = "history") String flag) {

		Map<String, Object> params = new HashMap<String, Object>();
		if(!"".equals(configSeq)) {
			session.setAttribute("HISTORY_CONFIG_SEQ", configSeq);
		}

		String ssHistoryConfigSeq = session.getAttribute("HISTORY_CONFIG_SEQ").toString();
		params.put("seq", ssHistoryConfigSeq);
		HistoryVO HistoryConfigVO = HistoryDao.selectConfig(params);
		if (CommUtils.isEmpty(HistoryConfigVO)) {
			throw new CustomGenericException("BIZ_ERR", "접속 권한이 없습니다.");
		} else {
			model.addAttribute("dataConfig", HistoryConfigVO);
			params.put("configSeq", ssHistoryConfigSeq);
			List<HistoryVO> HistoryCategoryVOList = HistoryDao.selectCategoryList(params);
			if (!CommUtils.isEmpty(HistoryCategoryVOList)) {
				model.addAttribute("categoryList", HistoryCategoryVOList);
				if("".equals(categorySeq)) {
					categorySeq = HistoryCategoryVOList.get(0).getCategorySeq();
				}
			}
			
		}
		params.clear();

		params.put("configSeq", ssHistoryConfigSeq);
		params.put("categorySeq", categorySeq);
		List<HistoryVO> HistoryVOList = HistoryDao.selectRecordList(params);
		model.addAttribute("dataList", HistoryVOList);

		model.addAttribute("categorySeq", categorySeq);
		model.addAttribute("flag", flag);
		return "history/record/list";
	}

	@RequestMapping("/iWORK/History/Record")
	public String Record(HttpSession session, HttpServletRequest request, Model model,
			@RequestParam(defaultValue = "0") String seq, @RequestParam(defaultValue = "history") String flag) {

		Map<String, Object> params = new HashMap<String, Object>();
		
		String ssHistoryConfigSeq = session.getAttribute("HISTORY_CONFIG_SEQ").toString();
		params.put("seq", ssHistoryConfigSeq);
		HistoryVO HistoryConfigVO = HistoryDao.selectConfig(params);
		if (CommUtils.isEmpty(HistoryConfigVO)) {
			throw new CustomGenericException("BIZ_ERR", "접속 권한이 없습니다.");
		} else {
			model.addAttribute("dataConfig", HistoryConfigVO);
			params.put("configSeq", ssHistoryConfigSeq);
			List<HistoryVO> HistoryCategoryVOList = HistoryDao.selectCategoryList(params);
			if (!CommUtils.isEmpty(HistoryCategoryVOList)) {
				model.addAttribute("categoryList", HistoryCategoryVOList);
			}
			
		}
		params.clear();

		if (!"0".equals(seq)) {
			params.put("seq", seq);
			HistoryVO HistoryVO = HistoryDao.selectRecord(params);
			if (!CommUtils.isEmpty(HistoryVO)) {
				session.setAttribute("HISTORY_RECORD_SEQ", HistoryVO.getSeq());
				model.addAttribute("data", HistoryVO);
			}
		}
		model.addAttribute("flag", flag);
		return "history/record/edit";
	}

	@RequestMapping(value = "/History/Record/saveProcData", method = RequestMethod.POST)
	public @ResponseBody ResponseVO saveProcData(@ModelAttribute HistoryVO HistoryVO, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ResponseVO resVO = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		try {
			String ip = Inet4Address.getLocalHost().getHostAddress();

			boolean isInsert = ("".equals(CommUtils.checkNull(HistoryVO.getSeq())));
			if (isInsert) {
				String seq = HistoryDao.seqNextVal();
				HistoryVO.setSeq(seq);

				HistoryVO.setRegIp(ip);
			} else {
				HistoryVO.setModIp(ip);
			}

			HistoryDao.saveProcRecord(HistoryVO);
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

	@RequestMapping(headers = "Content-Type=application/json", value = "/History/Record/deleteProcData.json", method = RequestMethod.POST)
	public @ResponseBody ResponseVO deleteProcData(@RequestBody HashMap<String, Object> hashMap) throws Exception {
		ResponseVO response = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		try {
			String[] seqArr = ((String) hashMap.get("seq")).split(",");
			for (String seq : seqArr) {
				HistoryDao.deleteProcRecord(seq);
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

	@RequestMapping(headers = "Content-Type=application/json", value = "/History/Record/useProcData.json", method = RequestMethod.POST)
	public @ResponseBody ResponseVO useProcData(@RequestBody HashMap<String, Object> hashMap) throws Exception {
		ResponseVO response = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		try {
			String[] seqArr = ((String) hashMap.get("seq")).split(",");
			String delYn = (String) hashMap.get("delYn");
			for (String seq : seqArr) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("seq", seq);
				params.put("delYn", delYn);
				HistoryDao.useProcRecord(params);
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
