package cn.edu.fjnu.towide.clw.usermodule.service;

import cn.edu.fjnu.towide.clw.usermodule.enums.ReasonOfFailure;
import cn.edu.fjnu.towide.util.CheckVariableUtil;
import cn.edu.fjnu.towide.util.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.fjnu.towide.dao.UserDao;
import cn.edu.fjnu.towide.service.DataCenterService;

import static cn.edu.fjnu.towide.util.ExceptionUtil.throwRequestFailureException;

@Service
public class UserModuleCheckService {

    @Autowired
    DataCenterService dataCenterService;
    @Autowired
    UserDao userDao;

    public void userModuleRequestCheck() {
    }
    private void checkEmptyIntVariable(int num) {
        if (num < 0) {
            throwRequestFailureException();
        }
    }

    // 检查分页信息
    private void checkpageInfo() {

        Integer pageNum = null;
        Integer pageSize = null;

        try {// 检查分页参数
            pageNum = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageNum");
            pageSize = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageSize");
        } catch (Exception e) {
            ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.PAGEINFO_ERROR);
        }
        checkEmptyIntVariable(pageNum);
        checkEmptyIntVariable(pageSize);

        dataCenterService.setData("pageNum", pageNum);
        dataCenterService.setData("pageSize", pageSize);
    }

    // 获取用户薪资信息
    public void getUserInfoRequestCheck() {
        checkpageInfo();
    }


    /**
     * @Description: 判断用户是否存在检查
     */
    public void isUserExistRequestCheck() {
    }


    /**
     * @Description: 获取用户项目考核
     */
    public void getUserExaminationItemsInAddRequestCheck() {
    }


    /**
     * @Description: 在详情中获取用户项目考核
     */
    public void getUserExaminationItemsInGetRequestCheck() {
        checkTimeAndUsername();
    }


    /**
     * @Description: 删除用户未审核的某月业绩考核
     */
    public void deleteExaminationItemsRequestCheck() {
        checkTimeAndUsername();
    }



    public void checkTimeAndUsername() {
        String time = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("time");
        String username = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("username");
        if (CheckVariableUtil.stringVariableIsEmpty(time)) {
            ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.TIME_IS_EMPTY);
        }
        if (CheckVariableUtil.stringVariableIsEmpty(username)) {
            ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.USER_NAME_IS_EMPTY);
        }
        dataCenterService.setData("time", time);
        dataCenterService.setData("username", username);
    }


    /**
     * @Description: 获取用户信息列表
     */
    public void getUserInfoListRequestCheck() {
        checkpageInfo();
    }


}
