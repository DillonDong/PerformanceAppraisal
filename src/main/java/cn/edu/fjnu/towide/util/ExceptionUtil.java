/**  
 * @Title: ExceptionUtil.java  
 * @Package cn.edu.fjnu.towide.utils
 * @author CaoZhengxi  
 * @date 2018年7月24日  
 */  
package cn.edu.fjnu.towide.util;

import javax.annotation.PostConstruct;

import cn.edu.fjnu.towide.enums.ReasonOfFailure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.edu.fjnu.towide.entity.ResponseData;
import cn.edu.fjnu.towide.enums.IReasonOfFailure;
import cn.edu.fjnu.towide.enums.ReasonOfFailure;
import cn.edu.fjnu.towide.exception.RequestFailureException;
import cn.edu.fjnu.towide.service.DataCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ExceptionUtil {
	
	@Autowired
	private DataCenterService dataCenter;
	
	private static DataCenterService dataCenterService;
	
	
	@PostConstruct
	public void init() {
		dataCenterService=dataCenter;
	}
	
	
	/**  
	 * 抛出自定义异常原因
	 */  
	public  static RequestFailureException setFailureMsgAndThrow( IReasonOfFailure reasonOfFailure) {
		ResponseData responseData=dataCenterService.getData("responseData");
		RequestFailureException requestFailureException=new RequestFailureException();
		requestFailureException.setResponseData(responseData);
		
		ResponseDataUtil.setResponseDataWithFailureInfo(responseData, reasonOfFailure);
		throw requestFailureException;
	}

	
	/**
	 * 抛出默认参数有误
	 */
	public static void throwRequestFailureException() {
		ResponseData responseData = dataCenterService.getData("responseData");
		RequestFailureException requestFailureException = new RequestFailureException();
		requestFailureException.setResponseData(responseData);
		ResponseDataUtil.setResponseDataWithFailureInfo(responseData,
				ReasonOfFailure.THE_PARAMETERS_SUBMITTED_ARE_INCORRECT);
		throw requestFailureException;
	}
	
	
	
}
