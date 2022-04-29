package com.itsp.vo;

import java.util.List;

public class ResponseVO {
	private ResponseHeaderVO header;
	private List<?> body;

	public ResponseHeaderVO getHeader() {
		return header;
	}

	public void setHeader(ResponseHeaderVO header) {
		this.header = header;
	}

	public List<?> getBody() {
		return body;
	}

	public void setBody(List<?> body) {
		this.body = body;
	}

}
