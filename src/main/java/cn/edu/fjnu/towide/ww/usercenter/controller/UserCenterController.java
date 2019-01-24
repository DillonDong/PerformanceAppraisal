package cn.edu.fjnu.towide.ww.usercenter.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.edu.fjnu.towide.entity.ResponseData;
import cn.edu.fjnu.towide.exception.RequestFailureException;
import cn.edu.fjnu.towide.service.DataCenterService;
import cn.edu.fjnu.towide.util.ResponseDataUtil;

@CrossOrigin
@RestController
public class UserCenterController {
	@Autowired
	UserCenterAppVerNoDispatcher loginAppVerNoDispatcher;
	@Autowired
	DataCenterService dataCenterService;

	@RequestMapping("/UserCenter")
	public Object home(HttpServletRequest request,HttpServletResponse response){
		JSONObject requestParamJson=(JSONObject) request.getAttribute("requestParamJson");
		// 初始化返回信息，包括头部head和数据部data，存放在responseData中
		ResponseData responseData=ResponseDataUtil.createResponseData(request,response);
		dataCenterService.init(requestParamJson, responseData);
		// requestParamJson,responseData是否有存在的必要
		loginAppVerNoDispatcher.dispatchByAppVerNo(requestParamJson,responseData);
		return responseData;
	}
	   
	@ExceptionHandler(RequestFailureException.class)
	public Object handleException(RequestFailureException requestFailureException){
		
		ResponseData responseData=requestFailureException.getResponseData();

//		String processResult=EventProcessingLog.PROCESS_RESULT_FAILURE_FOR_THE_PARAMETER_FAILED_THE_VALIDITY_CHECK;
		////		String processResultDescription=EventProcessingLog.PROCESS_RESULT_DESCRIPTION_FAILURE_FOR_THE_PARAMETER_FAILED_THE_VALIDITY_CHECK;
		//
		////		messageCenterDataCenterService.setProcessResult(processResult);
		////		messageCenterDataCenterService.setProcessResultDescription(processResultDescription);
		//
		//		//存储日志。。。
		
		return responseData;
		
	}
}
