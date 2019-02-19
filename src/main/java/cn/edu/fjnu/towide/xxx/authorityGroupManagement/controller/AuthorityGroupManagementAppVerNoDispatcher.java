package cn.edu.fjnu.towide.xxx.authorityGroupManagement.controller;

import cn.edu.fjnu.towide.entity.ResponseData;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorityGroupManagementAppVerNoDispatcher {
	@Autowired
	AuthorityGroupManagementFunctionNoDispatcher authorityGroupManagementFunctionNoDispatcher;
	public Object dispatchByAppVerNo(JSONObject requestParamJson, ResponseData responseData) {
		JSONObject head=requestParamJson.getJSONObject("head");
		String appVerNo=head.getString("appVerNo");
		String functionNo=head.getString("functionNo");
		
		switch (appVerNo) {
		case "1.0.0":
			authorityGroupManagementFunctionNoDispatcher.dispatchByFunctionNo(functionNo,responseData);
			break;

		default:
			break;
		}
		return null;
	}

}
