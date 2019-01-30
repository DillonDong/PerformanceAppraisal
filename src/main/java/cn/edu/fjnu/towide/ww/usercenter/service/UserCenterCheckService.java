package cn.edu.fjnu.towide.ww.usercenter.service;

import cn.edu.fjnu.towide.dao.UserDao;
import cn.edu.fjnu.towide.service.DataCenterService;
import cn.edu.fjnu.towide.util.*;
import cn.edu.fjnu.towide.vo.UserInfoVo;
import cn.edu.fjnu.towide.ww.usercenter.enums.ReasonOfFailure;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserCenterCheckService {

	@Autowired
    DataCenterService dataCenterService;
	@Autowired
    UserDao userDao;


	public void getCurrentLoginUserInfoCheck() {

	}

    public void weChatLoginVerificationUserInfoCheck() {
        String weChatCode = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("weChatCode");

        boolean checkWeChatCode= CheckVariableUtil.stringVariableIsEmpty(weChatCode);
        if (checkWeChatCode){
            ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.WECHAT_CODE_IS_EMPTY);
        }


        System.out.println("------weChatCode------" + weChatCode);
        dataCenterService.setData("weChatCode",weChatCode);
    }



    /**
     *  设置密码
     */
    public void setPasswordRequestCheck() {

        String userName = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("userName");
        String password = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("password");

        boolean checkUserName = CheckVariableUtil.stringVariableIsEmpty(userName);
        boolean checkPassword = CheckVariableUtil.stringVariableIsEmpty(password);

        if (checkUserName) {
            ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.USER_NAME_IS_EMPTY);
        }

        if (checkPassword) {
            ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.PASSWORD_IS_EMPTY);
        }

        dataCenterService.setData("userName", userName);
        dataCenterService.setData("password", password);
    }



    /**
     *  用户注册
     */
    public void getUserRegistrationRequestCheck() {
        String username = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("username");
        String password = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("password");
        String realName = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("realName");

        if (CheckVariableUtil.stringVariableIsEmpty(username)) {
            ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.USERNAME_IS_EMPTY);
        }

        if (CheckVariableUtil.stringVariableIsEmpty(password)) {
            ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.PASSWORD_IS_EMPTY);
        }

        if (CheckVariableUtil.stringVariableIsEmpty(realName)) {
            ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.REALNAME_IS_EMPTY);
        }

        dataCenterService.setData("username", username);
        dataCenterService.setData("password", password);
        dataCenterService.setData("realName", realName);

    }

    /**
     *  更新密码校验
     */
    public void forgetPasswordRequestCheck() {

        String password = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("password");

        if (CheckVariableUtil.stringVariableIsEmpty(password)) {
            ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.PASSWORD_IS_EMPTY);
        }

        dataCenterService.setData("password", password);

    }
}
