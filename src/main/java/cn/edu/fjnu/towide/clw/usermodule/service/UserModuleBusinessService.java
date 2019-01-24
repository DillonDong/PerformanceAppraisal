package cn.edu.fjnu.towide.clw.usermodule.service;

import cn.edu.fjnu.towide.dao.UserDetailDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

import cn.edu.fjnu.towide.clw.usermodule.enums.ReasonOfFailure;
import cn.edu.fjnu.towide.dao.AuthoritiesDao;
import cn.edu.fjnu.towide.dao.UserDao;
import cn.edu.fjnu.towide.entity.ResponseData;
import cn.edu.fjnu.towide.entity.User;
import cn.edu.fjnu.towide.service.DataCenterService;
import cn.edu.fjnu.towide.util.ExceptionUtil;
import cn.edu.fjnu.towide.util.ResponseDataUtil;
import cn.edu.fjnu.towide.vo.UserInfoVo;

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

	// 获取用户信息
	public void getUserInfoRequestProcess() {
		String openid =  dataCenterService.getData("openid");

		UserInfoVo userInfoVo=userDetailDao.getUserInfoByOpenId(openid);

		if(userInfoVo==null){
			ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.OPENID_IS_WRONG);
		}

		responseUtil("userInfoVo",userInfoVo);
	}

	/**
	 * @Description: 判断用户是否存在
	 */
	public void isUserExistRequestProcess() {
		String openid =  dataCenterService.getData("openid");

		boolean isUserExistCheck=userDao.isUserExist(openid);

		responseUtil("isUserExistCheck",isUserExistCheck);
	}

}
