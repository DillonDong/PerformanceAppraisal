package cn.edu.fjnu.towide.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import cn.edu.fjnu.towide.constant.FilePathNameTemplate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import cn.edu.fjnu.towide.enums.ReasonOfFailure;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

public class HttpServletRequestUtil {

	public static <T> T getParamValueFromHeadByParamName(HttpServletRequest request,String paramName) {
			JSONObject requestParamJson=(JSONObject) request.getAttribute("requestParamJson");
			if (requestParamJson==null) {
				requestParamJson=getRequestParamJsonFromRequest(request);
			}
			JSONObject head=requestParamJson.getJSONObject("head");
			@SuppressWarnings("unchecked")
			T paramValue=(T) head.get(paramName);
			return paramValue;
	}
	
	public static JSONObject getRequestParamJsonFromRequest(HttpServletRequest request) {

		String ip=getIpAddr(request);

		if (request.getParameter("isFormData")==null) {//json 格式的请求
			try {
	            BufferedReader streamReader = new BufferedReader(
	            		new InputStreamReader(request.getInputStream(), "UTF-8"));
	            StringBuilder responseStrBuilder = new StringBuilder();
	            String inputStr;  
	            while ((inputStr = streamReader.readLine()) != null)  {
					responseStrBuilder.append(inputStr);
				}
				JSONObject requestParamJson = JSONObject.parseObject(responseStrBuilder.toString());
	            requestParamJson.put("ip",ip);
				request.setAttribute("requestParamJson", requestParamJson);
				return requestParamJson;
			} catch (Exception e) {  
	        }  
	        return null;
		}
		else{	//formData格式的请求
			try{
				String appVerNo=request.getParameter("appVerNo");
				String functionNo=request.getParameter("functionNo");
				String token=request.getParameter("token");
				String suffix=request.getParameter("suffix");
				
				Part part= request.getPart("file");
				String fileName=part.getSubmittedFileName();
				
				String path=FilePathNameTemplate.uploadFileLocalPath; //本地路径
				
				String fileExtensionName;
				if(suffix!=null){
					fileExtensionName=suffix;//扩展名
				}else{
					fileExtensionName=fileName.substring(fileName.lastIndexOf(".")+1);//扩展名
				}
				
				
				
		        String uploadFileName= IdGenerator.getId()+"."+fileExtensionName;//随机文件名
		        
		        // 创建目录
		        File dir = new File(path);
		        if (!dir.exists()) {
		            dir.mkdir();
		        }
				part.write(path+uploadFileName);
				
				//封装json
				JSONObject requestParamJson=new JSONObject();
				
				Map<String, String> head=new HashMap<String, String>();
				head.put("appVerNo", appVerNo);
				head.put("functionNo", functionNo);
				head.put("token", token);
				
				Map<String, String> param=new HashMap<String, String>();
				param.put("fileName", uploadFileName);
				
				requestParamJson.put("head", head);
				requestParamJson.put("param", param);
				
				request.setAttribute("requestParamJson", requestParamJson);
		        return requestParamJson;
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	

	
	
	public static <T> T getParamValueFromParamByParamName(HttpServletRequest request, String paramName) {
		JSONObject requestParamJson=(JSONObject) request.getAttribute("requestParamJson");
		if (requestParamJson==null) {
			requestParamJson=getRequestParamJsonFromRequest(request);
		}
		JSONObject param=requestParamJson.getJSONObject("param");
		@SuppressWarnings("unchecked")
		T paramValue=(T) param.get(paramName);
		return paramValue;
	}

	/**
	 * 获取访问用户的客户端IP（适用于公网与局域网）.
	 */
	public static final String getIpAddr(final HttpServletRequest request) {
		if (request == null) {
			return "";
		}
		String ipString = request.getHeader("x-forwarded-for");
		if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
			ipString = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
			ipString = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
			ipString = request.getRemoteAddr();
		}

		// 多个路由时，取第一个非unknown的ip
		final String[] arr = ipString.split(",");
		for (final String str : arr) {
			if (!"unknown".equalsIgnoreCase(str)) {
				ipString = str;
				break;
			}
		}

		return ipString;
	}
}
