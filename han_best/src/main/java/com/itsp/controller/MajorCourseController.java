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
import com.itsp.dao.MajorDao;
import com.itsp.vo.MajorVO;
import com.itsp.vo.ResponseHeaderVO;
import com.itsp.vo.ResponseVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MajorCourseController {

	@Autowired
	MajorDao MajorDao;

	private static final Logger logger = LoggerFactory.getLogger(MajorCourseController.class);

	/***
	 * 리스트
	 ***/
	@RequestMapping("/iWORK/Major/CourseList")
	public String CourseList(HttpServletRequest request, Model model, HttpSession session,
			@RequestParam(defaultValue = "15") String curPage, @RequestParam(defaultValue = "1") String pageNum,
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
		int totalCount = MajorDao.selectCountCourse(params);
		model.addAttribute("totalCount", totalCount);
		
		HashMap<String, Object> pageMap = PageUtil.pageMap(totalCount, pageNum, curPage);
		model.addAttribute("pageMap", pageMap);

		params.put("startNum", pageMap.get("startNum"));
		params.put("endNum", pageMap.get("endNum"));

		List<MajorVO> MajorVOList = MajorDao.selectListCourse(params);
		model.addAttribute("dataList", MajorVOList);

		model.addAttribute("curPage", curPage);
		model.addAttribute("flag", flag);
		return "major/course/list";
	}

	@RequestMapping("/iWORK/Major/Course")
	public String Record(HttpSession session, HttpServletRequest request, Model model,
			@RequestParam(defaultValue = "0") String seq,  @RequestParam(defaultValue = "15") String curPage,
			@RequestParam(defaultValue = "1") String pageNum, @RequestParam(defaultValue = "major") String flag) {

		Map<String, Object> params = new HashMap<String, Object>();

		String aboutSeq = session.getAttribute("MAJOR_ABOUT_SEQ").toString();
		if ("".equals(aboutSeq)) {
			throw new CustomGenericException("BIZ_ERR", "접속 권한이 없습니다.");
		}

		if (!"0".equals(seq)) {
			params.put("aboutSeq", aboutSeq);
			params.put("seq", seq);
			MajorVO MajorVO = MajorDao.selectCourse(params);
			if (!CommUtils.isEmpty(MajorVO)) {
				session.setAttribute("MAJOR_COURSE_SEQ", MajorVO.getSeq());
				model.addAttribute("data", MajorVO);
			}
		}

		model.addAttribute("pageNum", pageNum);
		model.addAttribute("curPage", curPage);
		model.addAttribute("flag", flag);
		return "major/course/edit";
	}

	@RequestMapping(value = "/Major/Course/saveProcData", method = RequestMethod.POST)
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
				int totalCount = MajorDao.selectCountCourse(params);
				MajorVO.setOrderNum(String.valueOf(totalCount + 1));
			} else {
				MajorVO.setModIp(ip);
			}

			MajorDao.saveProcCourse(MajorVO);
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

	@RequestMapping(headers = "Content-Type=application/json", value = "/Major/Course/deleteProcData.json", method = RequestMethod.POST)
	public @ResponseBody ResponseVO deleteProcData(@RequestBody HashMap<String, Object> hashMap) throws Exception {
		ResponseVO response = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		try {
			String[] seqArr = ((String) hashMap.get("seq")).split(",");
			for (String seq : seqArr) {
				MajorDao.deleteProcCourse(seq);
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
