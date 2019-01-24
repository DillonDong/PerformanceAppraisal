package cn.edu.fjnu.towide.util;

import cn.edu.fjnu.towide.entity.ResponseData;
import cn.edu.fjnu.towide.entity.ResponseHead;
import cn.edu.fjnu.towide.enums.IReasonOfFailure;
import cn.edu.fjnu.towide.enums.ResponseHeadEnum;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResponseDataUtil  {

	public static void setResponseDataWithFailureInfo(ResponseData responseData,
			IReasonOfFailure reasonOfFailure) {
		ResponseHead head=responseData.getHead();
		head.setCode(ResponseHeadEnum.FAILURE.getCode());
		head.setEn_msg(reasonOfFailure.getEnMsgOfFailure());
		head.setZh_msg(reasonOfFailure.getZhMsgOfFailure());
	}

	public static ResponseData createResponseData(HttpServletRequest request, HttpServletResponse response) {
		ResponseData responseData=new ResponseData();
		String functionNo=HttpServletRequestUtil.getParamValueFromHeadByParamName(request,"functionNo");
		String token = HttpServletRequestUtil.getParamValueFromHeadByParamName(request,"token");
		ResponseHead head=new ResponseHead();
		head.setFunctionNo(functionNo);
		head.setToken(token);
		responseData.setHead(head);
		JSONObject data=new JSONObject();
		responseData.setHead(head);
		responseData.setData(data);
		return responseData;
	}

	public static void setHeadOfResponseDataWithSuccessInfo(ResponseData responseData) {
		ResponseHead head=responseData.getHead();
		head.setCode(ResponseHeadEnum.SUCCESS.getCode());
		head.setZh_msg(ResponseHeadEnum.SUCCESS.getZh_msg());
		head.setEn_msg(ResponseHeadEnum.SUCCESS.getEn_msg());
	}

	public static <T> void putValueToData(ResponseData responseData,String key, T value) {
			JSONObject data=responseData.getData();
		data.put(key, value);
	}

}
