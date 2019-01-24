package cn.edu.fjnu.towide.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.net.URI;

public class CommonUtil {

	public static void addKeyNameAndValueToHeadOfResponseData(String name, String value, JSONObject responseData) {
		JSONObject head=(JSONObject) responseData.get("head");
		if (head==null) {
			return;
		}
		head.put(name, value);
	}
	
	
	/**  
	 *  密码加密处理
	 * @param nativePassword 原生密码
	 * @return encodePassword 加密处理的密码
	 */  
	public static String passwordEncodeByBCrypt(String nativePassword) {
		 // 密码加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodePassword = passwordEncoder.encode(nativePassword);	
		return encodePassword;
	}


	/**
	 *  向第三方发送get请求获取返回数据
	 * @return : 返回String数据
	 */
	public static String sendGetRequest(String url) {
		String returnJson = null;
		try {

			// String string = System.setProperty("[图片]javax.net .ssl.trustStore",
			// "C:\\server-keystore\\tomcat.truststore");
			ClientHttpRequest request;
			request = new SimpleClientHttpRequestFactory().createRequest(new URI(url), HttpMethod.GET);
			// 设置请求头
			// request.getHeaders().setContentType(MediaType.APPLICATION_FORM_URLENCODED);

			request.getBody().flush();
			// 发送请求
			ClientHttpResponse response = request.execute();
			InputStream inputStream = response.getBody();
			byte[] bs = new byte[inputStream.available()];
			inputStream.read(bs);
			returnJson = new String(bs);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnJson;
	}


	/**
	 *  发送jsonData
	 * @return String
	 */
	public String sendJsonData(String jsonData, String url) throws Exception {
		// String string = System.setProperty("[图片]javax.net .ssl.trustStore",
		// "C:\\server-keystore\\tomcat.truststore");
		ClientHttpRequest request;
		request = new SimpleClientHttpRequestFactory().createRequest(new URI(url), HttpMethod.POST);
		// 设置请求头
		request.getHeaders().setContentType(MediaType.APPLICATION_JSON);
		String requestStr = jsonData;
		request.getBody().write(requestStr.getBytes("utf-8"));
		request.getBody().flush();
		// 发送请求
		ClientHttpResponse response = request.execute();
		InputStream inputStream = response.getBody();
		byte[] bs = new byte[inputStream.available()];
		inputStream.read(bs);
		String returnJson = new String(bs);

		return returnJson;
	}


	/**
	 *  发送表单数据
	 */
	public String sendFormData(String url, MultiValueMap<String, String> params) {
		RestTemplate client = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);// 表单提交方式
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params,
				headers);
		// 执行HTTP请求
		ResponseEntity<String> responseEntity = client.exchange(url, HttpMethod.POST, requestEntity, String.class);
		return responseEntity.getBody();
	}

}
