package com.batman.matchman.utils;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.Args;
import org.apache.http.util.ByteArrayBuffer;

import com.ipscg.framework.util.DomainUtil;
import com.ipscg.framework.util.http.Encoding;
import com.ipscg.framework.util.http.SinoDetect;

public class HttpHandle {
	private static final Log LOG = LogFactory.getLog(HttpHandle.class);
	private static SinoDetect sinoDetect = SinoDetect.getInstance();
	static Map<String, Long> HOST_TIME_MAP = new HashMap();
	static final String CHARSET = "utf-8";
	HttpClient httpClient = null;
	BasicCookieStore basicCookieStore = null;
	private String cookieString = null;
	private boolean needDelay = true;
	private int minDelayTime = 0;
	private int maxDelayTime = 0;
	static List<Delay> DELAY_HOST_SEGMENTS = new ArrayList();

	static {
		DELAY_HOST_SEGMENTS.add(new Delay("getappmsgext", 9000, 6000));
		DELAY_HOST_SEGMENTS.add(new Delay("weixin.sogou.com", 5000, 3000));
		DELAY_HOST_SEGMENTS.add(new Delay("weixin.qq.com", 7000, 4000));
		DELAY_HOST_SEGMENTS.add(new Delay("m.taobao.com", 1800, 1200));
		DELAY_HOST_SEGMENTS.add(new Delay("s.taobao.com/list", 5000, 3000));
		DELAY_HOST_SEGMENTS.add(new Delay("taobao.com", 3000, 1000));
		DELAY_HOST_SEGMENTS.add(new Delay("jd.com", 3000, 1000));
		DELAY_HOST_SEGMENTS.add(new Delay("google.com", 120000, 60000));
		DELAY_HOST_SEGMENTS.add(new Delay("suning.com", 10000, 5000));
		DELAY_HOST_SEGMENTS.add(new Delay("yhd.com", 6000, 3000));
		DELAY_HOST_SEGMENTS.add(new Delay("winxuan.com", 10000, 5000));
		DELAY_HOST_SEGMENTS.add(new Delay("amazon", 10000, 5000));
		DELAY_HOST_SEGMENTS.add(new Delay("openlaw.cn", 6000, 4000));
		DELAY_HOST_SEGMENTS.add(new Delay("itslaw.com", 10000, 4000));
		DELAY_HOST_SEGMENTS.add(new Delay("vip.com", 10000, 5000));
		DELAY_HOST_SEGMENTS.add(new Delay("jumei.com", 10000, 5000));
		DELAY_HOST_SEGMENTS.add(new Delay("gome.com", 10000, 5000));
		DELAY_HOST_SEGMENTS.add(new Delay("waimai.meituan.com/comment", 4000, 2000));
		DELAY_HOST_SEGMENTS.add(new Delay("waimai.meituan.com/restaurant", 4000, 2000));
		DELAY_HOST_SEGMENTS.add(new Delay("meituan.com", 10000, 5000));
		DELAY_HOST_SEGMENTS.add(new Delay("nuomi.com", 10000, 5000));
		DELAY_HOST_SEGMENTS.add(new Delay("dangdang.com", 10000, 5000));
		DELAY_HOST_SEGMENTS.add(new Delay("dianping.com", 5000, 2000));
		DELAY_HOST_SEGMENTS.add(new Delay("ctrip.com", 12000, 8000));
		DELAY_HOST_SEGMENTS.add(new Delay("tuniu.com", 12000, 8000));
		DELAY_HOST_SEGMENTS.add(new Delay("qunar.com", 8000, 5000));
		DELAY_HOST_SEGMENTS.add(new Delay("p.3.cn", 10000, 5000));
		DELAY_HOST_SEGMENTS.add(new Delay("weibo", 10000, 1000));
		DELAY_HOST_SEGMENTS.add(new Delay("104.195.11.242", 40000, 20000));
		DELAY_HOST_SEGMENTS.add(new Delay("104.222.229.66", 40000, 20000));
		DELAY_HOST_SEGMENTS.add(new Delay("125.64.91.82", 40000, 20000));
		DELAY_HOST_SEGMENTS.add(new Delay("chinaso.com", 10000, 5000));
		DELAY_HOST_SEGMENTS.add(new Delay("zhongsou.com", 10000, 5000));
		DELAY_HOST_SEGMENTS.add(new Delay("so.com", 5000, 1000));
		DELAY_HOST_SEGMENTS.add(new Delay("wyq.sina.com", 12000, 7000));
		DELAY_HOST_SEGMENTS.add(new Delay("1688.com", 12000, 8000));
	}

