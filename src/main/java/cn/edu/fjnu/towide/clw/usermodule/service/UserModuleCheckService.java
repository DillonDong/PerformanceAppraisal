package cn.edu.fjnu.towide.clw.usermodule.service;

import cn.edu.fjnu.towide.clw.usermodule.enums.ReasonOfFailure;
import cn.edu.fjnu.towide.util.CheckVariableUtil;
import cn.edu.fjnu.towide.util.ExceptionUtil;
import cn.edu.fjnu.towide.vo.UserDetailInfoVo;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.fjnu.towide.dao.UserDao;
import cn.edu.fjnu.towide.service.DataCenterService;

import java.util.List;
import java.util.Map;

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



    /**
     * @Description: 获取用户详细信息
     */
    public void getUserDetailedInfoRequestCheck() {
        String username = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("username");
        if (CheckVariableUtil.stringVariableIsEmpty(username)) {
            ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.USER_NAME_IS_EMPTY);
        }
        dataCenterService.setData("username", username);
    }


    /**
     * @Description: 更新用户详细信息
     */
    public void updateUserDetailedInfoRequestCheck() {
        JSONObject userDetailInfoJSONObject = dataCenterService
                .getParamValueFromParamOfRequestParamJsonByParamName("userDetailInfoVo");
        UserDetailInfoVo userDetailInfo = userDetailInfoJSONObject.toJavaObject(UserDetailInfoVo.class);
        if(userDetailInfo==null){
            ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.UPDATE_ERROR);
            return;
        }
        dataCenterService.setData("userDetailInfo", userDetailInfo);
    }


    /**
     * @Description: 添加绩效
     */
    public void addExaminationItemsRequestCheck() {


    }

    /**
     * @Description: 更新绩效
     */
    public void updateExaminationItemsRequestCheck() {


    }

    /**
     * @Description: 删除用户
     */
    public void deleteUserRequestCheck() {
        String username = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("username");
        if (CheckVariableUtil.stringVariableIsEmpty(username)) {
            ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.USER_NAME_IS_EMPTY);
        }
        dataCenterService.setData("username", username);
    }


    /**
     * @Description: 根据时间范围获得考核项柱形图数据
     */
    public void getGraphDataRequestCheck() {
        String examinationItem = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("examinationItem");
        String username = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("username");
        String startTime = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("startTime");
        String endTime = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("endTime");

        if (CheckVariableUtil.stringVariableIsEmpty(examinationItem)) {
            ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.EXAMINATION_ITEM_IS_EMPTY);
        }
        if (CheckVariableUtil.stringVariableIsEmpty(username)) {
            ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.USER_NAME_IS_EMPTY);
        }
        if (CheckVariableUtil.stringVariableIsEmpty(startTime)||CheckVariableUtil.stringVariableIsEmpty(endTime)) {
            ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.TIME_IS_EMPTY);
        }
        dataCenterService.setData("examinationItem", examinationItem);
        dataCenterService.setData("username", username);
        dataCenterService.setData("startTime", startTime);
        dataCenterService.setData("endTime", endTime);
    }
}
