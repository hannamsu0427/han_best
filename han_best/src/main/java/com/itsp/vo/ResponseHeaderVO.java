package com.itsp.vo;

public class ResponseHeaderVO {
	public final static String SUCCESS = "00";
	public final static String SUCCESS_MESSAGE = "성공했습니다.";
	
	public final static String NO_LOGIN = "90";
	public final static String NO_LOGIN_MESSAGE = "로그인 하여 주십시오.";
	
	public final static String NO_AUTH = "91";
	public final static String NO_AUTH_MESSAGE = "권한이 없습니다.";
	
	public final static String DUP_USER_ID = "92";
	public final static String DUP_USER_ID_MESSAGE = "아이디가 중복되었습니다.";
	
	public final static String DUP_USER_NAME = "93";
	public final static String DUP_USER_NAME_MESSAGE = "이름이 중복되었습니다.";
	
	public final static String FAIL = "99";
	public final static String FAIL_MESSAGE = "실패했습니다.";

	private String code;
	private String message;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
		String message = "";
		if (SUCCESS.equals(code)) {
			message = SUCCESS_MESSAGE;
		} else if (NO_LOGIN.equals(code)) {
			message = NO_LOGIN_MESSAGE;
		} else if (NO_AUTH.equals(code)) {
			message = NO_AUTH_MESSAGE;
		} else if (DUP_USER_ID.equals(code)) {
			message = DUP_USER_ID_MESSAGE;
		} else if (DUP_USER_NAME.equals(code)) {
			message = DUP_USER_NAME_MESSAGE;
		} else if (FAIL.equals(code)) {
			message = FAIL_MESSAGE;
		}
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
