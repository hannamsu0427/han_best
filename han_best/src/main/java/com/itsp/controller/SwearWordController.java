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
import com.itsp.dao.SwearWordDao;
import com.itsp.vo.ResponseHeaderVO;
import com.itsp.vo.ResponseVO;
import com.itsp.vo.SwearWordVO;

@Controller
public class SwearWordController {

	@Autowired
	SwearWordDao SwearWordDao;

	private static final Logger logger = LoggerFactory.getLogger(SwearWordController.class);

	@RequestMapping("/iWORK/SwearWord")
	public String SwearWord(HttpServletRequest request, Model model, @RequestParam(defaultValue = "0") String seq,
			@RequestParam(defaultValue = "swearword") String flag) {

		Map<String, Object> params = new HashMap<String, Object>();
		SwearWordVO data = SwearWordDao.selectData(params);
		model.addAttribute("data", data);

		model.addAttribute("flag", flag);
		return "swearword/edit";
	}

	@RequestMapping(value = "/iWORK/SwearWord/saveDataProc", method = RequestMethod.POST)
	public @ResponseBody ResponseVO saveDataProc(@ModelAttribute SwearWordVO vo, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ResponseVO resVO = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		try {
			boolean isInsert = ("".equals(CommUtils.checkNull(vo.getSeq())));
			if (isInsert) {
				SwearWordDao.insertDataProc(vo);
			} else {
				SwearWordDao.updateDataProc(vo);
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
