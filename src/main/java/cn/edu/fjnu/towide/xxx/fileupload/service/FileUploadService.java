package cn.edu.fjnu.towide.xxx.fileupload.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileUploadService {

	
	@Autowired
	FileUploadCheckService fileUploadCheckService;
	@Autowired
	FileUploadBusinessService fileUploadBusinessService;

	public void fileUploadRequestProcess() {
		fileUploadCheckService.fileUploadRequestCheck();
		fileUploadBusinessService.fileUploadRequestProcess();
	}

}
