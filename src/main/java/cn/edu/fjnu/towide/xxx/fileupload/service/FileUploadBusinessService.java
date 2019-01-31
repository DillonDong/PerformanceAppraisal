package cn.edu.fjnu.towide.xxx.fileupload.service;

import cn.edu.fjnu.towide.entity.ResponseData;
import cn.edu.fjnu.towide.service.DataCenterService;
import cn.edu.fjnu.towide.util.ResponseDataUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileUploadBusinessService {
	
	@Autowired
    DataCenterService dataCenterService;

	
	public void fileUploadRequestProcess() {
		
		String fileName=dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("fileName");
		
		JSONObject data = new JSONObject();
		data.put("fileName", fileName);
		
		setReturnDataOfSuccess(data);
	}

	//设置成功返回信息
	private void setReturnDataOfSuccess(JSONObject data) {
		ResponseData responseData=dataCenterService.getResponseDataFromDataLocal();
		ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
		responseData.setData(data);
	}

}
