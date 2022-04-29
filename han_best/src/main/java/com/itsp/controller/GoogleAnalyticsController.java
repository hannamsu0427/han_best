package com.itsp.controller;

import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itsp.common.CommUtils;
import com.itsp.dao.GoogleAnalyticsDao;
import com.itsp.vo.GoogleAnalyticsVO;
import com.itsp.vo.ResponseHeaderVO;
import com.itsp.vo.ResponseVO;

@Controller
public class GoogleAnalyticsController {

	@Autowired
	GoogleAnalyticsDao GoogleAnalyticsDao;

	private static final Logger logger = LoggerFactory.getLogger(GoogleAnalyticsController.class);

	@RequestMapping("/iWORK/GoogleAnalytics")
	public String GoogleAnalytics(HttpServletRequest request, Model model, @RequestParam(defaultValue = "0") String seq,
			@RequestParam(defaultValue = "googleanalytics") String flag) {

		Map<String, Object> params = new HashMap<String, Object>();
		GoogleAnalyticsVO data = GoogleAnalyticsDao.selectData(params);
		model.addAttribute("data", data);

		model.addAttribute("flag", flag);

		return "googleanalytics/edit";
	}

	@RequestMapping(value = "/iWORK/GoogleAnalytics/saveDataProc", method = RequestMethod.POST)
	public @ResponseBody ResponseVO saveDataProc(@ModelAttribute GoogleAnalyticsVO vo, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ResponseVO resVO = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		try {
			boolean isInsert = ("".equals(CommUtils.checkNull(vo.getSeq())));
			if (isInsert) {
				GoogleAnalyticsDao.insertDataProc(vo);
			} else {
				GoogleAnalyticsDao.updateDataProc(vo);
			}
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
