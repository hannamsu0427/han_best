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
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.itsp.common.CommUtils;
import com.itsp.common.Config;
import com.itsp.common.CustomGenericException;
import com.itsp.common.PageUtil;
import com.itsp.dao.AttachFileDao;
import com.itsp.dao.BoardDao;
import com.itsp.vo.AttachFiles;
import com.itsp.vo.BoardVO;
import com.itsp.vo.MemberVO;
import com.itsp.vo.ResponseHeaderVO;
import com.itsp.vo.ResponseVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class BoardRecordController {

	@Autowired
	BoardDao BoardDao;

	@Autowired
	AttachFileDao AttachFileDao;

	private static final Logger logger = LoggerFactory.getLogger(BoardRecordController.class);

	/***
	 * 리스트
	 ***/
	@RequestMapping("/iWORK/Board/RecordList")
	public String RecordList(HttpServletRequest request, Model model, HttpSession session,
			@RequestParam(defaultValue = "N") String delYn,
			@RequestParam(defaultValue = "all") String searchBy, @RequestParam(defaultValue = "") String searchValue,
			@RequestParam(defaultValue = "15") String curPage, @RequestParam(defaultValue = "1") String pageNum,
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
		model.addAttribute("pageNum", pageNum);

		model.addAttribute("flag", flag);
		return "board/record/list";
	}

	@RequestMapping("/iWORK/Board/Record")
	public String Record(HttpSession session, HttpServletRequest request, Model model,
			@RequestParam(defaultValue = "0") String seq, @RequestParam(defaultValue = "") String searchBy,
			@RequestParam(defaultValue = "") String searchValue, @RequestParam(defaultValue = "15") String curPage,
			@RequestParam(defaultValue = "1") String pageNum, @RequestParam(defaultValue = "board") String flag) {

		Map<String, Object> params = new HashMap<String, Object>();

		if (!"0".equals(seq)) {
			params.put("seq", seq);
			BoardVO BoardVO = BoardDao.selectBoardRecord(params);
			if (!CommUtils.isEmpty(BoardVO)) {
				session.setAttribute("BOARD_RECORD_SEQ", BoardVO.getSeq());
				model.addAttribute("data", BoardVO);
			}
		}
		
		model.addAttribute("searchValue", searchValue);
		model.addAttribute("searchBy", searchBy);
		model.addAttribute("curPage", curPage);
		model.addAttribute("pageNum", pageNum);

		model.addAttribute("flag", flag);
		return "board/record/edit";
	}

	@RequestMapping(value = "/Board/Record/saveProcData", method = RequestMethod.POST)
	public @ResponseBody ResponseVO saveProcData(@ModelAttribute BoardVO BoardVO, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ResponseVO resVO = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		try {
			String swearWord = session.getAttribute("SWEAR_WORD").toString();
			String title = CommUtils.filterTextCheck(swearWord, BoardVO.getTitle());
			String contents = CommUtils.filterTextCheck(swearWord, CommUtils.checkNull(BoardVO.getContents()));
			if(title.contains("BAD_TEXT") || contents.contains("BAD_TEXT")) {
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

				if ("".equals(CommUtils.checkNull(BoardVO.getGroupSeq()))) {
					BoardVO.setGroupSeq(seq);
					BoardVO.setParentSeq("0");
					BoardVO.setDepth("0");
				}
				BoardVO.setRegId(userId);
				BoardVO.setRegName(userNm);
				BoardVO.setRegIp(ip);
			} else {
				BoardVO.setModId(userId);
				BoardVO.setModName(userNm);
				BoardVO.setModIp(ip);
				
				MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
				List<AttachFiles> fileList = AttachFileDao.selectFileList(BoardVO.getSeq());
				if (fileList != null && fileList.size() > 0) {
					for (int i = 0; i < fileList.size(); i++) {
						AttachFiles file = fileList.get(i);
						file.setSeq(file.getSeq());
						String etc = "";
						etc = multipart.getParameter("fileEtc_" + file.getSeq());
						file.setEtc(etc);
						AttachFileDao.updateFileProc(file);
					}
				}
			}

			List<AttachFiles> fileList = CommUtils.multiFileUpload(request, response, BoardVO.getSeq());
			if (fileList == null) {
				throw new Exception("허용되지 않은 파일형식이거나 파일사이즈가 너무 큽니다.");
			}
			logger.info("첨부파일 개수 {}", fileList.size());
			if (fileList != null && fileList.size() > 0) {
				for (int i = 0; i < fileList.size(); i++) {
					AttachFiles attachFile = fileList.get(i);
					attachFile.setParentSeq(BoardVO.getSeq());
					AttachFileDao.insertFileProc(attachFile);
				}
			}

			BoardDao.saveProcBoardRecord(BoardVO);
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

	@RequestMapping(headers = "Content-Type=application/json", value = "/Board/Record/deleteProcData.json", method = RequestMethod.POST)
	public @ResponseBody ResponseVO deleteProcData(@RequestBody HashMap<String, Object> hashMap) throws Exception {
		ResponseVO response = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		try {
			String[] seqArr = ((String) hashMap.get("seq")).split(",");
			for (String seq : seqArr) {
				String parentSeq = seq;
				List<AttachFiles> fileList = AttachFileDao.selectFileList(parentSeq);
				for (int i = 0; i < fileList.size(); i++) {
					String fileSeq = fileList.get(i).getSeq().toString();
					AttachFileDao.deleteFileProc(fileSeq);
				}
				BoardDao.deleteProcBoardRecord(seq);
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
	
	@RequestMapping(headers = "Content-Type=application/json", value = "/Board/Record/useProcData.json", method = RequestMethod.POST)
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
				BoardDao.useProcBoardRecord(params);
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
