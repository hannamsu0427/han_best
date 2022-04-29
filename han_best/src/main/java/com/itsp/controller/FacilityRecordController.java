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
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.itsp.common.CommUtils;
import com.itsp.common.CustomGenericException;
import com.itsp.common.PageUtil;
import com.itsp.dao.AttachFileDao;
import com.itsp.dao.EtcDao;
import com.itsp.dao.FacilityDao;
import com.itsp.vo.AttachFiles;
import com.itsp.vo.FacilityVO;
import com.itsp.vo.ResponseHeaderVO;
import com.itsp.vo.ResponseVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class FacilityRecordController {

	@Autowired
	FacilityDao FacilityDao;

	@Autowired
	AttachFileDao AttachFileDao;
	
	@Autowired
	EtcDao EtcDao;

	private static final Logger logger = LoggerFactory.getLogger(FacilityRecordController.class);

	/***
	 * 리스트
	 ***/
	@RequestMapping("/iWORK/Facility/RecordList")
	public String RecordList(HttpServletRequest request, Model model, HttpSession session,
			@RequestParam(defaultValue = "all") String searchBy, @RequestParam(defaultValue = "") String searchValue,
			@RequestParam(defaultValue = "facility") String flag) {

		Map<String, Object> params = new HashMap<String, Object>();

		String ssConfigSeq = session.getAttribute("FACILITY_CONFIG_SEQ").toString();
		params.put("seq", ssConfigSeq);
		FacilityVO configVO = FacilityDao.selectConfig(params);
		if (CommUtils.isEmpty(configVO)) {
			throw new CustomGenericException("BIZ_ERR", "접속 권한이 없습니다.");
		} else {
			model.addAttribute("dataConfig", configVO);
		}
		params.clear();

		params.put("configSeq", ssConfigSeq);
		params.put("searchBy", searchBy);
		params.put("searchValue", searchValue);

		int totalCount = FacilityDao.selectCountRecord(params);
		model.addAttribute("totalCount", totalCount);

		List<FacilityVO> FacilityVOList = FacilityDao.selectRecordList(params);
		model.addAttribute("dataList", FacilityVOList);

		model.addAttribute("searchValue", searchValue);
		model.addAttribute("searchBy", searchBy);

		model.addAttribute("flag", flag);
		return "facility/record/list";
	}

	@RequestMapping("/iWORK/Facility/Record")
	public String Record(HttpSession session, HttpServletRequest request, Model model,
			@RequestParam(defaultValue = "0") String seq, @RequestParam(defaultValue = "") String searchBy,
			@RequestParam(defaultValue = "") String searchValue, @RequestParam(defaultValue = "facility") String flag) {

		Map<String, Object> params = new HashMap<String, Object>();

		String ssConfigSeq = session.getAttribute("FACILITY_CONFIG_SEQ").toString();
		params.put("configSeq", ssConfigSeq);
		if (!"0".equals(seq)) {
			params.put("seq", seq);
			FacilityVO FacilityVO = FacilityDao.selectRecord(params);
			if (!CommUtils.isEmpty(FacilityVO)) {
				session.setAttribute("FACILITY_RECORD_SEQ", FacilityVO.getSeq());
				model.addAttribute("data", FacilityVO);
			}
		}

		model.addAttribute("searchValue", searchValue);
		model.addAttribute("searchBy", searchBy);

		model.addAttribute("flag", flag);
		return "facility/record/edit";
	}

	@RequestMapping(value = "/Facility/Record/saveProcData", method = RequestMethod.POST)
	public @ResponseBody ResponseVO saveProcData(@ModelAttribute FacilityVO FacilityVO, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ResponseVO resVO = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		try {
			String ip = Inet4Address.getLocalHost().getHostAddress();
			boolean isInsert = ("".equals(CommUtils.checkNull(FacilityVO.getSeq())));
			if (isInsert) {
				String seq = FacilityDao.seqNextVal();
				FacilityVO.setSeq(seq);
				FacilityVO.setRegIp(ip);
				
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("configSeq", FacilityVO.getConfigSeq());
				int orderNum = FacilityDao.selectCountRecord(params);
				orderNum = orderNum + 1;
				FacilityVO.setOrderNum(String.valueOf(orderNum));
			} else {
				FacilityVO.setModIp(ip);

				MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
				List<AttachFiles> fileList = AttachFileDao.selectFileList(FacilityVO.getSeq());
				if (fileList != null && fileList.size() > 0) {
					for (int i = 0; i < fileList.size(); i++) {
						AttachFiles file = fileList.get(i);
						file.setSeq(file.getSeq());
						String etc = "";
						etc = multipart.getParameter("fileEtc_" + file.getSeq());
						file.setExplanation(etc);
						AttachFileDao.updateFileProc(file);
					}
				}
			}
			
			List<AttachFiles> fileList = CommUtils.multiFileUpload(request, response, FacilityVO.getSeq());
			if (fileList == null) {
				throw new Exception("허용되지 않은 파일형식이거나 파일사이즈가 너무 큽니다.");
			}
			logger.info("첨부파일 개수 {}", fileList.size());
			if (fileList != null && fileList.size() > 0) {
				for (int i = 0; i < fileList.size(); i++) {
					AttachFiles attachFile = fileList.get(i);
					attachFile.setParentSeq(FacilityVO.getSeq());
					AttachFileDao.insertFileProc(attachFile);
				}
			}
			
			EtcDao.deleteProcData(FacilityVO.getSeq());
			String[] etcArr = FacilityVO.getEtc1().split("\\|\\|");
			String[] etcArr2 = FacilityVO.getEtc2().split("\\|\\|");
			for(int i=0; i<etcArr.length; i++) {
				Map<String, Object> params = new HashMap<String, Object>();
				String etc1 = CommUtils.checkNull(etcArr[i]);
				if(" ".equals(etc1)) {
					etc1 = etc1.replaceAll(" ", "");
				}
				String etc2 = CommUtils.checkNull(etcArr2[i]);
				if(" ".equals(etc2)) {
					etc2 = etc1.replaceAll(" ", "");
				}
				params.put("recordSeq", FacilityVO.getSeq());
				params.put("etc1", etc1);
				params.put("etc2", etc2);
				EtcDao.insertProcData(params);
			}

			FacilityDao.saveProcRecord(FacilityVO);
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

	@RequestMapping(headers = "Content-Type=application/json", value = "/Facility/Record/deleteProcData.json", method = RequestMethod.POST)
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
				FacilityDao.deleteProcRecord(seq);
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

	@RequestMapping(headers = "Content-Type=application/json", value = "/Facility/Record/useProcData.json", method = RequestMethod.POST)
	public @ResponseBody ResponseVO useProcData(@RequestBody HashMap<String, Object> hashMap) throws Exception {
		ResponseVO response = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		try {
			String[] seqArr = ((String) hashMap.get("seq")).split(",");
			String useYn = (String) hashMap.get("useYn");
			for (String seq : seqArr) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("seq", seq);
				params.put("useYn", useYn);
				FacilityDao.useProcRecord(params);
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
	@RequestMapping(headers = "Content-Type=application/json", value = "/iWORK/Facility/Record/orderNumProc.json", method = RequestMethod.POST)
	public @ResponseBody ResponseVO orderNumProc(@RequestBody HashMap<String, Object> Map) throws Exception {

		ResponseVO response = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		try {
			ArrayList<HashMap> dataList = (ArrayList<HashMap>) Map.get("data");
			FacilityDao.orderNumProcRecord(dataList);
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
