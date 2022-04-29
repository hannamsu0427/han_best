package com.itsp.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.itsp.common.PageUtil;
import com.itsp.dao.AttachFileDao;
import com.itsp.dao.PopUpDao;
import com.itsp.vo.AttachFiles;
import com.itsp.vo.PopUpVO;
import com.itsp.vo.ResponseHeaderVO;
import com.itsp.vo.ResponseVO;

@Controller
public class PopUpController {

	@Autowired
	PopUpDao PopUpDao;
	@Autowired
	AttachFileDao AttachFileDao;

	private static final Logger logger = LoggerFactory.getLogger(PopUpController.class);

	@RequestMapping("/iWORK/PopUpList")
	public String PopUpZoneList(Model model, HttpServletRequest request, HttpSession session,
			@RequestParam(defaultValue = "") String searchBy, @RequestParam(defaultValue = "") String searchValue,
			@RequestParam(defaultValue = "15") String curPage, @RequestParam(defaultValue = "1") String pageNum,
			@RequestParam(defaultValue = "popUp") String flag) {

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -3); // 현재 날짜에서 3일전의 날짜 가져오기
		Date currentTime = cal.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String timestr = formatter.format(currentTime);
		int currentDate = Integer.parseInt(timestr);
		model.addAttribute("currentDate", currentDate);

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("searchBy", searchBy);
		params.put("searchValue", searchValue);
		model.addAttribute("searchBy", searchBy);
		model.addAttribute("searchValue", searchValue);

		int totalCount = PopUpDao.totalCount(params);
		HashMap<String, Object> pageMap = PageUtil.pageMap(totalCount, pageNum, curPage);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("curPage", curPage);

		params.put("startNum", pageMap.get("startNum"));
		params.put("endNum", pageMap.get("endNum"));

		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pageMap", pageMap);

		List<PopUpVO> dataList = PopUpDao.selectDataList(params);
		model.addAttribute("dataList", dataList);
		
		model.addAttribute("flag", flag);
		return "popUp/list";
	}

	@RequestMapping("/iWORK/PopUp")
	public String PopUp(HttpServletRequest request, Model model, @RequestParam(defaultValue = "15") String curPage,
			@RequestParam(defaultValue = "") String searchBy, @RequestParam(defaultValue = "") String searchValue,
			@RequestParam(defaultValue = "1") String pageNum, @RequestParam(defaultValue = "0") String seq,
			@RequestParam(defaultValue = "popUp") String flag) {

		Map<String, Object> params = new HashMap<String, Object>();

		if (!"0".equals(seq)) {
			params.put("seq", seq);
			PopUpVO data = PopUpDao.selectData(params);
			model.addAttribute("data", data);
		}

		model.addAttribute("searchBy", searchBy);
		model.addAttribute("searchValue", searchValue);
		model.addAttribute("curPage", curPage);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("flag", flag);

		return "popUp/edit";
	}

	@RequestMapping(value = "/iWORK/PopUp/saveDataProc", method = RequestMethod.POST)
	public @ResponseBody ResponseVO saveDataProc(@ModelAttribute PopUpVO vo, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ResponseVO resVO = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		if ("A".equals(vo.getSetting())) {
			vo.setStartDate("");
			vo.setEndDate("");
		}

		try {
			boolean isInsert = ("".equals(CommUtils.checkNull(vo.getSeq())));
			if (isInsert) {
				String seq = PopUpDao.seqNextVal();
				vo.setSeq(seq);

				Map<String, Object> params = new HashMap<String, Object>();
				int totalCount = PopUpDao.totalCount(params);
				vo.setOrderNum(String.valueOf(totalCount + 1));
			} else {
				MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
				List<AttachFiles> fileList = AttachFileDao.selectFileList(vo.getSeq());
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

			List<AttachFiles> fileList = CommUtils.multiFileUpload(request, response, vo.getSeq());
			if (fileList == null) {
				throw new Exception("허용되지 않은 파일형식이거나 파일사이즈가 너무 큽니다.");
			}
			logger.info("첨부파일 개수 {}", fileList.size());
			if (fileList != null && fileList.size() > 0) {
				for (int i = 0; i < fileList.size(); i++) {
					AttachFiles attachFile = fileList.get(i);
					attachFile.setParentSeq(vo.getSeq());
					AttachFileDao.insertFileProc(attachFile);
				}
			}

			if (isInsert) {
				PopUpDao.insertDataProc(vo);
			} else {
				PopUpDao.updateDataProc(vo);
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

	@RequestMapping(headers = "Content-Type=application/json", value = "/iWORK/PopUp/deleteDataProc.json", method = RequestMethod.POST)
	public @ResponseBody ResponseVO deleteDataProc(@RequestBody HashMap<String, Object> map) throws Exception {
		ResponseVO response = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();
		Map<String, Object> params = new HashMap<String, Object>();

		try {
			logger.info(map.toString());
			String[] seqArr = ((String) map.get("seq")).split(",");
			for (String seq : seqArr) {
				params.put("seq", seq);
				List<AttachFiles> fileList = AttachFileDao.selectFileList(seq);
				for (int i = 0; i < fileList.size(); i++) {
					String fileSeq = String.valueOf(fileList.get(i).getSeq());
					AttachFiles attachFile = AttachFileDao.selectFile(fileSeq);
					if (!CommUtils.isEmpty(attachFile)) {
						CommUtils.fileDelete(attachFile);
						AttachFileDao.deleteFileProc(fileSeq);
					}
				}
				PopUpDao.deleteDataProc(params);
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
	@RequestMapping(headers = "Content-Type=application/json", value = "/iWORK/PopUp/orderNumProc.json", method = RequestMethod.POST)
	public @ResponseBody ResponseVO orderNumProc(@RequestBody HashMap<String, Object> Map) throws Exception {

		ResponseVO response = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		try {
			ArrayList<HashMap> dataList = (ArrayList<HashMap>) Map.get("data");
			PopUpDao.orderNumProc(dataList);
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
