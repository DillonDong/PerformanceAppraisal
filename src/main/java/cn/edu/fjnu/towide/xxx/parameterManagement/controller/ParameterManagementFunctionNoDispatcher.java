package cn.edu.fjnu.towide.xxx.parameterManagement.controller;

import cn.edu.fjnu.towide.entity.ResponseData;
import cn.edu.fjnu.towide.enums.ReasonOfFailure;
import cn.edu.fjnu.towide.util.ResponseDataUtil;
import cn.edu.fjnu.towide.xxx.parameterManagement.constant.ParameterManagementFunctionNoConstants;
import cn.edu.fjnu.towide.xxx.parameterManagement.service.ParameterManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParameterManagementFunctionNoDispatcher {

    @Autowired
    ParameterManagementService parameterManagementService;


    static Logger logger = LoggerFactory.getLogger(ParameterManagementFunctionNoDispatcher.class);

    public void dispatchByFunctionNo(String functionNo, ResponseData responseData) {
        switch (functionNo) {

            case ParameterManagementFunctionNoConstants.UPDATE_PARAMETER_REQUEST:
                parameterManagementService.updateParameterRequest();
				break;
            case ParameterManagementFunctionNoConstants.GET_PARAMETER_REQUEST:
                parameterManagementService.getParameterRequest();
                break;
            default:
                ResponseDataUtil.setResponseDataWithFailureInfo(responseData, ReasonOfFailure.FUNCTION_NO_ARE_INCORRECT);
                break;
        }
    }

}
