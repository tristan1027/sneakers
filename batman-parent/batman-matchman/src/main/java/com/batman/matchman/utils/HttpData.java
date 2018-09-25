package com.batman.matchman.utils;

import java.io.UnsupportedEncodingException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;

public class HttpData {
	private static final Log LOG = LogFactory.getLog(HttpData.class);
	public static final int DEF_LANGUAGE = 1;
	public static final String DEF_CHARSET = "UTF-8";
	private String contentStr;
	private byte[] contentByte;
	private String charset;
	private String url;
	private Header[] headers;

	public HttpData() {
	}

	public HttpData(byte[] contentByte, String charset) {
		this.contentByte = contentByte;
		this.charset = charset;
		try {
			contentStr = new String(contentByte, charset);
			contentStr += "\n";
		} catch (UnsupportedEncodingException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	public HttpData(byte[] bytes, String charset, String url) {
		contentByte = bytes;
		this.charset = charset;
		this.url = url;
		try {
			if (contentByte == null) {
				contentStr = null;
			} else {
				contentStr = new String(contentByte, charset);
				contentStr += "\n";
			}
		} catch (UnsupportedEncodingException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	public HttpData(String contentStr, String charset) {
		this.contentStr = contentStr;
		this.charset = charset;
		try {
			contentByte = this.contentStr.getBytes(charset);
			this.contentStr += "\n";
		} catch (UnsupportedEncodingException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	public HttpData(String contentStr, String charset, String url) {
		this.contentStr = contentStr;
		this.charset = charset;
		this.url = url;
		try {
			contentByte = this.contentStr.getBytes(charset);
			this.contentStr += "\n";
		} catch (UnsupportedEncodingException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	public String getContentStr() {
		return contentStr;
	}

	public void setContentStr(String contentStr) {
		this.contentStr = contentStr;
	}

	public byte[] getContentByte() {
		return contentByte;
	}

	public void setContentByte(byte[] contentByte) {
		this.contentByte = contentByte;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Header[] getHeaders() {
		return headers;
	}

	public void setHeaders(Header[] headers) {
		this.headers = headers;
	}


}
