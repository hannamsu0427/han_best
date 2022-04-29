package com.itsp.vo;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class BoardVO implements Serializable {

	private String captchaInput;
	private String rnum;

	private String recordSeq;
	private String groupSeq;
	private String parentSeq;
	private String depth;

	private String seq;
	private String useYn;
	private String type;
	private String noticeYn;
	private String title;
	private String subTitle;

	private String newDay;
	private String preNextYn;
	private String fileYn;
	private String fileCnt;
	private String fileSize;
	private String fileType;
	private String commentYn;
	private String replyYn;
	private String editorYn;
	private String snsYn;
	private String printYn;
	private String ipYn;
	private String secretYn;
	private String delYn;
	private String nonMember;

	private String configSeq;
	private String configName;
	private String configType;
	private String categorySeq;
	private String categoryName;

	private String recordCnt;
	private String orderNum;
	private String hitCnt;
	private String contents;
	private String url;
	private String password;

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

	private String nextSeq;
	private String nextTitle;
	private String nextRegDate;

	private String preSeq;
	private String preTitle;
	private String preRegDate;

	private List<BoardVO> commentList;
	private BoardVO replyData;

	private String commentCnt;
	private String replyCnt;

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

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNoticeYn() {
		return noticeYn;
	}

	public void setNoticeYn(String noticeYn) {
		this.noticeYn = noticeYn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getNewDay() {
		return newDay;
	}

	public void setNewDay(String newDay) {
		this.newDay = newDay;
	}

	public String getPreNextYn() {
		return preNextYn;
	}

	public void setPreNextYn(String preNextYn) {
		this.preNextYn = preNextYn;
	}

	public String getFileYn() {
		return fileYn;
	}

	public void setFileYn(String fileYn) {
		this.fileYn = fileYn;
	}

	public String getFileCnt() {
		return fileCnt;
	}

	public void setFileCnt(String fileCnt) {
		this.fileCnt = fileCnt;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getCommentYn() {
		return commentYn;
	}

	public void setCommentYn(String commentYn) {
		this.commentYn = commentYn;
	}

	public String getReplyYn() {
		return replyYn;
	}

	public void setReplyYn(String replyYn) {
		this.replyYn = replyYn;
	}

	public String getEditorYn() {
		return editorYn;
	}

	public void setEditorYn(String editorYn) {
		this.editorYn = editorYn;
	}

	public String getSnsYn() {
		return snsYn;
	}

	public void setSnsYn(String snsYn) {
		this.snsYn = snsYn;
	}

	public String getPrintYn() {
		return printYn;
	}

	public void setPrintYn(String printYn) {
		this.printYn = printYn;
	}

	public String getIpYn() {
		return ipYn;
	}

	public void setIpYn(String ipYn) {
		this.ipYn = ipYn;
	}

	public String getSecretYn() {
		return secretYn;
	}

	public void setSecretYn(String secretYn) {
		this.secretYn = secretYn;
	}

	public String getDelYn() {
		return delYn;
	}

	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}

	public String getNonMember() {
		return nonMember;
	}

	public void setNonMember(String nonMember) {
		this.nonMember = nonMember;
	}

	public String getCategorySeq() {
		return categorySeq;
	}

	public void setCategorySeq(String categorySeq) {
		this.categorySeq = categorySeq;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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

	public String getConfigType() {
		return configType;
	}

	public void setConfigType(String configType) {
		this.configType = configType;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getHitCnt() {
		return hitCnt;
	}

	public void setHitCnt(String hitCnt) {
		this.hitCnt = hitCnt;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGroupSeq() {
		return groupSeq;
	}

	public void setGroupSeq(String groupSeq) {
		this.groupSeq = groupSeq;
	}

	public String getParentSeq() {
		return parentSeq;
	}

	public void setParentSeq(String parentSeq) {
		this.parentSeq = parentSeq;
	}

	public String getDepth() {
		return depth;
	}

	public void setDepth(String depth) {
		this.depth = depth;
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

	public String getNextSeq() {
		return nextSeq;
	}

	public void setNextSeq(String nextSeq) {
		this.nextSeq = nextSeq;
	}

	public String getNextTitle() {
		return nextTitle;
	}

	public void setNextTitle(String nextTitle) {
		this.nextTitle = nextTitle;
	}

	public String getNextRegDate() {
		return nextRegDate;
	}

	public void setNextRegDate(String nextRegDate) {
		this.nextRegDate = nextRegDate;
	}

	public String getPreSeq() {
		return preSeq;
	}

	public void setPreSeq(String preSeq) {
		this.preSeq = preSeq;
	}

	public String getPreTitle() {
		return preTitle;
	}

	public void setPreTitle(String preTitle) {
		this.preTitle = preTitle;
	}

	public String getPreRegDate() {
		return preRegDate;
	}

	public void setPreRegDate(String preRegDate) {
		this.preRegDate = preRegDate;
	}

	public List<BoardVO> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<BoardVO> commentList) {
		this.commentList = commentList;
	}

	public BoardVO getReplyData() {
		return replyData;
	}

	public void setReplyData(BoardVO replyData) {
		this.replyData = replyData;
	}

	public String getRecordCnt() {
		return recordCnt;
	}

	public void setRecordCnt(String recordCnt) {
		this.recordCnt = recordCnt;
	}

	public String getCommentCnt() {
		return commentCnt;
	}

	public void setCommentCnt(String commentCnt) {
		this.commentCnt = commentCnt;
	}

	public String getReplyCnt() {
		return replyCnt;
	}

	public void setReplyCnt(String replyCnt) {
		this.replyCnt = replyCnt;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRecordSeq() {
		return recordSeq;
	}

	public void setRecordSeq(String recordSeq) {
		this.recordSeq = recordSeq;
	}

}
