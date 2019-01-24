package cn.edu.fjnu.towide.exception;

import cn.edu.fjnu.towide.entity.ResponseData;

public class RequestFailureException extends RuntimeException{
	
	private ResponseData responseData;
//	@Override
//	public String getMessage() {
//		return "请求参数格式不符合接口要求";
//		
//	}

	public ResponseData getResponseData() {
		return responseData;
	}

	public void setResponseData(ResponseData responseData) {
		this.responseData = responseData;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}
