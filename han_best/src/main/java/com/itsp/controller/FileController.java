package com.itsp.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.itsp.common.CommUtils;
import com.itsp.common.Config;
import com.itsp.dao.AttachFileDao;
import com.itsp.vo.AttachFiles;
import com.itsp.vo.CKEditorFiles;
import com.itsp.vo.ResponseHeaderVO;
import com.itsp.vo.ResponseVO;

@Controller
public class FileController {

	@Autowired
	AttachFileDao AttachFileDao;

	static Logger logger = Logger.getLogger(FileController.class.getName());

	private Map<String, MultipartFile> fileMap;

	private void fileOutput(String downloadFilePath, String downloadFilename, String realFilename, String saveFilePath,
			String mimeType, HttpServletResponse response) throws Exception {

		FileInputStream fis = null;
		OutputStream out = null;
		logger.info("fileOutput Start");
		try {
			String tempPath = downloadFilePath;
			String tempName = downloadFilename;
			downloadFilePath = tempPath.replaceAll("\\\\", Config.PATH_SEPARATOR);
			downloadFilename = tempName.replaceAll("\\\\", Config.PATH_SEPARATOR);

			logger.info("downloadFile=[" + (downloadFilePath + Config.PATH_SEPARATOR + downloadFilename) + "]");
			File downloadFile = new File(downloadFilePath + Config.PATH_SEPARATOR + downloadFilename);

			if (realFilename == null || "".equals(realFilename))
				realFilename = downloadFile.getName();
			realFilename = URLEncoder.encode(realFilename, "UTF-8");

			response.setCharacterEncoding("UTF-8");
			response.setContentType(mimeType);
			response.setContentLength((int) downloadFile.length());
			response.setHeader("Content-Disposition", "attachment; filename=\"" + realFilename + "\";");
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.setHeader("Pragma", "");
			response.setHeader("Expires", "-1");

			out = response.getOutputStream();

			fis = new FileInputStream(downloadFile);
			FileCopyUtils.copy(fis, out);
		} catch (Exception e) {
			e.printStackTrace();
			CommUtils.stackTraceToString(e);
			throw e;
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (out != null) {
				out.flush();
			}
		}
	}

