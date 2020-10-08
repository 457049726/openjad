package com.openjad.common.util.http;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.openjad.common.exception.BizException;
import com.openjad.logger.api.Logger;
import com.openjad.logger.api.LoggerFactory;


public class HttpsUtils {
	
	private static final Logger logger= LoggerFactory.getLogger(HttpsUtils.class);
	
	public static String METHOD_GET = "GET";
	public static String METHOD_POST = "POST";

	public static int DEF_CONNECT_TIMEOUT = 2 * 1000;
	public static int DEF_READ_TIMEOUT = 8 * 1000;
	public static Charset DEF_CHARSET = Charset.forName("UTF-8");

	public static String CHARSET_DEF = DEF_CHARSET.name();
	private static String CHARSET_STR = "charset=";
	private static int CHARSET_STR_LEN = CHARSET_STR.length();

	
//	public static void main(String[] args) {
//		JSONObject x = HttpsUtils.doGetAuthorization("https://amzrealtime.despatchcloud.co.uk/ws/v1/wsfulfilment/list_fulfilment_clients");
//		System.out.println(x);
//	}
//
//	public static JSONObject doGetAuthorization(String url) {
//		Map<String, String> headers = new HashMap<>();
//		headers.put("Authorization", "DC 454");
//		String xx = HttpsUtils.Get(url, headers);
//		return JSONObject.parseObject(xx);
//	}

	public static TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return new java.security.cert.X509Certificate[] {};
		}

		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}
	} };

	public static void trustAll() {
		try {
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new BizException(e);
		}
	}

	static {
		trustAll();
	}

	public static String get(String urlString) {
		return httpsGo(urlString, METHOD_GET, null, null, DEF_CONNECT_TIMEOUT, DEF_READ_TIMEOUT);
	}

	public static String get(String urlString, Map<String, String> headers) {
		return httpsGo(urlString, METHOD_GET, headers, null, DEF_CONNECT_TIMEOUT, DEF_READ_TIMEOUT);
	}

	public static String get(String urlString, Map<String, String> headers, Map<String, String> params) {
		if (params != null && params.isEmpty() == false) {
			StringBuffer url = new StringBuffer(urlString);
			try {
				boolean isFirst = true;
				if (urlString.contains("?")) {
					if (urlString.endsWith("&") == false && urlString.contains("&")) {
						isFirst = false;
					}
				} else {
					url.append('?');
				}
				String paramsEncoding = DEF_CHARSET.name();
				for (Map.Entry<String, String> entry : params.entrySet()) {
					if (isFirst)
						isFirst = false;
					else
						url.append('&');
					url.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
					url.append('=');
					url.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
				}
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
				throw new BizException(e);
			}
			return get(url.toString(), headers);
		} else {
			return get(urlString, headers);
		}
	}

	public static String post(String urlString, String contentType, byte[] content) {
		Map<String, String> headers = new HashMap<String, String>(1);
		headers.put("Content-Type", contentType);
		return httpsGo(urlString, METHOD_POST, headers, content, DEF_CONNECT_TIMEOUT, DEF_READ_TIMEOUT);
	}

	public static String formPost(String urlString, String content) {
		Map<String, String> headers = new HashMap<String, String>(1);
		headers.put("Content-Type", String.format("application/x-www-form-urlencoded; charset=%s", DEF_CHARSET.name()));
		return httpsGo(urlString, METHOD_POST, null, content.getBytes(DEF_CHARSET), DEF_CONNECT_TIMEOUT, DEF_READ_TIMEOUT);
	}

	public static String xmlPost(String urlString, String content) {
		Map<String, String> headers = new HashMap<String, String>(1);
		headers.put("Content-Type", String.format("text/html; charset=%s", DEF_CHARSET.name()));
		return httpsGo(urlString, METHOD_POST, headers, content.getBytes(DEF_CHARSET), DEF_CONNECT_TIMEOUT, DEF_READ_TIMEOUT);
	}

//	public static String JsonPost(String urlString, Object content) {
//		return JsonPost(urlString, JSONObject.toJSONString(content, SerializerFeature.DisableCircularReferenceDetect));
//	}

	public static String jsonPost(String urlString, String content) {
		Map<String, String> headers = new HashMap<String, String>(1);
		headers.put("Content-Type", String.format("application/json; charset=%s", DEF_CHARSET.name()));
		return httpsGo(urlString, METHOD_POST, headers, content.getBytes(DEF_CHARSET), DEF_CONNECT_TIMEOUT, DEF_READ_TIMEOUT);
	}

	public static String httpsGo(String urlString, String method, Map<String, String> headers, byte[] content, int connectTimeout, int readTimeout) {
		HttpsURLConnection conn = null;
		try {
			conn = (HttpsURLConnection) new URL(urlString).openConnection();

			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new SecureRandom());
			conn.setHostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}
			});
			conn.setSSLSocketFactory(sc.getSocketFactory());

			conn.setRequestMethod(method);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setConnectTimeout(connectTimeout);
			conn.setReadTimeout(readTimeout);

			if (headers != null) {
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					conn.addRequestProperty(entry.getKey(), entry.getValue());
				}
			}
			if (content != null) {
				if (headers == null || headers.containsKey("Content-Length") == false) {
					conn.addRequestProperty("Content-Length", Integer.toString(content.length));
				}
				OutputStream output = null;
				try {
					output = conn.getOutputStream();
					output.write(content);
					output.flush();
				} finally {
					if (output != null)
						try {
							output.close();
						} catch (Exception e) {
						}
				}
			}

			return readContent(conn.getResponseCode() == 200 ? conn.getInputStream() : conn.getErrorStream(), getCharset(conn));
		} catch (Exception e) {
			throw new BizException(e);
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
				
		}
	}

	public static String encodeParams(Map<String, String> params, String paramsEncoding) throws Exception {
		boolean isFirst = true;
		StringBuilder encodedParams = new StringBuilder();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			if (isFirst)
				isFirst = false;
			else
				encodedParams.append('&');
			encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
			encodedParams.append('=');
			encodedParams.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
		}
		return encodedParams.toString();
	}

	
	private static String getCharset(HttpURLConnection conn) {
		String contentType = conn.getHeaderField("Content-Type");
		int length = contentType != null ? contentType.length() : 0;
		if (length < CHARSET_STR_LEN) {
			return CHARSET_DEF;
		}
		int pos = contentType != null ? contentType.indexOf("charset=") : -1;
		if (pos < 0) {
			return CHARSET_DEF;
		}
		return contentType.substring(pos + CHARSET_STR_LEN);
	}

	private static String readContent(InputStream input, String charset) throws Exception {
		try {
			int APPEND_LEN = 4 * 1024;
			int offset = 0;
			byte[] data = new byte[APPEND_LEN];
			while (true) {
				int len = input.read(data, offset, data.length - offset);
				if (len == -1) {
					break;
				}
				offset += len;
				if (offset >= data.length) {
					data = Arrays.copyOf(data, offset + APPEND_LEN);
				}
			}
			return charset != null ? new String(data, 0, offset, charset) : new String(data, 0, offset);
		} finally {
			if (input != null)
				try {
					input.close();
				} catch (Exception e) {
				}
		}
	}
}
