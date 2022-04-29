package com.itsp.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GoogleAnalyticsVO implements Serializable {

	private String captchaInput;
	private String rnum;

	private String seq;
	private String contents;
	private String useYn;

	private String regId;
	private String regName;
	private String regDate;
	private String regDateFmt;
	private String regIp;

	private String modId;
	private String modName;
	private String modDate;
	private String modDateFmt;
	private String modIp;

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

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getRegName() {
		return regName;
	}

	public void setRegName(String regName) {
		this.regName = regName;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getRegDateFmt() {
		return regDateFmt;
	}

	public void setRegDateFmt(String regDateFmt) {
		this.regDateFmt = regDateFmt;
	}

	public String getRegIp() {
		return regIp;
	}

	public void setRegIp(String regIp) {
		this.regIp = regIp;
	}

	public String getModId() {
		return modId;
	}

	public void setModId(String modId) {
		this.modId = modId;
	}

	public String getModName() {
		return modName;
	}

	public void setModName(String modName) {
		this.modName = modName;
	}

	public String getModDate() {
		return modDate;
	}

	public void setModDate(String modDate) {
		this.modDate = modDate;
	}

	public String getModDateFmt() {
		return modDateFmt;
	}

	public void setModDateFmt(String modDateFmt) {
		this.modDateFmt = modDateFmt;
	}

	public String getModIp() {
		return modIp;
	}

	public void setModIp(String modIp) {
		this.modIp = modIp;
	}

}
