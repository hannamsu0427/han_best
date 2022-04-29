package com.itsp.vo;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class BannerVO implements Serializable {

	private String captchaInput;
	private String rnum;

	private String seq;
	private String title;
	private String contents;

	private String linkUrl;
	private String linkYn;
	private String linkTarget;

	private String setting;

	private String startDate;
	private String endDate;

	private String regDate;
	private String modDate;

	private String useYn;
	private String orderNum;

	private List<AttachFiles> attachFileList;
	private AttachFiles attachFile;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getLinkYn() {
		return linkYn;
	}

	public void setLinkYn(String linkYn) {
		this.linkYn = linkYn;
	}

	public String getLinkTarget() {
		return linkTarget;
	}

	public void setLinkTarget(String linkTarget) {
		this.linkTarget = linkTarget;
	}

	public String getSetting() {
		return setting;
	}

	public void setSetting(String setting) {
		this.setting = setting;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getModDate() {
		return modDate;
	}

	public void setModDate(String modDate) {
		this.modDate = modDate;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public List<AttachFiles> getAttachFileList() {
		return attachFileList;
	}

	public void setAttachFileList(List<AttachFiles> attachFileList) {
		this.attachFileList = attachFileList;
	}

	public AttachFiles getAttachFile() {
		return attachFile;
	}

	public void setAttachFile(AttachFiles attachFile) {
		this.attachFile = attachFile;
	}

}
