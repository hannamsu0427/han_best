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
import org.springframework.ui.ModelMap;
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
import com.itsp.dao.VisualDao;
import com.itsp.vo.AttachFiles;
import com.itsp.vo.ResponseHeaderVO;
import com.itsp.vo.ResponseVO;
import com.itsp.vo.VisualVO;

@Controller
public class VisualController {

	@Autowired
	VisualDao VisualDao;

	@Autowired
	AttachFileDao AttachFileDao;

	private static final Logger logger = LoggerFactory.getLogger(VisualController.class);

	@RequestMapping("/iWORK/VisualList")
	public String VisualZoneList(Model model, HttpServletRequest request, HttpSession session,
			@RequestParam(defaultValue = "") String searchBy, @RequestParam(defaultValue = "") String searchValue,
			@RequestParam(defaultValue = "15") String curPage, @RequestParam(defaultValue = "1") String pageNum,
			@RequestParam(defaultValue = "visual") String flag) {

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

		int totalCount = VisualDao.totalCount(params);
		HashMap<String, Object> pageMap = PageUtil.pageMap(totalCount, pageNum, curPage);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("curPage", curPage);

		params.put("startNum", pageMap.get("startNum"));
		params.put("endNum", pageMap.get("endNum"));

		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pageMap", pageMap);

		List<VisualVO> dataList = VisualDao.selectDataList(params);
		model.addAttribute("dataList", dataList);

		model.addAttribute("flag", flag);
		return "visual/list";
	}

	@RequestMapping("/iWORK/Visual")
	public String Visual(HttpServletRequest request, Model model, @RequestParam(defaultValue = "15") String curPage,
			@RequestParam(defaultValue = "") String searchBy, @RequestParam(defaultValue = "") String searchValue,
			@RequestParam(defaultValue = "1") String pageNum, @RequestParam(defaultValue = "0") String seq,
			@RequestParam(defaultValue = "visual") String flag) {

		Map<String, Object> params = new HashMap<String, Object>();

		if (!"0".equals(seq)) {
			params.put("seq", seq);
			VisualVO data = VisualDao.selectData(params);
			model.addAttribute("data", data);
		}

		model.addAttribute("searchBy", searchBy);
		model.addAttribute("searchValue", searchValue);
		model.addAttribute("curPage", curPage);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("flag", flag);

		return "visual/edit";
	}

	@RequestMapping(value = "/iWORK/Visual/saveDataProc", method = RequestMethod.POST)
	public @ResponseBody ResponseVO saveDataProc(@ModelAttribute VisualVO vo, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ResponseVO resVO = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		try {
			boolean isInsert = ("".equals(CommUtils.checkNull(vo.getSeq())));
			if (isInsert) {
				String seq = VisualDao.seqNextVal();
				vo.setSeq(seq);

				Map<String, Object> params = new HashMap<String, Object>();
				int totalCount = VisualDao.totalCount(params);
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
				VisualDao.insertDataProc(vo);
			} else {
				VisualDao.updateDataProc(vo);
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

	@RequestMapping(headers = "Content-Type=application/json", value = "/iWORK/Visual/deleteDataProc.json", method = RequestMethod.POST)
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
				VisualDao.deleteDataProc(params);
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
	@RequestMapping(headers = "Content-Type=application/json", value = "/iWORK/Visual/orderNumProc.json", method = RequestMethod.POST)
	public @ResponseBody ResponseVO orderNumProc(@RequestBody HashMap<String, Object> Map) throws Exception {

		ResponseVO response = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		try {
			ArrayList<HashMap> dataList = (ArrayList<HashMap>) Map.get("data");
			VisualDao.orderNumProc(dataList);
			responseHeader.setCode(ResponseHeaderVO.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			responseHeader.setCode(ResponseHeaderVO.FAIL);
			responseHeader.setMessage(ResponseHeaderVO.FAIL_MESSAGE);
		}

		response.setHeader(responseHeader);
		return response;
	}
	
	@RequestMapping(headers = "Content-Type=application/json", value = "/Visual/selectListData.json", method = RequestMethod.POST)
	public @ResponseBody ResponseVO SelectListData(@RequestBody HashMap<String, Object> hashMap, HttpSession session,
			ModelMap model) throws Exception {

		ResponseVO response = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		ArrayList<VisualVO> dataList = new ArrayList<VisualVO>();
		try {
			hashMap.put("useYn", "Y");
			dataList = (ArrayList<VisualVO>) VisualDao.selectDataList(hashMap);
			responseHeader.setCode(ResponseHeaderVO.SUCCESS);
		} catch (Exception e) {
			dataList = new ArrayList<VisualVO>();
			e.printStackTrace();
		}

		response.setHeader(responseHeader);
		response.setBody(dataList);
		return response;
	}

}
