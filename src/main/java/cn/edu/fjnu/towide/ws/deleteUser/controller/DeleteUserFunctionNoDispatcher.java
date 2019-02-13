package cn.edu.fjnu.towide.ws.deleteUser.controller;

import cn.edu.fjnu.towide.entity.ResponseData;
import cn.edu.fjnu.towide.enums.ReasonOfFailure;
import cn.edu.fjnu.towide.service.DataCenterService;
import cn.edu.fjnu.towide.util.ResponseDataUtil;
import cn.edu.fjnu.towide.ws.deleteUser.constant.DeleteUserFunctionNoConstants;
import cn.edu.fjnu.towide.ws.deleteUser.service.DeleteUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteUserFunctionNoDispatcher {
    @Autowired
    DeleteUserService userModuleService;
    @Autowired
    DataCenterService dataCenterService;

    static Logger logger = LoggerFactory.getLogger(DeleteUserFunctionNoDispatcher.class);

    public void dispatchByFunctionNo(String functionNo, ResponseData responseData) {
        switch (functionNo) {


            case DeleteUserFunctionNoConstants.DELETE_USER:
                userModuleService.deleteUserRequestProcess();
                break;

            default:
                ResponseDataUtil.setResponseDataWithFailureInfo(responseData, ReasonOfFailure.FUNCTION_NO_ARE_INCORRECT);
                break;
        }
    }

}
