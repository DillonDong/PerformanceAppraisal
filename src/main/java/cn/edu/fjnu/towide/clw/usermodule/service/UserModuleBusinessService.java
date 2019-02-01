package cn.edu.fjnu.towide.clw.usermodule.service;

import cn.edu.fjnu.towide.dao.UserDetailDao;
import cn.edu.fjnu.towide.entity.*;
import cn.edu.fjnu.towide.util.CheckVariableUtil;
import cn.edu.fjnu.towide.util.IdGenerator;
import cn.edu.fjnu.towide.vo.CountVo;
import cn.edu.fjnu.towide.vo.DeptAsVo;
import cn.edu.fjnu.towide.vo.UserDetailInfoVo;
import cn.edu.fjnu.towide.xxx.fileupload.constant.LogConstant;
import com.alibaba.fastjson.JSONArray;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.edu.fjnu.towide.constant.FilePathNameTemplate.uploadFileLocalPath;

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
            return;
        }
        boolean deleteUserExaminationItemsCheck=userDetailDao.deleteUserExaminationItems(username,time);
        if (!deleteUserExaminationItemsCheck) {
            ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.DELETE_IS_WRONG);
			return;
		}
        responseUtil(null,null);
    }


    /**
     * @Description: 获取用户信息列表
     */
    public void getUserInfoListRequestProcess() {
        int pageNum = dataCenterService.getData("pageNum");
        int pageSize = dataCenterService.getData("pageSize");
        String keyword = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("keyword");

        PageHelper.startPage(pageNum, pageSize);

        List<UserInfoVo> userInfoVo=userDetailDao.getUserInfoList(keyword);
        PageInfo<UserInfoVo> pageResult = new PageInfo<UserInfoVo>(userInfoVo);

        responseUtil("pageResult",pageResult);
    }


	/**
	 * @Description: 获取用户详细信息
	 */
	public void getUserDetailedInfoRequestProcess() {
		String username = dataCenterService.getData("username");
		UserDetailInfoVo userDetailInfo=userDetailDao.getUserDetailedInfoList(username);
		userDetailInfo.setIdPre(uploadFileLocalPath +userDetailInfo.getIdPre());
		userDetailInfo.setIdAfter(uploadFileLocalPath +userDetailInfo.getIdAfter());
		responseUtil("userDetailInfo",userDetailInfo);
	}


	/**
	 * @Description: 添加绩效
	 */
	public void addExaminationItemsRequestProcess() {
		JSONArray examinationItems = dataCenterService
				.getParamValueFromParamOfRequestParamJsonByParamName("examinationItemsList");
		if(examinationItems==null||examinationItems.size()==0) {
			ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.EXAMINATION_ITEMS_EMPTY);
			return;
		}
		User currentLoginUser = dataCenterService.getCurrentLoginUserFromDataLocal();
		String username = currentLoginUser.getUsername();
		//数组第一个为时间
		String time=JSONObject.parseObject(JSONObject.toJSONString(examinationItems.get(0))).getString("value");
		//数组第二个为营业额
		Double turnover=JSONObject.parseObject(JSONObject.toJSONString(examinationItems.get(1))).getDouble("value");

		Map<String,Double> countMap=new HashMap<>();
		for(int i=2;i<examinationItems.size();i++){
			String examinationItemId=JSONObject.parseObject(JSONObject.toJSONString(examinationItems.get(i))).getString("examinationItemId");
			Double value=JSONObject.parseObject(JSONObject.toJSONString(examinationItems.get(i))).getDouble("value");
			CountVo bigger=userDetailDao.getBiggerCount(examinationItemId,value);
			CountVo smaller=userDetailDao.getSmallerCount(examinationItemId,value);
			//计算比例
			double bili=(bigger.getLevel()-smaller.getLevel()) / (bigger.getCount()-smaller.getCount());
			//计算分数
			double count=(value-smaller.getLevel()) / bili+smaller.getCount();
			//把考核项目和分数放到map中
			countMap.put(examinationItemId,count);
			//把考核项目和分数存到user_assessment表中
			UserAssessment userAssessment=new UserAssessment();
			userAssessment.setId(IdGenerator.getId());
			userAssessment.setTime(time);
			String assessmentItemName=userDetailDao.getAssessmentItemName(examinationItemId);
			userAssessment.setAssessmentItem(assessmentItemName);
			userAssessment.setCount(count);

			userAssessment.setUserId(username);
			boolean addSuccess=userDetailDao.addAssessmentItemAndCount(userAssessment);
			if(!addSuccess){
				ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.ADD_EXAMINATION_ITEMS_WRONG);
				return;
			}
		}
		String deptId=userDetailDao.getDeptIdByUserName(username);

		List<DeptAsVo> assessmentList=userDetailDao.getDeptAssessmentList(deptId);
		double totalCount=0;
		for (Map.Entry<String, Double> myItems : countMap.entrySet()) {
			for (DeptAsVo deptAsVo : assessmentList) {
				if(myItems.getKey().equals(deptAsVo.getAsId())){

				}
			}
		}

	}


	/**
	 * @Description: 更新用户详细信息
	 */
	public void updateUserDetailedInfoRequestProcess() {
		UserDetailInfoVo userDetailInfo = dataCenterService.getData("userDetailInfo");
		boolean updateUserDetailResult =userDetailDao.updateUserDetailedInfo(userDetailInfo);
		if (!updateUserDetailResult) {
			ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.UPDATE_ERROR);
			return;
		}
		responseUtil(null,null);
	}
}
