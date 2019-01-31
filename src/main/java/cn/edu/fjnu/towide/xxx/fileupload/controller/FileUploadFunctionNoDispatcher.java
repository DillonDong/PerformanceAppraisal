package cn.edu.fjnu.towide.xxx.fileupload.controller;

import cn.edu.fjnu.towide.entity.ResponseData;
import cn.edu.fjnu.towide.enums.ReasonOfFailure;
import cn.edu.fjnu.towide.util.ResponseDataUtil;
import cn.edu.fjnu.towide.xxx.fileupload.constant.FileUploadFunctionNoConstants;
import cn.edu.fjnu.towide.xxx.fileupload.service.FileUploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileUploadFunctionNoDispatcher {
	
	@Autowired
	FileUploadService fileUploadService;
	

	static Logger logger = LoggerFactory.getLogger(FileUploadFunctionNoDispatcher.class);

	public void dispatchByFunctionNo(String functionNo, ResponseData responseData) {
		switch (functionNo) {
		
		// 上传文件
		case FileUploadFunctionNoConstants.FILE_UPLOAD:
			fileUploadService.fileUploadRequestProcess();
			break;
		
		default:
			ResponseDataUtil.setResponseDataWithFailureInfo(responseData, ReasonOfFailure.FUNCTION_NO_ARE_INCORRECT);
			break;
		}
	}

}
