package com.openjad.common.util.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

import com.openjad.common.exception.BizException;
import com.openjad.logger.api.Logger;
import com.openjad.logger.api.LoggerFactory;

public class HttpUtils {

	private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

	private static final String CTYPE_FORM = "application/x-www-form-urlencoded;charset=utf-8";
	private static final String CTYPE_JSON = "application/json; charset=utf-8";
	private static final String charset = "utf-8";

	private int CONNECT_TIMEOUT = 15000;
	private int READ_TIMEOUT = 15000;

	private static HttpUtils instance = null;

	public static HttpUtils getInstance() {
		if (instance == null) {
			return new HttpUtils();
		}
		return instance;
	}

	public static void main(String[] args) throws SocketTimeoutException, IOException {
		String resp = getInstance().postJson("http://localhost:8080/test/test", "{\"custCmonId\":\"12345678\",\"custNo\":\"111\",\"custNo111\":\"706923\"}");
		System.out.println(resp);
	}

	/**
	 * 以application/json; charset=utf-8方式传输
	 * 
	 * @param url url
	 * @param jsonContent jsonContent
	 * @return res
	 */
	public String postJson(String url, String jsonContent) {
		try {
			return doRequest("POST", url, jsonContent, CONNECT_TIMEOUT, READ_TIMEOUT, CTYPE_JSON, null);
		} catch (Exception e) {
			logger.error(e.getMessage() + ",url:" + url + ",jsonContent:" + jsonContent, e);
			throw new BizException(e);
		}
	}

	/**
	 * POST 以application/x-www-form-urlencoded;charset=utf-8方式传输
	 * 
	 * @param url url
	 * @return res
	 */
	public String postForm(String url) {
		try {
			return doRequest("POST", url, "", CONNECT_TIMEOUT, READ_TIMEOUT, CTYPE_FORM, null);
		} catch (Exception e) {
			logger.error(e.getMessage() + ",url:" + url, e);
			throw new BizException(e);
		}
	}

	/**
	 * POST 以application/x-www-form-urlencoded;charset=utf-8方式传输
	 * 
	 * @param url url
	 * @param params params
	 * @return res 
	 */
	public String postForm(String url, Map<String, String> params) {
		try {
			return doRequest("POST", url, buildQuery(params), CONNECT_TIMEOUT, READ_TIMEOUT, CTYPE_FORM, null);
		} catch (Exception e) {
			logger.error(e.getMessage() + ",url:" + url, e);
			throw new BizException(e);
		}
	}

	/**
	 * POST 以application/x-www-form-urlencoded;charset=utf-8方式传输
	 * 
	 * @param url url 
	 * @return res 
	 */
	public String getForm(String url) {
		try {
			return doRequest("GET", url, "", CONNECT_TIMEOUT, READ_TIMEOUT, CTYPE_FORM, null);
		} catch (Exception e) {
			logger.error(e.getMessage() + ",url:" + url, e);
			throw new BizException(e);
		}
	}

	/**
	 * POST 以application/x-www-form-urlencoded;charset=utf-8方式传输
	 * 
	 * @param url url
	 * @param params params
	 * @return res
	 */
	public String getForm(String url, Map<String, String> params) {
		try {
			return doRequest("GET", url, buildQuery(params), CONNECT_TIMEOUT, READ_TIMEOUT, CTYPE_FORM, null);
		} catch (Exception e) {
			logger.error(e.getMessage() + ",url:" + url, e);
			throw new BizException(e);
		}
	}

	/**
	 * 
	 * <p>
	 * 
	 * @Description:
	 *               </p>
	 * 
	 * @param method         请求的method post/get
	 * @param url            请求url
	 * @param requestContent 请求参数
	 * @param connectTimeout 请求超时
	 * @param readTimeout    响应超时
	 * @param ctype          请求格式 xml/json等等
	 * @param headerMap      请求header中要封装的参数
	 * @return res
	 * @throws SocketTimeoutException e
	 * @throws IOException e
	 */
	private String doRequest(String method, String url, String requestContent,
			int connectTimeout, int readTimeout, String ctype,
			Map<String, String> headerMap) throws SocketTimeoutException,
			IOException {
		HttpURLConnection conn = null;
		OutputStream out = null;
		String rsp = null;
		try {
			conn = getConnection(new URL(url), method, ctype, headerMap);
			conn.setConnectTimeout(connectTimeout);
			conn.setReadTimeout(readTimeout);

			if (requestContent != null && requestContent.trim().length() > 0) {
				out = conn.getOutputStream();
				out.write(requestContent.getBytes(charset));
			}

			rsp = getResponseAsString(conn);
		} finally {
			if (out != null) {
				out.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
			conn = null;
		}
		return rsp;
	}

	private HttpURLConnection getConnection(URL url, String method, String ctype, Map<String, String> headerMap) throws IOException {
		HttpURLConnection conn;
		conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(method);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestProperty("Accept", "text/xml,text/javascript,text/html,application/json");
		conn.setRequestProperty("Content-Type", ctype);
		if (headerMap != null) {
			for (Map.Entry<String, String> entry : headerMap.entrySet()) {
				conn.setRequestProperty(entry.getKey(), entry.getValue());
			}
		}
		return conn;
	}

	private String getResponseAsString(HttpURLConnection conn)
			throws IOException {
		InputStream es = conn.getErrorStream();
		if (es == null) {
			return getStreamAsString(conn.getInputStream(), charset, conn);
		} else {
			String msg = getStreamAsString(es, charset, conn);
			if (msg != null && msg.trim().length() > 0) {
				throw new IOException(conn.getResponseCode() + ":" + conn.getResponseMessage());
			} else {
				return msg;
			}
		}
	}

	private String getStreamAsString(InputStream stream, String charset,
			HttpURLConnection conn) throws IOException {
		try {
			Reader reader = new InputStreamReader(stream, charset);

			StringBuilder response = new StringBuilder();
			final char[] buff = new char[1024];
			int read = 0;
			while ((read = reader.read(buff)) > 0) {
				response.append(buff, 0, read);
			}

			return response.toString();
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
	}

	private String buildQuery(Map<String, String> params) throws IOException {
		if (params == null || params.isEmpty()) {
			return "";
		}

		StringBuilder query = new StringBuilder();
		Set<Map.Entry<String, String>> entries = params.entrySet();
		boolean hasParam = false;

		for (Map.Entry<String, String> entry : entries) {
			String name = entry.getKey();
			String value = entry.getValue();
			if (hasParam) {
				query.append("&");
			} else {
				hasParam = true;
			}
			query.append(name).append("=").append(URLEncoder.encode(value, charset));
		}
		return query.toString();
	}

}
