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
import com.itsp.common.CustomGenericException;
import com.itsp.dao.AttachFileDao;
import com.itsp.dao.MajorDao;
import com.itsp.vo.AttachFiles;
import com.itsp.vo.MajorVO;
import com.itsp.vo.ResponseHeaderVO;
import com.itsp.vo.ResponseVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MajorApplicationController {

	@Autowired
	MajorDao MajorDao;
	
	@Autowired
	AttachFileDao AttachFileDao;

	private static final Logger logger = LoggerFactory.getLogger(MajorApplicationController.class);

	@RequestMapping("/iWORK/Major/Application")
	public String Record(HttpSession session, HttpServletRequest request, Model model,
			@RequestParam(defaultValue = "major") String flag) {

		Map<String, Object> params = new HashMap<String, Object>();

		String aboutSeq = request.getParameter("aboutSeq");
		session.setAttribute("MAJOR_ABOUT_SEQ", aboutSeq);
		
		params.put("seq", aboutSeq);
		MajorVO MajorVO = MajorDao.selectAbout(params);
		if (!CommUtils.isEmpty(MajorVO)) {
			model.addAttribute("dataAbout", MajorVO);
		}
		params.clear();

		params.put("aboutSeq", aboutSeq);
		MajorVO ApplicationVO = MajorDao.selectApplication(params);
		if (!CommUtils.isEmpty(ApplicationVO)) {
			session.setAttribute("MAJOR_APPLICATION_SEQ", ApplicationVO.getSeq());
			model.addAttribute("data", ApplicationVO);
		}
		
		model.addAttribute("flag", flag);
		return "major/application/edit";
	}

	@RequestMapping(value = "/Major/Application/saveProcData", method = RequestMethod.POST)
	public @ResponseBody ResponseVO saveProcData(@ModelAttribute MajorVO MajorVO, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ResponseVO resVO = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		try {
			String ip = Inet4Address.getLocalHost().getHostAddress();
			boolean isInsert = ("".equals(CommUtils.checkNull(MajorVO.getSeq())));
			if (isInsert) {
				String seq = MajorDao.seqNextVal();
				MajorVO.setSeq(seq);
				MajorVO.setRegIp(ip);
			} else {
				MajorVO.setModIp(ip);
				
				MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
				List<AttachFiles> fileList = AttachFileDao.selectFileList(MajorVO.getSeq());
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
			
			List<AttachFiles> fileList = CommUtils.multiFileUpload(request, response, MajorVO.getSeq());
			if (fileList == null) {
				throw new Exception("허용되지 않은 파일형식이거나 파일사이즈가 너무 큽니다.");
			}
			logger.info("첨부파일 개수 {}", fileList.size());
			if (fileList != null && fileList.size() > 0) {
				for (int i = 0; i < fileList.size(); i++) {
					AttachFiles attachFile = fileList.get(i);
					attachFile.setParentSeq(MajorVO.getSeq());
					AttachFileDao.insertFileProc(attachFile);
				}
			}
			
			MajorDao.saveProcApplication(MajorVO);
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

	@RequestMapping(headers = "Content-Type=application/json", value = "/Major/Application/deleteProcData.json", method = RequestMethod.POST)
	public @ResponseBody ResponseVO deleteProcData(@RequestBody HashMap<String, Object> hashMap) throws Exception {
		ResponseVO response = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		try {
			String[] seqArr = ((String) hashMap.get("seq")).split(",");
			for (String seq : seqArr) {
				MajorDao.deleteProcApplication(seq);
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
