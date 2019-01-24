package cn.edu.fjnu.towide.clw.usermodule.service;

import cn.edu.fjnu.towide.clw.usermodule.enums.ReasonOfFailure;
import cn.edu.fjnu.towide.util.CheckVariableUtil;
import cn.edu.fjnu.towide.util.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.fjnu.towide.dao.UserDao;
import cn.edu.fjnu.towide.service.DataCenterService;

@Service
public class UserModuleCheckService {

    @Autowired
    DataCenterService dataCenterService;
    @Autowired
    UserDao userDao;

    public void userModuleRequestCheck() {
    }


    // 获取用户信息
    public void getUserInfoRequestCheck() {
        String openid = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("openid");
        boolean checkOpenid= CheckVariableUtil.stringVariableIsEmpty(openid);
        if (checkOpenid){
            ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.OPENID_IS_EMPTY);
        }
        dataCenterService.setData("openid", openid);

    }


    /**
     * @Description: 判断用户是否存在检查
     */
    public void isUserExistRequestCheck() {
        String openid = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("openid");
        boolean checkOpenid= CheckVariableUtil.stringVariableIsEmpty(openid);
        if (checkOpenid){
            ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.OPENID_IS_EMPTY);
        }
        dataCenterService.setData("openid", openid);

    }
}
