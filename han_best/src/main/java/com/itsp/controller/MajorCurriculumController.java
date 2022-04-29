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
import com.itsp.dao.MajorDao;
import com.itsp.vo.MajorVO;
import com.itsp.vo.ResponseHeaderVO;
import com.itsp.vo.ResponseVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MajorCurriculumController {

	@Autowired
	MajorDao MajorDao;

	private static final Logger logger = LoggerFactory.getLogger(MajorCurriculumController.class);

	/***
	 * 리스트
	 ***/
	@RequestMapping("/iWORK/Major/CurriculumList")
	public String CurriculumList(HttpServletRequest request, Model model, HttpSession session,
			@RequestParam(defaultValue = "major") String flag) {

		Map<String, Object> params = new HashMap<String, Object>();

		String aboutSeq = session.getAttribute("MAJOR_ABOUT_SEQ").toString();
		if ("".equals(aboutSeq)) {
			throw new CustomGenericException("BIZ_ERR", "접속 권한이 없습니다.");
		} else {
			params.put("seq", aboutSeq);
			MajorVO MajorVO = MajorDao.selectAbout(params);
			if (!CommUtils.isEmpty(MajorVO)) {
				model.addAttribute("dataAbout", MajorVO);
			}
		}

		params.clear();

		params.put("aboutSeq", aboutSeq);
		int totalCount = MajorDao.selectCountCurriculum(params);
		model.addAttribute("totalCount", totalCount);

		List<MajorVO> MajorVOList = MajorDao.selectListCurriculum(params);
		model.addAttribute("dataList", MajorVOList);

		model.addAttribute("flag", flag);
		return "major/curriculum/list";
	}

	@RequestMapping("/iWORK/Major/Curriculum")
	public String Record(HttpSession session, HttpServletRequest request, Model model,
			@RequestParam(defaultValue = "0") String seq, @RequestParam(defaultValue = "major") String flag) {

		Map<String, Object> params = new HashMap<String, Object>();

		String aboutSeq = session.getAttribute("MAJOR_ABOUT_SEQ").toString();
		if ("".equals(aboutSeq)) {
			throw new CustomGenericException("BIZ_ERR", "접속 권한이 없습니다.");
		}

		if (!"0".equals(seq)) {
			params.put("aboutSeq", aboutSeq);
			params.put("seq", seq);
			MajorVO MajorVO = MajorDao.selectCurriculum(params);
			if (!CommUtils.isEmpty(MajorVO)) {
				session.setAttribute("MAJOR_CURRICULUM_SEQ", MajorVO.getSeq());
				model.addAttribute("data", MajorVO);
			}
		}

		model.addAttribute("flag", flag);
		return "major/curriculum/edit";
	}

	@RequestMapping(value = "/Major/Curriculum/saveProcData", method = RequestMethod.POST)
	public @ResponseBody ResponseVO saveProcData(@ModelAttribute MajorVO MajorVO, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ResponseVO resVO = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		try {
			String ip = Inet4Address.getLocalHost().getHostAddress();
			boolean isInsert = ("".equals(CommUtils.checkNull(MajorVO.getSeq())));
			if (isInsert) {
				String seq = MajorDao.seqNextVal();
				MajorVO.setSeq(seq);
				MajorVO.setRegIp(ip);

				Map<String, Object> params = new HashMap<String, Object>();
				params.put("aboutSeq", MajorVO.getAboutSeq());
				int totalCount = MajorDao.selectCountCurriculum(params);
				MajorVO.setOrderNum(String.valueOf(totalCount + 1));
			} else {
				MajorVO.setModIp(ip);
			}

			MajorDao.saveProcCurriculum(MajorVO);
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

	@RequestMapping(headers = "Content-Type=application/json", value = "/Major/Curriculum/deleteProcData.json", method = RequestMethod.POST)
	public @ResponseBody ResponseVO deleteProcData(@RequestBody HashMap<String, Object> hashMap) throws Exception {
		ResponseVO response = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		try {
			String[] seqArr = ((String) hashMap.get("seq")).split(",");
			for (String seq : seqArr) {
				MajorDao.deleteProcCurriculum(seq);
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
	@RequestMapping(headers = "Content-Type=application/json", value = "/iWORK/Major/Curriculum/orderNumProc.json", method = RequestMethod.POST)
	public @ResponseBody ResponseVO orderNumProc(@RequestBody HashMap<String, Object> Map) throws Exception {

		ResponseVO response = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		try {
			ArrayList<HashMap> dataList = (ArrayList<HashMap>) Map.get("data");
			MajorDao.orderNumProcCurriculum(dataList);
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
