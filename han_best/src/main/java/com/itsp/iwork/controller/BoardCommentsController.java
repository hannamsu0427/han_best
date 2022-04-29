package com.itsp.iwork.controller;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itsp.common.CommUtils;
import com.itsp.common.Config;
import com.itsp.dao.BoardDao;
import com.itsp.vo.BoardVO;
import com.itsp.vo.MemberVO;
import com.itsp.vo.ResponseHeaderVO;
import com.itsp.vo.ResponseVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class BoardCommentsController {

	@Autowired
	BoardDao BoardDao;

	private static final Logger logger = LoggerFactory.getLogger(BoardCommentsController.class);

	@RequestMapping(value = "/Board/Comments/saveProcData", method = RequestMethod.POST)
	public @ResponseBody ResponseVO saveProcData(@ModelAttribute BoardVO BoardVO, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ResponseVO resVO = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		try {
			String swearWord = session.getAttribute("SWEAR_WORD").toString();
			String contents = CommUtils.filterTextCheck(swearWord, BoardVO.getContents());
			if (contents.contains("BAD_TEXT")) {
				responseHeader.setCode(ResponseHeaderVO.FAIL);
				responseHeader.setMessage("사용할 수 없는 단어가 포함되어있습니다.");
			}

			String userId = "";
			String userNm = "";
			MemberVO memberVO = (MemberVO) session.getAttribute(Config.SESSION_MEMBER);
			if (memberVO != null) {
				userId = memberVO.getUser_id();
				userNm = memberVO.getUser_nm();
			}

			String ip = Inet4Address.getLocalHost().getHostAddress();
			boolean isInsert = ("".equals(CommUtils.checkNull(BoardVO.getSeq())));
			if (isInsert) {
				String seq = BoardDao.seqNextVal();
				BoardVO.setSeq(seq);
				BoardVO.setRegId(userId);
				BoardVO.setRegName(userNm);
				BoardVO.setRegIp(ip);

				BoardVO.setParentSeq("0");
				BoardVO.setDepth("0");
			} else {
				BoardVO.setModId(userId);
				BoardVO.setModName(userNm);
				BoardVO.setModIp(ip);
			}
			contents = CommUtils.filterText(swearWord, BoardVO.getContents());
			BoardVO.setContents(contents);

			BoardDao.saveProcComments(BoardVO);
			responseHeader.setCode(ResponseHeaderVO.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			responseHeader.setCode(ResponseHeaderVO.FAIL);
			responseHeader.setMessage(ResponseHeaderVO.FAIL_MESSAGE);
		}
		resVO.setHeader(responseHeader);
		return resVO;
	}

	@RequestMapping(headers = "Content-Type=application/json", value = "/Board/Comments/DeleteProcData.json", method = RequestMethod.POST)
	public @ResponseBody ResponseVO DeleteProcData(@RequestBody HashMap<String, Object> hashMap, Model model)
			throws Exception {

		ResponseVO response = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		try {
			BoardDao.deleteProcComments((String) hashMap.get("seq"));
			responseHeader.setCode(ResponseHeaderVO.SUCCESS);
		} catch (Exception e) {
			responseHeader.setCode(ResponseHeaderVO.FAIL);
			responseHeader.setMessage(ResponseHeaderVO.FAIL_MESSAGE);
		}

		response.setHeader(responseHeader);
		return response;
	}

	@RequestMapping(headers = "Content-Type=application/json", value = "/Board/Comments/selectListData.json", method = RequestMethod.POST)
	public @ResponseBody ResponseVO SelectListData(@RequestBody HashMap<String, Object> hashMap, HttpSession session,
			ModelMap model) throws Exception {

		ResponseVO response = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		ArrayList<BoardVO> commentList = new ArrayList<BoardVO>();
		try {
			String recordSeq = "";
			if (session.getAttribute("BOARD_RECORD_SEQ") != null
					&& !"".equals(session.getAttribute("BOARD_RECORD_SEQ").toString())) {
				recordSeq = session.getAttribute("BOARD_RECORD_SEQ").toString();
			} else {
				responseHeader.setCode(ResponseHeaderVO.FAIL);
			}
			commentList = (ArrayList<BoardVO>) BoardDao.selectListComments(recordSeq);
			responseHeader.setCode(ResponseHeaderVO.SUCCESS);
		} catch (Exception e) {
			commentList = new ArrayList<BoardVO>();
			e.printStackTrace();
		}

		response.setHeader(responseHeader);
		response.setBody(commentList);
		return response;
	}

}