	static Delay DEFAULT_DELAY = new Delay("*", 1000, 100);

	public HttpHandle(boolean needDelay, Map<String, String> headers, int minDelayTime, int maxDelayTime,
			int connectTimeout) {
		this.needDelay = needDelay;
		this.minDelayTime = minDelayTime;
		this.maxDelayTime = maxDelayTime;

		RequestConfig config = RequestConfig.custom().setSocketTimeout(connectTimeout).setConnectTimeout(connectTimeout)
				.setConnectionRequestTimeout(connectTimeout).setRedirectsEnabled(false).setCookieSpec("standard")
				.build();

		basicCookieStore = new BasicCookieStore();
		if (headers == null) {
			httpClient = HttpClients.custom()
					.setUserAgent("Mozilla/5.0 (Windows NT 6.3; WOW64; rv:41.0) Gecko/20100101 Firefox/41.0")
					.setDefaultRequestConfig(config).setDefaultCookieStore(basicCookieStore).build();
		} else {
			Set<Header> set = new HashSet();
			for (String key : headers.keySet()) {
				Header header = new BasicHeader(key, (String) headers.get(key));
				set.add(header);
			}
			httpClient = HttpClients.custom().setDefaultHeaders(set)
					.setUserAgent("Mozilla/5.0 (Windows NT 6.3; WOW64; rv:41.0) Gecko/20100101 Firefox/41.0")
					.setDefaultRequestConfig(config).setDefaultCookieStore(basicCookieStore).build();
		}
	}
	
	

	public HttpHandle(boolean isHttps, String digitalCertificatePath, String digitalCertificatePsw, boolean needDelay,
			int minDelayTime, int maxDelayTime, int connectTimeout) {
		this.needDelay = needDelay;
		this.minDelayTime = minDelayTime;
		this.maxDelayTime = maxDelayTime;

		httpClient = createSSLClientDefault(connectTimeout);
	}

