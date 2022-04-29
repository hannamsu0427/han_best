package com.itsp.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.itsp.common.CommUtils;
import com.itsp.common.CustomGenericException;
import com.itsp.common.PageUtil;
import com.itsp.dao.AttachFileDao;
import com.itsp.dao.BoardDao;
import com.itsp.vo.AttachFiles;
import com.itsp.vo.BoardVO;
import com.itsp.vo.ResponseHeaderVO;
import com.itsp.vo.ResponseVO;

@Controller
public class BoardTrashController {

	@Autowired
	BoardDao BoardDao;
	@Autowired
	AttachFileDao AttachFileDao;
	
	@RequestMapping("/iWORK/Board/Trash")
	public String Trash(HttpServletRequest request, Model model, HttpSession session,
			@RequestParam(defaultValue = "all") String searchBy, @RequestParam(defaultValue = "") String searchValue,
			@RequestParam(defaultValue = "15") String curPage, @RequestParam(defaultValue = "1") String pageNum,
			@RequestParam(defaultValue = "Y") String delYn,
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
		params.clear();
		
		params.put("delYn", delYn);
		params.put("configSeq", BoardConfigVO.getSeq());
		params.put("searchBy", searchBy);
		params.put("searchValue", searchValue);

		int totalCount = BoardDao.selectCountBoardRecord(params);

		HashMap<String, Object> pageMap = PageUtil.pageMap(totalCount, pageNum, curPage);

		params.put("startNum", pageMap.get("startNum"));
		params.put("endNum", pageMap.get("endNum"));

		List<BoardVO> BoardVOList = BoardDao.selectBoardRecordList(params);
		model.addAttribute("dataList", BoardVOList);

		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pageMap", pageMap);

		model.addAttribute("searchValue", searchValue);
		model.addAttribute("searchBy", searchBy);
		model.addAttribute("curPage", curPage);

		model.addAttribute("flag", flag);
		return "board/trash/list";
	}



}