	private void fileViewput(String downloadFilePath, String downloadFilename, String realFilename, String saveFilePath,
			String mimeType, HttpServletResponse response) throws Exception {

		BufferedOutputStream out = null;
		InputStream in = null;

		try {
			logger.info("fileViewput Start");
			String tempPath = downloadFilePath;
			String tempName = downloadFilename;
			downloadFilePath = tempPath.replaceAll("\\\\", Config.PATH_SEPARATOR);
			downloadFilename = tempName.replaceAll("\\\\", Config.PATH_SEPARATOR);

			logger.info("downloadFile=[" + (downloadFilePath + Config.PATH_SEPARATOR + downloadFilename) + "]");
			
			response.setContentType(mimeType);
			response.setHeader("Content-Disposition", "inline;filename=" + realFilename);
			File imgFile = new File(downloadFilePath + Config.PATH_SEPARATOR + downloadFilename);
			if(imgFile.exists()){
				in = new FileInputStream(imgFile);
				out = new BufferedOutputStream(response.getOutputStream());
				int len;
				byte[] buf = new byte[1024];
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			CommUtils.stackTraceToString(e);
			throw e;
		} finally {
			if(out != null){ out.flush(); }
			if(out != null){ out.close(); }
			if(in != null){ in.close(); }
		}
	}
	
	private void imgResizeViewPut(String downloadFilePath, String downloadFilename, String realFilename, String saveFilePath,
			String mimeType, HttpServletResponse response) throws Exception {

		BufferedOutputStream out = null;
		InputStream in = null;

		try {
			logger.info("fileViewput Start");
			String tempPath = downloadFilePath;
			String tempName = downloadFilename;
			downloadFilePath = tempPath.replaceAll("\\\\", Config.PATH_SEPARATOR);
			downloadFilename = tempName.replaceAll("\\\\", Config.PATH_SEPARATOR);

			logger.info("downloadFile=[" + (downloadFilePath + Config.PATH_SEPARATOR + downloadFilename) + "]");
			
			response.setContentType(mimeType);
			response.setHeader("Content-Disposition", "inline;filename=" + realFilename);
			
			File imgFile = new File(downloadFilePath + Config.PATH_SEPARATOR + downloadFilename);
			if (!imgFile.isFile() && tempName.contains("resize_")) {
				tempName = tempName.replace("resize_", "");
				downloadFilename = tempName.replaceAll("\\\\", Config.PATH_SEPARATOR);
				imgFile = new File(downloadFilePath + Config.PATH_SEPARATOR + downloadFilename);
			}
			
			if(imgFile.exists()){
				in = new FileInputStream(imgFile);
				out = new BufferedOutputStream(response.getOutputStream());
				int len;
				byte[] buf = new byte[1024];
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			CommUtils.stackTraceToString(e);
			throw e;
		} finally {
			if(out != null){ out.flush(); }
			if(out != null){ out.close(); }
			if(in != null){ in.close(); }
		}
	}

	@RequestMapping("/file/fileDownLoad")
	public void fileDownLoad(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seq = request.getParameter("seq");
		AttachFiles attachFile = AttachFileDao.selectFile(seq);
		String saveFilePath = attachFile.getRealFilePath();

		String downloadFilePath = AttachFiles.getRealFilePath(attachFile.getRealFilePath());
		String downloadFilename = attachFile.getSavedFileName();
		String realFileName = attachFile.getRealFileName();

		fileOutput(downloadFilePath, downloadFilename, realFileName, saveFilePath, "application/octet-stream", response);
	}

	/***
	 * img src
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/file/imgView")
	public void fileImgSrcView(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seq = request.getParameter("seq");
		AttachFiles attachFile = AttachFileDao.selectFile(seq);
		String saveFilePath = attachFile.getRealFilePath();

		String downloadFilename = "";
		String realFilename = "";
		String downloadFilePath = Config.COMMON_FILE_PATH;
		String fileType = "png";

		downloadFilename = attachFile.getSavedFileName();
		realFilename = attachFile.getRealFileName();
		downloadFilePath = AttachFiles.getRealFilePath(attachFile.getRealFilePath());
		fileType = attachFile.getFileType();

		logger.info("path>>" + downloadFilePath);

		fileViewput(downloadFilePath, downloadFilename, realFilename, saveFilePath, "image/" + fileType, response);
	}
	
	@RequestMapping("/file/imgResizeView")
	public void imgResizeView(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seq = request.getParameter("seq");
		AttachFiles attachFile = AttachFileDao.selectFile(seq);
		String saveFilePath = attachFile.getRealFilePath();

		String downloadFilename = "";
		String realFilename = "";
		String downloadFilePath = Config.COMMON_FILE_PATH;
		String fileType = "png";

		downloadFilename = "resize_" + attachFile.getSavedFileName();
		realFilename = attachFile.getRealFileName();
		downloadFilePath = AttachFiles.getRealFilePath(attachFile.getRealFilePath());
		fileType = attachFile.getFileType();

		logger.info("path>>" + downloadFilePath);

		imgResizeViewPut(downloadFilePath, downloadFilename, realFilename, saveFilePath, "image/" + fileType, response);
	}

	/***
	 * pdf src
	 *
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/file/pdfView")
	public void pdfView(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String seq = request.getParameter("seq");
		AttachFiles attachFile = AttachFileDao.selectFile(seq);
		String saveFilePath = attachFile.getRealFilePath();

		String downloadFilename = "";
		String realFilename = "";
		String downloadFilePath = Config.COMMON_FILE_PATH;

		downloadFilename = attachFile.getSavedFileName();
		realFilename = attachFile.getRealFileName();
		downloadFilePath = AttachFiles.getRealFilePath(attachFile.getSavedFilePath());

		fileViewput(downloadFilePath, downloadFilename, realFilename, saveFilePath, "application/pdf", response);
	}

	@RequestMapping("/file/ckImgView")
	public void ckImgView(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(defaultValue = "") String fileUrl) throws Exception {
		String downloadFilePath = Config.COMMON_FILE_PATH_EDITOR;
		String fileType = "";
		String saveFilePath = "";
		fileViewput(downloadFilePath, fileUrl, fileUrl, saveFilePath, "image/" + fileType, response);
	}

	@RequestMapping(value = "/file/ckFileUpload", method = RequestMethod.POST)
	public String ckFileUpload(CKEditorFiles fileBean, HttpServletRequest request, Model model) {

		String rootPath = Config.COMMON_FILE_PATH_EDITOR; // 웹서비스 root 경로
		String attachPath = Config.PATH_SEPARATOR;

		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String today = sdf.format(date);

		String year = today.substring(0, 4);
		String month = today.substring(4, 6);
		attachPath += year + Config.PATH_SEPARATOR + month + Config.PATH_SEPARATOR;
		String fileFolder = rootPath + attachPath;
		File dirs = new File(fileFolder);
		dirs.mkdirs();

		MultipartFile upload = fileBean.getUpload();
		String filename = "";
		String CKEditorFuncNum = "";

		if (upload != null) {
			filename = upload.getOriginalFilename();

			// 서버에 업로드 할 파일명(한글문제로 인해 원본파일은 올리지 않는것이 좋음)
			String ext = filename.substring(filename.lastIndexOf(".") + 1);
			filename = UUID.randomUUID().toString() + "." + ext;

			fileBean.setFilename(filename);
			CKEditorFuncNum = fileBean.getCKEditorFuncNum();

			try {
				MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
				fileMap = (Map<String, MultipartFile>) multipart.getFileMap();
				Iterator<String> formFieldNames = (Iterator<String>) multipart.getFileNames();
				String result = "Y";

				while (formFieldNames.hasNext()) {
					String formFieldName = (String) formFieldNames.next();
					MultipartFile multipartFile = fileMap.get(formFieldName);

					long fileSize = multipartFile.getSize();
					String fileType = multipartFile.getContentType();
					if (fileSize > 0) {
						logger.info("File fileSize > 0 ");
						// 설정파일에서 업로드 제한 용량 확인
						if (multipartFile.getSize() > Config.FILE_UPLOAD_LIMIT) {
							// 지정 용량을 초과 한 경우 메시지 출력
							String sizeExcess = "지정된 용량을 초과하였습니다.(" + Config.FILE_UPLOAD_LIMIT + " byte)";
							model.addAttribute("sizeExcess", sizeExcess);
							result = "N";
						}

						String[] type = Config.IMAGE_MIME_TYPE;
						String fileResult = "N";
						for (int i = 0; i < type.length; i++) {
							if (ext.equals(type[i])) {
								fileResult = "Y";
							}
						}

						if (fileResult != "Y") {
							model.addAttribute("error", "올바르지 않은 파일입니다.");
							result = "N";
						}
					}
				}

				if (result == "Y") {
					logger.info("File result ::: Y ");
					File file = new File(fileFolder + filename);
					upload.transferTo(file);
					String filePath = "/file/ckImgView.do?fileUrl=" + attachPath + filename;
					model.addAttribute("filePath", filePath);
					model.addAttribute("CKEditorFuncNum", CKEditorFuncNum);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "ckEditor_fileUpload";
	}

	@RequestMapping(headers = "Content-Type=application/json", value = "/AttachFile/deleteFileProc.json", method = RequestMethod.POST)
	public @ResponseBody ResponseVO deleteFileProc(@RequestBody HashMap<String, Object> hashMap) throws Exception {

		ResponseVO response = new ResponseVO();
		ResponseHeaderVO responseHeader = new ResponseHeaderVO();

		try {
			String seq = (String) hashMap.get("seq");
			AttachFiles attachFile = AttachFileDao.selectFile(seq);
			CommUtils.fileDelete(attachFile);
			AttachFileDao.deleteFileProc(seq);
			responseHeader.setCode(ResponseHeaderVO.SUCCESS);
		} catch (Exception e) {
			responseHeader.setCode(ResponseHeaderVO.FAIL);
			responseHeader.setMessage(ResponseHeaderVO.FAIL_MESSAGE);
		}

		response.setHeader(responseHeader);
		return response;
	}

}