	public HttpData get(String url, String host, int port, Map<String, String> heads, String charset,
			boolean needCookie, boolean specialEncode, int tryTimes) throws ClientProtocolException, IOException {
		delayJudge(url, null);
		url = encodeURL(url, charset);
		if (specialEncode) {
			url = encode(url, charset);
		}
		HttpGet request = new HttpGet(url);
		if (needCookie) {
			if (cookieString != null) {
				request.addHeader("Cookie", cookieString);
			} else {
				request.addHeader("Cookie", getCookies());
			}
		}
		RequestConfig config;
		if (host != null) {
			HttpHost proxy = new HttpHost(host, port, "http");

			config = RequestConfig.custom().setProxy(proxy).build();
			request.setConfig(config);
		}
		if (null != heads) {
			Set<String> keySet = heads.keySet();
			for (String key : keySet) {
				request.addHeader(key, (String) heads.get(key));
			}
		}
		HttpResponse response = httpClient.execute(request);
		Header[] headers = response.getAllHeaders();
		String movedUrl = null;
		int status = response.getStatusLine().getStatusCode();
		if (((status == 302) || (status == 301) || (status == 303) || (status == 307)) && (tryTimes < 1)) {
			Header header = response.getFirstHeader("location");
			if (header != null) {
				movedUrl = DomainUtil.getAbsoluteURL(header.getValue(), url);
				request.releaseConnection();
				tryTimes += 1;
				return get(movedUrl, host, port, heads, charset, needCookie, specialEncode, tryTimes);
			}
		}
		HttpEntity entity = response.getEntity();
		byte[] bytes = null;
		try {
			bytes = entity != null ? toByteArray(entity) : null;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		if (charset == null) {
			if (bytes == null) {
				charset = "utf-8";
			} else {
				charset = Encoding.htmlname[sinoDetect.detectEncoding(bytes)];
			}
		}
		request.releaseConnection();
		if (bytes == null) {
			return null;
		}
		HttpData httpData = new HttpData(bytes, charset, url);
		httpData.setHeaders(headers);
		return httpData;
	}

	private void delayJudge(String url, Map<String, String> params) {
		String host = DomainUtil.getSecondLevelDomain(url);
		Random random = new Random();
		Delay _delay = DEFAULT_DELAY;
		if (needDelay) {
			for (Delay delay : DELAY_HOST_SEGMENTS) {
				if (url.contains(delay.getHostSegment())) {
					_delay = delay;
					break;
				}
			}
			synchronized (_delay) {
				int minDelay = _delay.getMinDelay();
				int maxDelay = _delay.getMaxDelay();
				if (minDelay < minDelayTime) {
					minDelay = minDelayTime;
				}
				if (maxDelay < maxDelayTime) {
					maxDelay = maxDelayTime;
				}
				Long time = (Long) HOST_TIME_MAP.get(host);
				if (time != null) {
					long currentTime = System.currentTimeMillis();
					if (currentTime - time.longValue() < minDelay) {
						try {
							Thread.sleep(

									random.nextInt(maxDelay) % (maxDelay - minDelay + 1) + minDelay - currentTime
											+ time.longValue());
						} catch (InterruptedException e) {
							LOG.error(e.getMessage(), e);
						}
					}
				}
				HOST_TIME_MAP.put(host, Long.valueOf(System.currentTimeMillis()));
			}
		}
	}

	static class Delay {
		String hostSegment;
		int maxDelay;
		int minDelay;

		public Delay(String hostSegment, int maxDelay, int minDelay) {
			this.hostSegment = hostSegment;
			this.maxDelay = maxDelay;
			this.minDelay = minDelay;
		}

		public String getHostSegment() {
			return hostSegment;
		}

		public void setHostSegment(String hostSegment) {
			this.hostSegment = hostSegment;
		}

		public int getMaxDelay() {
			return maxDelay;
		}

		public void setMaxDelay(int maxDelay) {
			this.maxDelay = maxDelay;
		}

		public int getMinDelay() {
			return minDelay;
		}

		public void setMinDelay(int minDelay) {
			this.minDelay = minDelay;
		}
	}

	private String encodeURL(String url, String charset) {
		if (charset == null) {
			charset = "utf-8";
		}
		StringBuffer encodeURL = new StringBuffer();
		try {
			Pattern pattern = Pattern.compile("[　{}\\s一-龥]");
			Matcher matcher = pattern.matcher(url);
			while (matcher.find()) {
				matcher.appendReplacement(encodeURL, URLEncoder.encode(matcher.group(), charset));
			}
			matcher.appendTail(encodeURL);
		} catch (UnsupportedEncodingException e) {
			LOG.error(e.getMessage(), e);
		}
		return encodeURL.toString();
	}

	private static String encode(String url, String charset) {
		if (charset == null) {
			charset = "utf-8";
		}
		StringBuffer b = new StringBuffer();
		String nonUrlPattern = "[^:/\\-_\\?=%&]";
		try {
			Pattern p = Pattern.compile(nonUrlPattern);
			Matcher m = p.matcher(url);
			while (m.find()) {
				m.appendReplacement(b, URLEncoder.encode(m.group(0), charset));
			}
			m.appendTail(b);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return b.toString();
	}

	public HttpData post(String url, Map<String, String> params, String host, int port, Map<String, String> heads,
			String charset, boolean specialEncode) throws ClientProtocolException, IOException {
		delayJudge(url, params);
		url = encodeURL(url, charset);
		if (specialEncode) {
			url = encode(url, charset);
		}
		HttpPost request = postForm(url, params, charset);
		RequestConfig config;
		if (host != null) {
			HttpHost proxy = new HttpHost(host, port, "http");

			config = RequestConfig.custom().setProxy(proxy).build();
			request.setConfig(config);
		}
		if (null != heads) {
			Set<String> keySet = heads.keySet();
			for (String key : keySet) {
				request.addHeader(key, (String) heads.get(key));
			}
		}
		HttpResponse response = httpClient.execute(request);
		Header[] headers = response.getAllHeaders();
		int status = response.getStatusLine().getStatusCode();
		if ((status == 302) || (status == 301) || (status == 303) || (status == 307)) {
			Header header = response.getFirstHeader("location");
			if (header != null) {
				String movedUrl = DomainUtil.getAbsoluteURL(header.getValue(), url);

				request.releaseConnection();
				return get(movedUrl, host, port, heads, charset, true, specialEncode, 1);
			}
			return null;
		}
		if (status == 200) {
			HttpEntity entity = response.getEntity();
			byte[] bytes = null;
			try {
				bytes = entity != null ? toByteArray(entity) : null;
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
				e.printStackTrace();
			}
			if (charset == null) {
				if (bytes == null) {
					charset = "utf-8";
				} else {
					charset = Encoding.htmlname[sinoDetect.detectEncoding(bytes)];
				}
			}
			request.releaseConnection();
			if (bytes == null) {
				return null;
			}
			HttpData httpData = new HttpData(bytes, charset, url);
			httpData.setHeaders(headers);
			return httpData;
		}else if(status==500){
			LOG.error("目标服务器报错,我也是醉了");
			
		}
		return null;
	}

	public HttpData postJson(String url, String json, String host, int port, Map<String, String> heads,
			String charset, boolean specialEncode) throws ClientProtocolException, IOException {
		url = encodeURL(url, charset);
		if (specialEncode) {
			url = encode(url, charset);
		}
        HttpPost request = new HttpPost(url);  

		if (null != heads) {
			Set<String> keySet = heads.keySet();
			for (String key : keySet) {
				request.addHeader(key, (String) heads.get(key));
			}
		}
        StringEntity stringEntity = new StringEntity(json,"UTF-8");//解决中文乱码问题  
        stringEntity.setContentEncoding("UTF-8");  
        stringEntity.setContentType("application/json");  
        request.setEntity(stringEntity);
		HttpResponse response = httpClient.execute(request);
		
		Header[] headers = response.getAllHeaders();
		int status = response.getStatusLine().getStatusCode();
		if ((status == 302) || (status == 301) || (status == 303) || (status == 307)) {
			Header header = response.getFirstHeader("location");
			if (header != null) {
				String movedUrl = DomainUtil.getAbsoluteURL(header.getValue(), url);

				request.releaseConnection();
				return get(movedUrl, host, port, heads, charset, true, specialEncode, 1);
			}
			return null;
		}
		if (status == 200) {
			HttpEntity entity = response.getEntity();
			byte[] bytes = null;
			try {
				bytes = entity != null ? toByteArray(entity) : null;
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
				e.printStackTrace();
			}
			if (charset == null) {
				if (bytes == null) {
					charset = "utf-8";
				} else {
					charset = Encoding.htmlname[sinoDetect.detectEncoding(bytes)];
				}
			}
			request.releaseConnection();
			if (bytes == null) {
				return null;
			}
			HttpData httpData = new HttpData(bytes, charset, url);
			httpData.setHeaders(headers);
			return httpData;
		}else if(status==500){
			LOG.error("目标服务器报错,我也是醉了");
			
		}
		return null;
	}
	
	private HttpPost postForm(String url, Map<String, String> params, String charset) {
		HttpPost httPost = new HttpPost(url);

		List<NameValuePair> nvps = new ArrayList();

		Set<String> keySet = params.keySet();
		for (String key : keySet) {
			nvps.add(new BasicNameValuePair(key, (String) params.get(key)));
		}
		if (charset == null) {
			httPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		} else {
			httPost.setEntity(new UrlEncodedFormEntity(nvps, Charset.forName(charset)));
		}
		return httPost;
	}

	public String getCookies() {
		String tmpcookies = "";
		List<Cookie> cookieList = basicCookieStore.getCookies();
		for (int i = 0; i < cookieList.size(); i++) {
			Cookie cookie = (Cookie) cookieList.get(i);

			tmpcookies = tmpcookies + cookie.getName() + "=" + cookie.getValue() + ";";
		}
		return tmpcookies;
	}

	public CloseableHttpClient createSSLClientDefault(int connectTimeout) {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);

			RequestConfig config = RequestConfig.custom().setSocketTimeout(connectTimeout)
					.setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectTimeout)
					.setRedirectsEnabled(false).setCookieSpec(CookieSpecs.STANDARD).build();
			basicCookieStore = new BasicCookieStore();

			return HttpClients.custom()
					.setUserAgent("Mozilla/5.0 (Windows NT 6.3; WOW64; rv:41.0) Gecko/20100101 Firefox/41.0")
					.setDefaultRequestConfig(config).setDefaultCookieStore(basicCookieStore).setSSLSocketFactory(sslsf)
					.build();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return HttpClients.createDefault();
	}

	public void setCookieString(String cookieString) {
		this.cookieString = cookieString;
	}

	private byte[] toByteArray(HttpEntity entity) throws IOException {
		Args.notNull(entity, "Entity");
		if (entity.getContentLength() >= 5242880L) {
			throw new IllegalArgumentException("HTTP entity too large to be buffered in memory");
		}
		InputStream instream = entity.getContent();
		ByteArrayBuffer buffer = null;
		if (instream == null) {
			return null;
		}
		try {
			int i = (int) entity.getContentLength();
			if (i < 0) {
				i = 4096;
			}
			buffer = new ByteArrayBuffer(i);
			byte[] tmp = new byte['က'];
			int l;
			while ((l = instream.read(tmp)) != -1) {
				buffer.append(tmp, 0, l);
			}
		} catch (EOFException eofe) {
			if ((instream.available() == 0) && ((eofe.getMessage() == null)
					|| (eofe.getMessage().equals("Unexpected end of ZLIB input stream")))) {
				eofe.fillInStackTrace();
			} else {
				throw eofe;
			}
		} finally {
			instream.close();
		}
		return buffer.toByteArray();
	}
}