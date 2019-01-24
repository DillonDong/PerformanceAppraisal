package cn.edu.fjnu.towide.clw.usermodule.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import cn.edu.fjnu.towide.entity.ResponseData;
@Component
public class UserModuleAppVerNoDispatcher {
	@Autowired
	UserModuleFunctionNoDispatcher userCenterFunctionNoDispatcher;
	public Object dispatchByAppVerNo(JSONObject requestParamJson,ResponseData responseData) {
		JSONObject head=requestParamJson.getJSONObject("head");
		String appVerNo=head.getString("appVerNo");
		String functionNo=head.getString("functionNo");
		
		switch (appVerNo) {
		case "1.0.0":
			userCenterFunctionNoDispatcher.dispatchByFunctionNo(functionNo,responseData);
			break;

		default:
			break;
		}
		return null;
	}

}
