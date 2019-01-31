package cn.edu.fjnu.towide.xxx.deptManagement.controller;

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
public class DeptManagementController {
	@Autowired
	DeptManagementAppVerNoDispatcher deptManagementAppVerNoDispatcher;
	@Autowired
	DataCenterService dataCenterService;

	@RequestMapping("/dept")
	public Object home(HttpServletRequest request,HttpServletResponse response){
		JSONObject requestParamJson=(JSONObject) request.getAttribute("requestParamJson");
		// 初始化返回信息，包括头部head和数据部data，存放在responseData中
		ResponseData responseData=ResponseDataUtil.createResponseData(request,response);
		dataCenterService.init(requestParamJson, responseData);
		// requestParamJson,responseData是否有存在的必要
        deptManagementAppVerNoDispatcher.dispatchByAppVerNo(requestParamJson,responseData);
		return responseData;
	}
	   
	@ExceptionHandler(RequestFailureException.class)
	public Object handleException(RequestFailureException requestFailureException){
		
		ResponseData responseData=requestFailureException.getResponseData();
		return responseData;
		
	}
}
