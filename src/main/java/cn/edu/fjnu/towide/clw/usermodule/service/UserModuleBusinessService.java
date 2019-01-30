package cn.edu.fjnu.towide.clw.usermodule.service;

import cn.edu.fjnu.towide.dao.UserDetailDao;
import cn.edu.fjnu.towide.entity.*;
import cn.edu.fjnu.towide.util.CheckVariableUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

import cn.edu.fjnu.towide.clw.usermodule.enums.ReasonOfFailure;
import cn.edu.fjnu.towide.dao.AuthoritiesDao;
import cn.edu.fjnu.towide.dao.UserDao;
import cn.edu.fjnu.towide.service.DataCenterService;
import cn.edu.fjnu.towide.util.ExceptionUtil;
import cn.edu.fjnu.towide.util.ResponseDataUtil;
import cn.edu.fjnu.towide.vo.UserInfoVo;

import java.util.List;

@Service
public class UserModuleBusinessService {
	
	@Autowired
	DataCenterService dataCenterService;
	@Autowired
	UserDao userDao;
	@Autowired
	UserDetailDao userDetailDao;
	@Autowired
	AuthoritiesDao authoritiesDao;

	/**
	 * @Description: 返回数据封装
	 */
	public void responseUtil(String dataKey, Object dataValue) {
		ResponseData responseData = dataCenterService.getData("responseData");
		ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
		if (dataKey != null) {
			JSONObject data = new JSONObject();
			data.put(dataKey, dataValue);
			responseData.setData(data);
		}
	}
    /**
     * @Description: 获取用户信息 管理员界面进入要传username；员工界面进入不要传，直接获取当前用户
     */
	public void getUserInfoRequestProcess() {
		String startTime = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("startTime");
		String endTime = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("endTime");
		int pageNum = dataCenterService.getData("pageNum");
		int pageSize = dataCenterService.getData("pageSize");

		String username = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("username");
		if(CheckVariableUtil.stringVariableIsEmpty(username)){
			User currentLoginUser = dataCenterService.getCurrentLoginUserFromDataLocal();
			username = currentLoginUser.getUsername();
		}

		PageHelper.startPage(pageNum, pageSize);

		List<UserWages> userInfoVo=userDetailDao.getUserInfoByUsername(startTime,endTime,username);
		PageInfo<UserWages> pageResult = new PageInfo<UserWages>(userInfoVo);

		responseUtil("pageResult",pageResult);
	}

	/**
	 * @Description: 判断用户是否存在
	 */
	public void isUserExistRequestProcess() {
		String openid =  dataCenterService.getData("openid");

		boolean isUserExistCheck=userDao.isUserExist(openid);

		responseUtil("isUserExistCheck",isUserExistCheck);
	}


	/**
	 * @Description: 获取用户项目考核用于添加考核业绩
	 */
	public void getUserExaminationItemsInAddRequestProcess() {
		User currentLoginUser = dataCenterService.getCurrentLoginUserFromDataLocal();
		String username = currentLoginUser.getUsername();
		List<AssessmentItem> examinationItems=userDetailDao.getUserExaminationItemsInAdd(username);

		responseUtil("examinationItems",examinationItems);
	}


    /**
     * @Description: 在详情中获取用户项目考核
     */
    public void getUserExaminationItemsInGetRequestProcess() {
        String time = dataCenterService.getData("time");
        String username = dataCenterService.getData("username");

        List<UserAssessment> examinationItems=userDetailDao.getUserExaminationItemsInGet(username,time);

        responseUtil("examinationItems",examinationItems);
    }


    /**
     * @Description: 删除用户未审核的某月业绩考核
     */
    @Transactional
    public void deleteExaminationItemsRequestProcess() {
        String time = dataCenterService.getData("time");
        String username = dataCenterService.getData("username");
        boolean deleteUserSalaryInfoCheck=userDetailDao.deleteUserSalaryInfo(username,time);
        if (!deleteUserSalaryInfoCheck) {
            ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.DELETE_IS_WRONG);
        }
        boolean deleteUserExaminationItemsCheck=userDetailDao.deleteUserExaminationItems(username,time);
        if (!deleteUserExaminationItemsCheck) {
            ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.DELETE_IS_WRONG);
        }
        responseUtil(null,null);
    }
}
