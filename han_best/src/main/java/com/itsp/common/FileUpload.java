package com.itsp.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.plexus.util.FileUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.itsp.vo.AttachFiles;

public class FileUpload {

	private Map<String, Object> filesInfo;

	private List<AttachFiles> fileList;

	private String savePath;
	private String realFilePath;
	private String resultMsg;
	private Map<String, MultipartFile> fileMap;

	private AttachFiles attachFile;

	private static Logger logger = Logger.getLogger(FileUpload.class.getName());

	public String getResultMsg() {
		return resultMsg;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public Map<String, Object> getFilesInfo() {
		return filesInfo;
	}

	public List<AttachFiles> getFileList() {
		return fileList;
	}

	public void setRequestFilesInfo(HttpServletRequest request, HttpServletResponse response, String savePath) {
		setSavePath(savePath);
		setFilesInfo(request, response);
	}

	public String setFilesInfo(HttpServletRequest request, HttpServletResponse response) {

		filesInfo = new HashMap<String, Object>();
		MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
		fileMap = (Map<String, MultipartFile>) multipart.getFileMap();
		Iterator<String> formFieldNames = (Iterator<String>) multipart.getFileNames();
		String result = "Y";
		fileList = new ArrayList<AttachFiles>();

		while (formFieldNames.hasNext()) {

			String formFieldName = (String) formFieldNames.next();
			MultipartFile multipartFile = fileMap.get(formFieldName);

			long fileSize = multipartFile.getSize();
			String fileType = multipartFile.getContentType();
			logger.info("file content-type : " + fileType);
			if (fileSize > 0) {

				// 설정파일에서 업로드 제한 용량 확인
				if (multipartFile.getSize() > Config.FILE_UPLOAD_LIMIT) {
					// 지정 용량을 초과 한 경우 메시지 출력
					return "size";
				}

				String type = fileType.split("/")[1];
				if (!Config.GetAttMimeList().contains(type)) {
					result = "N";
				}
				
				String fileCnt = formFieldName.replace("file_", "");
				String fileEtc = multipart.getParameter("fileEtc_" + fileCnt);
				
				String originalFilename = multipartFile.getOriginalFilename();
				String destFilePath = savePath + File.separator;
				System.out.println("savePathsavePathsavePathsavePathsavePath : " + savePath);
				String destFilename = System.nanoTime() + "." + CommUtils.getRestWord(originalFilename, ".");

				AttachFiles attachFiles = new AttachFiles();

				attachFiles.setRealFileName(originalFilename);
				attachFiles.setRealFilePath(realFilePath);
				attachFiles.setSavedFileName(destFilename);
				attachFiles.setSavedFilePath(realFilePath);
				attachFiles.setFileSize((int) fileSize);
				attachFiles.setFileType(type);
				attachFiles.setExplanation(fileEtc);
				attachFiles.setFormFieldName(formFieldName);
				logger.info("formFieldName : " + formFieldName);
				filesInfo.put(formFieldName, attachFiles);
				setAttachFile(attachFiles);
				fileList.add(attachFiles);
			} else
				setAttachFile(new AttachFiles());
		}
		return result;
	}

	public void fileCopyAll() {
		Iterator<Entry<String, Object>> i = filesInfo.entrySet().iterator();
		while (i.hasNext()) {
			Entry<String, Object> entry = i.next();
			AttachFiles attachFile = (AttachFiles) entry.getValue();
			String filePath = AttachFiles.getRealFilePath(attachFile.getRealFilePath()) + File.separator;
			try {
				FileUtils.forceMkdir(new File(filePath));
				File destFile = new File(filePath + attachFile.getSavedFileName());
				fileMap.get(attachFile.getFormFieldName()).transferTo(destFile);

				String tmpPath = filePath;
				String tmpFileName = attachFile.getSavedFileName();
				int temp = attachFile.getSavedFileName().lastIndexOf(".");
				String ext = attachFile.getSavedFileName().substring(temp + 1);
				if ("jpg".equalsIgnoreCase(ext) || "gif".equalsIgnoreCase(ext) || "jpeg".equalsIgnoreCase(ext) || "bmp".equalsIgnoreCase(ext) || "png".equalsIgnoreCase(ext)) {
					String fullPath = tmpPath + "/" + tmpFileName;
					ImageResize.resizeImage(fullPath, 1080, 1080, tmpPath, tmpFileName);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean fileDelete(String filePath) {
		boolean flag = false;
		File file = new File(filePath);
		if (file.exists()) {
			flag = file.delete();
			logger.info("파일삭제여부: " + flag);
		} else {
			resultMsg = "파일이 존재하지 않습니다";
		}
		return flag;
	}

	public AttachFiles getAttachFile() {
		return attachFile;
	}

	public void setAttachFile(AttachFiles attachFile) {
		this.attachFile = attachFile;
	}

	public String getRealFilePath() {
		return realFilePath;
	}

	public void setRealFilePath(String realFilePath) {
		this.realFilePath = realFilePath;
	}

}
