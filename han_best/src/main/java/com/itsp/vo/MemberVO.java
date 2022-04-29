package com.itsp.vo;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class MemberVO implements Serializable {

	private String captchaInput;
	private String rnum;

	private String id;
	private String password;// 비밀번호
	private String loginGb;// 로그인구분 (재학생,교직원 등등)
	private String loginFlag;// 로그인구분 플래그(student,staff)
	private boolean isLogin = false;// 로그인여부
	private String redirect;// 로그인후 이동할 주소
	private String userGb;// 사용자구분 (11,12등등)

	private String birth;
	private String question;
	private String answer;
	private String sex;

	private String gwa;
	private String bdCode;

	private int year;// 학년
	private String iphak_year;
	private String gwajung_code;
	private String col;
	private String jolup_gwajung;

	private String div;// 교직원 구분
	private String offDiv;// 퇴사구분
	private String passwdCheck;
	private String iphakDay;

	private String jungong;// 전공

	private String newPassword;

	private String hp;

	private String bd_code;
	private String bd_day;

	// 백도어여부
	private String backDoorYn;

	// 계정관리 관련
	private String user_id;
	private String user_nm;
	private String reg_date;

	private Date passwd_date;
	private String acc_status;
	private Date acc_date;
	private String passwdLast;
	
	private String action;
	private String regIp;

	public String getCaptchaInput() {
		return captchaInput;
	}

	public void setCaptchaInput(String captchaInput) {
		this.captchaInput = captchaInput;
	}

	public String getRnum() {
		return rnum;
	}

	public void setRnum(String rnum) {
		this.rnum = rnum;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoginGb() {
		return loginGb;
	}

	public void setLoginGb(String loginGb) {
		this.loginGb = loginGb;
	}

	public String getLoginFlag() {
		return loginFlag;
	}

	public void setLoginFlag(String loginFlag) {
		this.loginFlag = loginFlag;
	}

	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

	public String getUserGb() {
		return userGb;
	}

	public void setUserGb(String userGb) {
		this.userGb = userGb;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getGwa() {
		return gwa;
	}

	public void setGwa(String gwa) {
		this.gwa = gwa;
	}

	public String getBdCode() {
		return bdCode;
	}

	public void setBdCode(String bdCode) {
		this.bdCode = bdCode;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getIphak_year() {
		return iphak_year;
	}

	public void setIphak_year(String iphak_year) {
		this.iphak_year = iphak_year;
	}

	public String getGwajung_code() {
		return gwajung_code;
	}

	public void setGwajung_code(String gwajung_code) {
		this.gwajung_code = gwajung_code;
	}

	public String getCol() {
		return col;
	}

	public void setCol(String col) {
		this.col = col;
	}

	public String getJolup_gwajung() {
		return jolup_gwajung;
	}

	public void setJolup_gwajung(String jolup_gwajung) {
		this.jolup_gwajung = jolup_gwajung;
	}

	public String getDiv() {
		return div;
	}

	public void setDiv(String div) {
		this.div = div;
	}

	public String getOffDiv() {
		return offDiv;
	}

	public void setOffDiv(String offDiv) {
		this.offDiv = offDiv;
	}

	public String getPasswdCheck() {
		return passwdCheck;
	}

	public void setPasswdCheck(String passwdCheck) {
		this.passwdCheck = passwdCheck;
	}

	public String getIphakDay() {
		return iphakDay;
	}

	public void setIphakDay(String iphakDay) {
		this.iphakDay = iphakDay;
	}

	public String getJungong() {
		return jungong;
	}

	public void setJungong(String jungong) {
		this.jungong = jungong;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getHp() {
		return hp;
	}

	public void setHp(String hp) {
		this.hp = hp;
	}

	public String getBd_code() {
		return bd_code;
	}

	public void setBd_code(String bd_code) {
		this.bd_code = bd_code;
	}

	public String getBd_day() {
		return bd_day;
	}

	public void setBd_day(String bd_day) {
		this.bd_day = bd_day;
	}

	public String getBackDoorYn() {
		return backDoorYn;
	}

	public void setBackDoorYn(String backDoorYn) {
		this.backDoorYn = backDoorYn;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_nm() {
		return user_nm;
	}

	public void setUser_nm(String user_nm) {
		this.user_nm = user_nm;
	}

	public String getReg_date() {
		return reg_date;
	}

	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}

	public Date getPasswd_date() {
		return passwd_date;
	}

	public void setPasswd_date(Date passwd_date) {
		this.passwd_date = passwd_date;
	}

	public String getAcc_status() {
		return acc_status;
	}

	public void setAcc_status(String acc_status) {
		this.acc_status = acc_status;
	}

	public Date getAcc_date() {
		return acc_date;
	}

	public void setAcc_date(Date acc_date) {
		this.acc_date = acc_date;
	}

	public String getPasswdLast() {
		return passwdLast;
	}

	public void setPasswdLast(String passwdLast) {
		this.passwdLast = passwdLast;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getRegIp() {
		return regIp;
	}

	public void setRegIp(String regIp) {
		this.regIp = regIp;
	}
	
	

}
