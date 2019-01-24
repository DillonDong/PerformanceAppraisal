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
        // String openid = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("openid");
        // String nickName = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("nickName");
        // String avatarUrl = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("avatarUrl");
        // String province = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("province");
        // String city = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("city");
        JSONObject userInfoVoJSONObject = dataCenterService
                .getParamValueFromParamOfRequestParamJsonByParamName("userInfoVo");
        UserInfoVo userInfoVo = userInfoVoJSONObject.toJavaObject(UserInfoVo.class);
        if(userInfoVo==null){
            ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.USERINFO_IS_EMPTY);
            return;
        }
        boolean checkName = CheckVariableUtil.stringVariableIsEmpty(userInfoVo.getUsername());


        if (checkName) {
            ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.OPENID_IS_EMPTY);
        }

        dataCenterService.setData("userInfoVo", userInfoVo);

    }

    /**
     *  忘记密码校验
     */
    public void forgetPasswordRequestCheck() {

        String idCardNum = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("idNum");
        String email = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("email");

        boolean checkIdCardNum = CheckVariableUtil.isIdcardIllegal(idCardNum);
        boolean checkEmail = CheckVariableUtil.stringVariableIsEmpty(email);


        if (checkIdCardNum) {
            ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.IDNUM_IS_ERROR);
        }

        if (checkEmail) {
            ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.EMAIL_IS_EMPTY);
        }

        String emailFromIdCardNum = userDao.getEmailFromIdCardNum(idCardNum);
        if (emailFromIdCardNum == null || (!email.equals(emailFromIdCardNum))) {
            ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.USER_DOES_NOT_EXIST);
        }

        dataCenterService.setData("idCardNum", idCardNum);
        dataCenterService.setData("email", email);

    }
}
