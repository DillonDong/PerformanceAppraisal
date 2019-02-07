package cn.edu.fjnu.towide.xxx.parameterManagement.service;

import cn.edu.fjnu.towide.dao.ParameterDao;
import cn.edu.fjnu.towide.entity.Parameter;
import cn.edu.fjnu.towide.entity.ResponseData;
import cn.edu.fjnu.towide.service.DataCenterService;
import cn.edu.fjnu.towide.util.ExceptionUtil;
import cn.edu.fjnu.towide.util.ResponseDataUtil;
import cn.edu.fjnu.towide.xxx.parameterManagement.enums.ReasonOfFailure;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class ParameterManagementBusinessService {
	
	@Autowired
    DataCenterService dataCenterService;

	@Autowired
	private ParameterDao parameterDao;


	private void setReturnDataOfSuccess() {

		ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
		ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
	}
	//设置成功返回信息
	private void setReturnDataOfSuccess(JSONObject data) {
		ResponseData responseData=dataCenterService.getResponseDataFromDataLocal();
		ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
		responseData.setData(data);
	}

	@Transactional
	public void updateParameterRequestProcess() {

		Parameter parameter = dataCenterService.getData("parameter");

		Boolean res = parameterDao.UpdateParameterRequest(parameter);
		if (!res){
			ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.UPDATE_IS_FAILURE);
		}
		setReturnDataOfSuccess();

	}

	public void getParameterRequestProcess() {
		Parameter parameter = parameterDao.GetParameterRequest();

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("parameter",parameter);
		setReturnDataOfSuccess(jsonObject);
	}
}
