package com.batman.matchman.utils;

import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;

public class HttpService {
	private static final Log LOG = LogFactory.getLog(HttpService.class);
	HttpHandle httpHandle = null;

	public HttpService() {
		this(false, null, null, true, null, 0, 0, 60000);
	}

	public HttpService(Map<String, String> headers) {
		this(false, null, null, true, headers, 0, 0, 60000);
	}

	public HttpService(boolean needDelay, int minDelayTime, int maxDelayTime) {
		this(false, null, null, needDelay, null, minDelayTime, maxDelayTime, 60000);
	}

	public HttpService(boolean needDelay, int minDelayTime, int maxDelayTime, int connectTimeout) {
		this(false, null, null, needDelay, null, minDelayTime, maxDelayTime, connectTimeout);
	}

	public HttpService(boolean needDelay, Map<String, String> headers) {
		this(false, null, null, needDelay, headers, 0, 0, 60000);
	}

	public HttpService(boolean isHttps, boolean needDelay, Map<String, String> headers) {
		if (!isHttps) {
			httpHandle = new HttpHandle(needDelay, headers, 0, 0, 60000);
		} else {
			httpHandle = new HttpHandle(isHttps, null, null, needDelay, 0, 0, 60000);
		}
	}

	public HttpService(boolean isHttps, String digitalCertificatePath, String digitalCertificatePsw, boolean needDelay,
			Map<String, String> headers, int minDelayTime, int maxDelayTime, int connectTimeout) {
		if (!isHttps) {
			httpHandle = new HttpHandle(needDelay, headers, minDelayTime, maxDelayTime, connectTimeout);
		} else {
			httpHandle = new HttpHandle(isHttps, digitalCertificatePath, digitalCertificatePsw, needDelay, minDelayTime,
					maxDelayTime, connectTimeout);
		}
	}

	public HttpData get(String url) {
		return get(url, null, -1, null, null, false, true, 0);
	}

	public HttpData get(String url, boolean specialEncode) {
		return get(url, null, -1, null, null, false, specialEncode, 0);
	}

	public HttpData getNeedCookie(String url) {
		return get(url, null, -1, null, null, true, true, 0);
	}

	public HttpData get(String url, String charset) {
		return get(url, null, -1, null, charset, false, true, 0);
	}

	public HttpData get(String url, String host, int port) {
		return get(url, host, port, null, null, false, true, 0);
	}

	public HttpData get(String url, String host, int port, String charset) {
		return get(url, host, port, null, charset, false, true, 0);
	}

	public HttpData getWithHeads(String url, Map<String, String> heads) {
		return get(url, null, -1, heads, null, false, true, 0);
	}

	public HttpData getWithHeads(String url, String host, int port, Map<String, String> heads) {
		return get(url, host, port, heads, null, false, true, 0);
	}

	public HttpData get(String url, String host, int port, Map<String, String> heads, String charset,
			boolean needCookie, boolean specialEncode, int tryTimes) {
		try {
			return httpHandle.get(url, host, port, heads, charset, needCookie, specialEncode, tryTimes);
		} catch (ClientProtocolException e) {
			LOG.error(url);
			LOG.error(e.getMessage(), e);
			return null;
		} catch (IOException e) {
			LOG.error(url);
			LOG.error(e.getMessage(), e);
		}
		return null;
	}

	public HttpData post(String url, Map<String, String> params) {
		return post(url, params, null, -1, null, null, true);
	}

	public HttpData post(String url, Map<String, String> params, boolean specialEncode) {
		return post(url, params, null, -1, null, null, specialEncode);
	}

	public HttpData post(String url, Map<String, String> params, String charset) {
		return post(url, params, null, -1, null, charset, true);
	}

	public HttpData post(String url, Map<String, String> params, String host, int port) {
		return post(url, params, host, port, null, null, true);
	}

	public HttpData post(String url, Map<String, String> params, String host, int port, String charset) {
		return post(url, params, host, port, null, charset, true);
	}

	public HttpData postWithHeads(String url, Map<String, String> params, Map<String, String> heads) {
		return post(url, params, null, -1, heads, null, true);
	}

	public HttpData postWithHeads(String url, Map<String, String> params, String host, int port,
			Map<String, String> heads) {
		return post(url, params, host, port, heads, null, true);
	}

	public HttpData post(String url, Map<String, String> params, String host, int port, Map<String, String> heads,
			String charset, boolean specialEncode) {
		try {
			return httpHandle.post(url, params, host, port, heads, charset, specialEncode);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			LOG.error(url);
			LOG.error(e.getMessage(), e);
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			LOG.error(url);
			LOG.error(e.getMessage(), e);
		}
		return null;
	}

	public String getCookies() {
		return httpHandle.getCookies();
	}

	public void setCookieString(String cookieString) {
		httpHandle.setCookieString(cookieString);
	}

	public static void main(String[] args) {
		HttpService httpService = new HttpService();
		HttpData data = null;

		String text = "{\"indexes\":[\"404\"],\"types\":[\"0\",\"0\",\"3\",\"6\",\"2\",\"7\",\"4\",\"5\",\"8\",\"136\",\"123\",\"68\",\"100\"],\"levels\":[-1,0,1,2,3],\"categories\":null,\"mustWords\":[\"护士 失联\"],\"mustNotWords\":null,\"shouldWords\":null,\"matchWords\":null,\"fromTime\":null,\"toTime\":null,\"repost\":null,\"region\":3,\"pageNum\":1,\"pageSize\":40,\"sort\":{\"field\":\"pubTime\",\"asc\":false}}";
		String text1 = "{\"indexes\":[\"404\"],\"types\":[\"3\",\"6\",\"2\",\"7\",\"4\",\"5\",\"8\",\"136\",\"123\",\"68\",\"100\"],\"levels\":[-1,0,1,2,3],\"categories\":null,\"mustWords\":[\"护士 失联\"],\"mustNotWords\":null,\"shouldWords\":null,\"matchWords\":null,\"fromTime\":null,\"toTime\":null,\"repost\":null,\"region\":3,\"pageNum\":1,\"pageSize\":40,\"sort\":{\"field\":\"pubTime\",\"asc\":false}}";
		String url = "http://120.26.230.234:8080/ips-index/es/search?format=json";
		Map<String, String> params = new HashMap();
		params.put("query", text);
		data = httpService.post(url, params);
		if (data != null) {
			System.out.println(data.getContentStr());
		}
		params.put("query", text1);
		data = httpService.post(url, params);
		if (data != null) {
			System.out.println(data.getContentStr());
		}
	}
}