package cn.edu.fjnu.towide.xxx.assessmentItemManagement.controller;

import cn.edu.fjnu.towide.entity.ResponseData;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssessmentItemManagementAppVerNoDispatcher {
	@Autowired
    AssessmentItemManagementNoDispatcher assessmentItemManagementNoDispatcher;
	public Object dispatchByAppVerNo(JSONObject requestParamJson,ResponseData responseData) {
		JSONObject head=requestParamJson.getJSONObject("head");
		String appVerNo=head.getString("appVerNo");
		String functionNo=head.getString("functionNo");
		
		switch (appVerNo) {
		case "1.0.0":
			assessmentItemManagementNoDispatcher.dispatchByFunctionNo(functionNo, responseData);
			break;

		default:
			break;
		}
		return null;
	}

}
