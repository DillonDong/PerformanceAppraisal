package cn.edu.fjnu.towide.xxx.parameterManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParameterManagementService {

	
	@Autowired
	ParameterManagementCheckService parameterManagementCheckService;
	@Autowired
	ParameterManagementBusinessService parameterManagementBusinessService;


    public void updateParameterRequest() {
        parameterManagementCheckService.updateParameterRequestCheck();
        parameterManagementBusinessService.updateParameterRequestProcess();
    }

    public void getParameterRequest() {
        parameterManagementCheckService.getParameterRequestCheck();
        parameterManagementBusinessService.getParameterRequestProcess();
    }
}
