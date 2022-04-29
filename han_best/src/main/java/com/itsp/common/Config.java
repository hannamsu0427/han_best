package com.itsp.common;

import java.util.ArrayList;
import java.util.Arrays;

public class Config {

	public static String COMMON_FILE_PATH = "/home/itsp/file/CHUGYE_CONSERVATORY/upload";
	public static String COMMON_FILE_PATH_EDITOR = "/home/itsp/file/CHUGYE_CONSERVATORY/upload/ckEditor";
	public static String PATH_SEPARATOR = "/";

	public static String SESSION_SITE = "sessionScopeSite";
	public static String SESSION_MEMBER = "sessionScopeMember";

	public static String SESSION_GOOGLE_ANALYTICS = "googleAnalytics";

	public static String OS_TYPE_WEB = "web";

	public static long FILE_UPLOAD_LIMIT = 314572800; // 300MB
	
	public static String[] IMAGE_MIME_TYPE = { "jpeg", "pjpeg", "gif", "png", "jpg", "bmp", "image/jpeg" };
	public static String[] VIDEO_ITSP_MIME_TYPE = { "mp4" };
	public static String[] PDF_ITSP_MIME_TYPE = { "pdf" };
	public static String[] ATT_MIME_TYPE = { "x-zip-compressed", "excel", "vnd.ms-excel", "x-excel", "x-msexcel",
			"vnd.openxmlformats-officedocument.spreadsheetml.sheet"
			/* var doc = */ , "msword", "vnd.openxmlformats-officedocument.wordprocessingml.document","haansoftxls"
			/* var ppt = */ , "mspowerpoint", "powerpoint", "vnd.ms-powerpoint", "x-mspowerpoint", "vnd.openxmlformats-officedocument.presentationml.presentation"
			/* var pdf = */ , "pdf"
			/* var hwp = */ , "haansofthwp", "x-hwp", "hangul", "jpeg", "pjpeg", "gif", "png", "jpg", "quicktime", "x-msvideo", "x-ms-wmv", "vnd.mpegurl", "vnd.objectvideo", "vnd.sealed.mpeg1", "vnd.sealed.mpeg4", "vnd.sealed.swf", "vnd.sealedmedia.softseal.mov", "vnd.vivo", "x-sgi-movie", "x-msvideo", "mp4", "zip", "rar", "apk", "msi","octet-stream" };

	private static ArrayList<String> imageMimeList = null;
	private static ArrayList<String> videoItspMimeList = null;
	private static ArrayList<String> pdfItspMimeList = null;
	private static ArrayList<String> attMimeList = null;

	public static ArrayList<String> GetImageMimeList() {
		if (imageMimeList == null) {
			imageMimeList = new ArrayList<String>();
			imageMimeList.addAll(Arrays.asList(IMAGE_MIME_TYPE));
		}
		return imageMimeList;
	}

	public static ArrayList<String> GetVideoItspMimeList() {
		if (videoItspMimeList == null) {
			videoItspMimeList = new ArrayList<String>();
			videoItspMimeList.addAll(Arrays.asList(VIDEO_ITSP_MIME_TYPE));
		}
		return videoItspMimeList;
	}
	
	public static ArrayList<String> GetPdfItspMimeList() {
		if (pdfItspMimeList == null) {
			pdfItspMimeList = new ArrayList<String>();
			pdfItspMimeList.addAll(Arrays.asList(PDF_ITSP_MIME_TYPE));
		}
		return videoItspMimeList;
	}

	public static ArrayList<String> GetAttMimeList() {
		if (attMimeList == null) {
			attMimeList = new ArrayList<String>();
			attMimeList.addAll(Arrays.asList(ATT_MIME_TYPE));
		}
		return attMimeList;
	}
}
