package cn.edu.fjnu.towide.clw.usermodule.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.edu.fjnu.towide.clw.usermodule.constant.UserModuleFunctionNoConstants;
import cn.edu.fjnu.towide.clw.usermodule.service.UserModuleService;
import cn.edu.fjnu.towide.entity.ResponseData;
import cn.edu.fjnu.towide.enums.ReasonOfFailure;
import cn.edu.fjnu.towide.service.DataCenterService;
import cn.edu.fjnu.towide.util.ResponseDataUtil;

@Component
public class UserModuleFunctionNoDispatcher {
    @Autowired
    UserModuleService userModuleService;
    @Autowired
    DataCenterService dataCenterService;

    static Logger logger = LoggerFactory.getLogger(UserModuleFunctionNoDispatcher.class);

    public void dispatchByFunctionNo(String functionNo, ResponseData responseData) {
        switch (functionNo) {

            case UserModuleFunctionNoConstants.GET_USER_INFO:
                userModuleService.getUserInfoRequestProcess();
                break;
            case UserModuleFunctionNoConstants.IS_USER_EXIST:
                userModuleService.isUserExistRequestProcess();
                break;
            default:
                ResponseDataUtil.setResponseDataWithFailureInfo(responseData, ReasonOfFailure.FUNCTION_NO_ARE_INCORRECT);
                break;
        }
    }

}
