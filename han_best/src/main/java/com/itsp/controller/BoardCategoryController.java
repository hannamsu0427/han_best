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
import com.itsp.common.CustomGenericException;
import com.itsp.dao.BoardDao;
import com.itsp.vo.BoardVO;
import com.itsp.vo.ResponseHeaderVO;
import com.itsp.vo.ResponseVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class BoardCategoryController {

	@Autowired
	BoardDao BoardDao;

	private static final Logger logger = LoggerFactory.getLogger(BoardCategoryController.class);

	@RequestMapping(headers = "Content-Type=application/json", value = "/iWORK/Board/Category/JsonList.json", method = RequestMethod.POST)
	public @ResponseBody ResponseVO JsonList(@RequestBody HashMap<String, Object> hashMap, HttpSession session)
			throws Exception {

		ResponseVO response = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();
		List<BoardVO> dataList = new ArrayList<BoardVO>();

		try {
			hashMap.put("useYn", "Y");
			dataList = BoardDao.selectBoardCategoryList(hashMap);
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
	@RequestMapping("/iWORK/Board/CategoryList")
	public String CategoryList(HttpServletRequest request, Model model, HttpSession session,
			@RequestParam String configSeq, @RequestParam(defaultValue = "board") String flag) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("configSeq", configSeq);
		
		String ssBoardConfigSeq = session.getAttribute("BOARD_CONFIG_SEQ").toString();
		if(!configSeq.equals(ssBoardConfigSeq)) {
			throw new CustomGenericException("BIZ_ERR", "접속 권한이 없습니다.");
		}
		
		params.put("seq", ssBoardConfigSeq);
		BoardVO BoardConfigVO = BoardDao.selectBoardConfig(params);
		if(CommUtils.isEmpty(BoardConfigVO)) {
			throw new CustomGenericException("BIZ_ERR", "접속 권한이 없습니다.");
		}else {
			model.addAttribute("dataConfig", BoardConfigVO);
		}
		
		List<BoardVO> BoardVOList = BoardDao.selectBoardCategoryList(params);
		model.addAttribute("dataList", BoardVOList);

		model.addAttribute("flag", flag);
		return "board/category/list";
	}

	@RequestMapping("/iWORK/Board/Category")
	public String Category(HttpSession session, HttpServletRequest request, Model model,
			@RequestParam(defaultValue = "0") String categorySeq,
			@RequestParam(defaultValue = "board") String flag) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		String ssBoardConfigSeq = session.getAttribute("BOARD_CONFIG_SEQ").toString();
		
		params.put("seq", ssBoardConfigSeq);
		BoardVO BoardConfigVO = BoardDao.selectBoardConfig(params);
		if(CommUtils.isEmpty(BoardConfigVO)) {
			throw new CustomGenericException("BIZ_ERR", "접속 권한이 없습니다.");
		}else {
			model.addAttribute("dataConfig", BoardConfigVO);
		}
		
		if (!"0".equals(categorySeq)) {
			params.put("configSeq", ssBoardConfigSeq);
			params.put("categorySeq", categorySeq);
			BoardVO BoardVO = BoardDao.selectBoardCategory(params);
			if (!CommUtils.isEmpty(BoardVO)) {
				session.setAttribute("BOARD_CATEGORY_SEQ", BoardVO.getCategorySeq());
				model.addAttribute("data", BoardVO);
			}
		}
		model.addAttribute("flag", flag);
		return "board/category/edit";
	}

	@RequestMapping(value = "/iWORK/Board/Category/saveProcData", method = RequestMethod.POST)
	public @ResponseBody ResponseVO saveProcData(@ModelAttribute BoardVO BoardVO, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ResponseVO resVO = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		boolean isInsert = ("".equals(CommUtils.checkNull(BoardVO.getCategorySeq())));
		if (isInsert) {
			String seq = BoardDao.seqNextVal();
			BoardVO.setCategorySeq(seq);
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("configSeq", BoardVO.getConfigSeq());
			int orderNum = BoardDao.selectCountBoardCategory(params);
			orderNum = orderNum + 1;
			BoardVO.setOrderNum(String.valueOf(orderNum));
		}

		try {
			BoardDao.saveProcBoardCategory(BoardVO);
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

	@RequestMapping(headers = "Content-Type=application/json", value = "/iWORK/Board/Category/deleteProcData.json", method = RequestMethod.POST)
	public @ResponseBody ResponseVO deleteProcData(@RequestBody HashMap<String, Object> hashMap) throws Exception {
		ResponseVO response = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		try {
			String[] seqArr = ((String) hashMap.get("seq")).split(",");
			for (String categorySeq : seqArr) {
				BoardDao.deleteProcBoardCategory(categorySeq);
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
	@RequestMapping(headers = "Content-Type=application/json", value = "/iWORK/Board/Category/orderNumProc.json", method = RequestMethod.POST)
	public @ResponseBody ResponseVO orderNumProc(@RequestBody HashMap<String, Object> Map) throws Exception {

		ResponseVO response = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		try {
			ArrayList<HashMap> dataList = (ArrayList<HashMap>) Map.get("data");
			BoardDao.orderNumProcBoardCategory(dataList);
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
