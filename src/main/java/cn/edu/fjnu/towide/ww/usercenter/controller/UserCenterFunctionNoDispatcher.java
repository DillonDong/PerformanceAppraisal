package cn.edu.fjnu.towide.ww.usercenter.controller;

import cn.edu.fjnu.towide.entity.ResponseData;
import cn.edu.fjnu.towide.enums.ReasonOfFailure;
import cn.edu.fjnu.towide.util.ResponseDataUtil;
import cn.edu.fjnu.towide.ww.usercenter.constant.UserCenterFunctionNoConstants;
import cn.edu.fjnu.towide.ww.usercenter.service.UserCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserCenterFunctionNoDispatcher {
    @Autowired
    UserCenterService userCenterService;

    public void dispatchByFunctionNo(String functionNo, ResponseData responseData) {
        switch (functionNo) {
            case UserCenterFunctionNoConstants.WECHAT_LOGIN_VERIFICATION:
                userCenterService.weChatLoginVerificationRequestProcess();
                break;

            case UserCenterFunctionNoConstants.GET_PHONE_VERIFICATION_CODE:
                userCenterService.getPhoneVerificationCodeRequestProcess();
                break;

            case UserCenterFunctionNoConstants.GET_LOGIN_VERIFICATION_CODE:
                userCenterService.getLoginVerificationCodeRequestProcess();
                break;

            case UserCenterFunctionNoConstants.Get_Current_Login_User_Info:
                userCenterService.getCurrentLoginUserInfoRequestProcess();
                break;
     
            //设置密码
            case UserCenterFunctionNoConstants.SET_PASSWORD:
                userCenterService.setPasswordRequestProcess();
                break;

            //用户注册
            case UserCenterFunctionNoConstants.Get_USER_REGISTRATION:
                userCenterService.getUserRegistrationRequestProcess();
                break;

            //忘记密码
            case UserCenterFunctionNoConstants.FORGET_PASSWORD:
                userCenterService.forgetPasswordRequestProcess();
                break;

            default:
                ResponseDataUtil.setResponseDataWithFailureInfo(responseData, ReasonOfFailure.FUNCTION_NO_ARE_INCORRECT);
                break;
        }
    }
}
