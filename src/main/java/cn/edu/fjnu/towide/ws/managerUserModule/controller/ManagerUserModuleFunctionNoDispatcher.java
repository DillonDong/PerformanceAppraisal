package cn.edu.fjnu.towide.ws.managerUserModule.controller;

import cn.edu.fjnu.towide.entity.ResponseData;
import cn.edu.fjnu.towide.enums.ReasonOfFailure;
import cn.edu.fjnu.towide.service.DataCenterService;
import cn.edu.fjnu.towide.util.ResponseDataUtil;
import cn.edu.fjnu.towide.ws.managerUserModule.constant.ManagerUserModuleFunctionNoConstants;
import cn.edu.fjnu.towide.ws.managerUserModule.service.ManagerUserModuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ManagerUserModuleFunctionNoDispatcher {
    @Autowired
    ManagerUserModuleService managerUserModuleService;
    @Autowired
    DataCenterService dataCenterService;

    static Logger logger = LoggerFactory.getLogger(ManagerUserModuleFunctionNoDispatcher.class);

    public void dispatchByFunctionNo(String functionNo, ResponseData responseData) {
        switch (functionNo) {


            case ManagerUserModuleFunctionNoConstants.GET_USER_INFO_LIST:
                managerUserModuleService.getUserInfoListRequestProcess();
                break;
            case ManagerUserModuleFunctionNoConstants.EXAMINE_SALARY:
                managerUserModuleService.examineSalaryRequestProcess();
                break;
            default:
                ResponseDataUtil.setResponseDataWithFailureInfo(responseData, ReasonOfFailure.FUNCTION_NO_ARE_INCORRECT);
                break;
        }
    }

}
