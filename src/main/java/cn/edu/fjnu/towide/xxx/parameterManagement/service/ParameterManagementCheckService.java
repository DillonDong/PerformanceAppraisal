package cn.edu.fjnu.towide.xxx.parameterManagement.service;

import cn.edu.fjnu.towide.entity.Parameter;
import cn.edu.fjnu.towide.service.DataCenterService;
import cn.edu.fjnu.towide.util.ExceptionUtil;
import cn.edu.fjnu.towide.xxx.parameterManagement.enums.ReasonOfFailure;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Service
public class ParameterManagementCheckService {

	@Autowired
    DataCenterService dataCenterService;


	public void updateParameterRequestCheck() {
		JSONObject jsonObject = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("parameter");
		if (jsonObject==null){
			ExceptionUtil.throwRequestFailureException();
		}
		Parameter parameter = jsonObject.toJavaObject(Parameter.class);
		for (Field f : parameter.getClass().getDeclaredFields()) {
			f.setAccessible(true);
			try {
				if (f.get(parameter) == null) { //判断字段是否为空，并且对象属性中的基本都会转为对象类型来判断
					ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.THE_PARAMETERS_EXIST_NULL);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		Double jxqjLeft = parameter.getJxqjLeft();
		Double jxqjRight = parameter.getJxqjRight();
		Double ccqjLeft = parameter.getCcqjLeft();
		Double ccqjMiddle = parameter.getCcqjMiddle();
		Double ccqjRight = parameter.getCcqjRight();
		Double ccqjzzLeft = parameter.getCcqjzzLeft();
		Double ccqjzzRight = parameter.getCcqjzzRight();
		Double ccqjzyLeft = parameter.getCcqjzyLeft();
		Double ccqjzyRight = parameter.getCcqjzyRight();

		if (jxqjLeft>=jxqjRight || ccqjLeft>=ccqjMiddle || ccqjLeft >=ccqjRight || ccqjMiddle>=ccqjRight || ccqjzzLeft>=ccqjzzRight || ccqjzyLeft>=ccqjzyRight){
			ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.THE_PARAMETERS_DO_NOT_CONFORM_TO_SPECIFICATIONS);
		}
		dataCenterService.setData("parameter",parameter);



	}

	public void getParameterRequestCheck() {
	}
}