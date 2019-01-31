package cn.edu.fjnu.towide.xxx.fileupload.service;

import cn.edu.fjnu.towide.service.DataCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileUploadCheckService {

	@Autowired
    DataCenterService dataCenterService;
	
	
	public void fileUploadRequestCheck() {}
	
}
