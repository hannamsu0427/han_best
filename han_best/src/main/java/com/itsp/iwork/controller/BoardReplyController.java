package com.itsp.iwork.controller;

import java.net.Inet4Address;
import java.util.HashMap;

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
public class BoardReplyController {

	@Autowired
	BoardDao BoardDao;

	private static final Logger logger = LoggerFactory.getLogger(BoardReplyController.class);

	@RequestMapping(value = "/Board/Reply/saveProcData", method = RequestMethod.POST)
	public @ResponseBody ResponseVO saveProcData(@ModelAttribute BoardVO BoardVO, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ResponseVO resVO = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		try {
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
			} else {
				BoardVO.setModId(userId);
				BoardVO.setModName(userNm);
				BoardVO.setModIp(ip);
			}

			String swearWord = session.getAttribute("SWEAR_WORD").toString();
			String contents = CommUtils.filterText(swearWord, BoardVO.getContents());
			BoardVO.setContents(contents);

			BoardDao.saveProcReply(BoardVO);
			responseHeader.setCode(ResponseHeaderVO.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			responseHeader.setCode(ResponseHeaderVO.FAIL);
			responseHeader.setMessage(ResponseHeaderVO.FAIL_MESSAGE);
		}
		resVO.setHeader(responseHeader);
		return resVO;
	}

	@RequestMapping(headers = "Content-Type=application/json", value = "/Board/Reply/deleteProcData.json", method = RequestMethod.POST)
	public @ResponseBody ResponseVO deleteProcData(@RequestBody HashMap<String, Object> hashMap, Model model)
			throws Exception {

		ResponseVO response = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		try {
			BoardDao.deleteProcReply((String) hashMap.get("seq"));
			responseHeader.setCode(ResponseHeaderVO.SUCCESS);
		} catch (Exception e) {
			responseHeader.setCode(ResponseHeaderVO.FAIL);
			responseHeader.setMessage(ResponseHeaderVO.FAIL_MESSAGE);
		}

		response.setHeader(responseHeader);
		return response;
	}
}
