package cn.edu.fjnu.towide.xxx.fileupload.controller;

import cn.edu.fjnu.towide.entity.ResponseData;
import cn.edu.fjnu.towide.exception.RequestFailureException;
import cn.edu.fjnu.towide.service.DataCenterService;
import cn.edu.fjnu.towide.util.ResponseDataUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
public class FileUploadController {
	@Autowired
	FileUploadAppVerNoDispatcher fileUploadAppVerNoDispatcher;
	@Autowired
    DataCenterService dataCenterService;
	
	@RequestMapping("/fileUpload" )
	public Object home(HttpServletRequest request,HttpServletResponse response){
		JSONObject requestParamJson=(JSONObject) request.getAttribute("requestParamJson");
		ResponseData responseData= ResponseDataUtil.createResponseData(request,response);
		dataCenterService.init(requestParamJson, responseData);
		fileUploadAppVerNoDispatcher.dispatchByAppVerNo(requestParamJson,responseData);
		return responseData;
	}

	@ExceptionHandler(RequestFailureException.class)
	public Object handleException(RequestFailureException requestFailureException){
		
		ResponseData responseData=requestFailureException.getResponseData();
		return responseData;
		
	}
}
