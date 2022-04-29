package com.itsp.controller;

import java.net.Inet4Address;
import java.util.Calendar;
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
import com.itsp.dao.ScheduleDao;
import com.itsp.vo.ResponseHeaderVO;
import com.itsp.vo.ResponseVO;
import com.itsp.vo.ScheduleVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class ScheduleRecordController {

	@Autowired
	ScheduleDao ScheduleDao;

	@Autowired
	AttachFileDao AttachFileDao;

	private static final Logger logger = LoggerFactory.getLogger(ScheduleRecordController.class);

	/***
	 * 리스트
	 ***/
	@RequestMapping("/iWORK/Schedule/RecordList")
	public String RecordList(HttpServletRequest request, Model model, HttpSession session,
			@RequestParam(defaultValue = "") String configSeq, @RequestParam(defaultValue = "") String startYear,
			@RequestParam(defaultValue = "schedule") String flag) {

		Map<String, Object> params = new HashMap<String, Object>();
		if (!"".equals(configSeq)) {
			session.setAttribute("SCHEDULE_CONFIG_SEQ", configSeq);
		}

		String ssConfigSeq = session.getAttribute("SCHEDULE_CONFIG_SEQ").toString();
		params.put("seq", ssConfigSeq);
		ScheduleVO ScheduleConfigVO = ScheduleDao.selectConfig(params);
		if (CommUtils.isEmpty(ScheduleConfigVO)) {
			throw new CustomGenericException("BIZ_ERR", "접속 권한이 없습니다.");
		} else {
			model.addAttribute("dataConfig", ScheduleConfigVO);
		}
		params.clear();
		
		params.put("configSeq", ssConfigSeq);
		Calendar c = Calendar.getInstance(); //객체 생성 및 현재 일시분초...셋팅
		String nowYear = String.valueOf(c.get(Calendar.YEAR));
		if("".equals(startYear)) {
			startYear = nowYear;
		}
		params.put("startYear", startYear);
		List<ScheduleVO> ScheduleVOList = ScheduleDao.selectRecordList(params);
		model.addAttribute("dataList", ScheduleVOList);

		model.addAttribute("startYear", startYear);
		model.addAttribute("flag", flag);
		return "schedule/record/list";
	}

	@RequestMapping("/iWORK/Schedule/Record")
	public String Record(HttpSession session, HttpServletRequest request, Model model,
			@RequestParam(defaultValue = "0") String seq, @RequestParam(defaultValue = "schedule") String flag) {

		Map<String, Object> params = new HashMap<String, Object>();

		String ssConfigSeq = session.getAttribute("SCHEDULE_CONFIG_SEQ").toString();
		params.put("seq", ssConfigSeq);
		ScheduleVO ScheduleConfigVO = ScheduleDao.selectConfig(params);
		if (CommUtils.isEmpty(ScheduleConfigVO)) {
			throw new CustomGenericException("BIZ_ERR", "접속 권한이 없습니다.");
		} else {
			model.addAttribute("dataConfig", ScheduleConfigVO);
		}
		params.clear();

		if (!"0".equals(seq)) {
			params.put("configSeq", ssConfigSeq);
			params.put("seq", seq);
			ScheduleVO ScheduleVO = ScheduleDao.selectRecord(params);
			if (!CommUtils.isEmpty(ScheduleVO)) {
				session.setAttribute("Schedule_RECORD_SEQ", ScheduleVO.getSeq());
				model.addAttribute("data", ScheduleVO);
			}
		}
		model.addAttribute("flag", flag);
		return "schedule/record/edit";
	}

	@RequestMapping(value = "/Schedule/Record/saveProcData", method = RequestMethod.POST)
	public @ResponseBody ResponseVO saveProcData(@ModelAttribute ScheduleVO ScheduleVO, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ResponseVO resVO = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		try {
			String ip = Inet4Address.getLocalHost().getHostAddress();

			boolean isInsert = ("".equals(CommUtils.checkNull(ScheduleVO.getSeq())));
			if (isInsert) {
				String seq = ScheduleDao.seqNextVal();
				ScheduleVO.setSeq(seq);

				ScheduleVO.setRegIp(ip);
			} else {
				ScheduleVO.setModIp(ip);
			}

			ScheduleDao.saveProcRecord(ScheduleVO);
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

	@RequestMapping(headers = "Content-Type=application/json", value = "/Schedule/Record/deleteProcData.json", method = RequestMethod.POST)
	public @ResponseBody ResponseVO deleteProcData(@RequestBody HashMap<String, Object> hashMap) throws Exception {
		ResponseVO response = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		try {
			String[] seqArr = ((String) hashMap.get("seq")).split(",");
			for (String seq : seqArr) {
				ScheduleDao.deleteProcRecord(seq);
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

	@RequestMapping(headers = "Content-Type=application/json", value = "/Schedule/Record/useProcData.json", method = RequestMethod.POST)
	public @ResponseBody ResponseVO useProcData(@RequestBody HashMap<String, Object> hashMap) throws Exception {
		ResponseVO response = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		try {
			String[] seqArr = ((String) hashMap.get("seq")).split(",");
			String useYn = (String) hashMap.get("useYn");
			for (String seq : seqArr) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("seq", seq);
				params.put("useYn", useYn);
				ScheduleDao.useProcRecord(params);
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
