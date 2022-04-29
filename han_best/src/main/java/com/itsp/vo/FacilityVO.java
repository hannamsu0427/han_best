package com.itsp.vo;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class FacilityVO implements Serializable {

	private String captchaInput;
	private String rnum;

	private String configSeq;
	private String configName;

	private String seq;

	private String title;
	private String explanation;
	private String contents;

	private String useYn;
	private String orderNum;
	private String recordCnt;

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

	private List<AttachFiles> attachFileList;
	private AttachFiles attachFile;

	private List<EtcVO> etcList;

	private String etc1;
	private String etc2;

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

	public String getConfigSeq() {
		return configSeq;
	}

	public void setConfigSeq(String configSeq) {
		this.configSeq = configSeq;
	}

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
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

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
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

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getRecordCnt() {
		return recordCnt;
	}

	public void setRecordCnt(String recordCnt) {
		this.recordCnt = recordCnt;
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

	public List<EtcVO> getEtcList() {
		return etcList;
	}

	public void setEtcList(List<EtcVO> etcList) {
		this.etcList = etcList;
	}

	public String getEtc1() {
		return etc1;
	}

	public void setEtc1(String etc1) {
		this.etc1 = etc1;
	}

	public String getEtc2() {
		return etc2;
	}

	public void setEtc2(String etc2) {
		this.etc2 = etc2;
	}

}
