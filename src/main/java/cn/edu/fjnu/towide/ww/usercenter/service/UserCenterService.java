package cn.edu.fjnu.towide.ww.usercenter.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.fjnu.towide.dao.KeyVerificationCodeDao;
import cn.edu.fjnu.towide.dao.UserDao;
import cn.edu.fjnu.towide.service.DataCenterService;
@Service
public class UserCenterService {
	@Autowired
	UserDao userDao;
	@Autowired
	KeyVerificationCodeDao keyVerificationCodeDao;
	@Autowired
	UserCenterCheckService userCenterCheckService;
	@Autowired
	DataCenterService dataCenterService;
	@Autowired
	UserCenterLogService userCenterLogService;
	@Autowired
	UserCenterBusinessService userCenterBusinessService;


	public void weChatLoginVerificationRequestProcess() {
		userCenterCheckService.weChatLoginVerificationUserInfoCheck();
		userCenterBusinessService.weChatLoginVerificationRequestProcess();

	}


	public void getPhoneVerificationCodeRequestProcess() {
		userCenterBusinessService.getPhoneVerificationCodeRequestProcess();
	}

	public void getLoginVerificationCodeRequestProcess() {
		userCenterCheckService.getCurrentLoginUserInfoCheck();
		userCenterBusinessService.getLoginVerificationCodeRequestProcess();
	}


	public void getCurrentLoginUserInfoRequestProcess() {
		userCenterCheckService.getCurrentLoginUserInfoCheck();
		userCenterBusinessService.getCurrentLoginUserInfoRequestProcess();
	}

	/**
	 * 设置密码
	 */
	public void setPasswordRequestProcess() {
		userCenterCheckService.setPasswordRequestCheck();
		userCenterBusinessService.setPasswordRequestProcess();
	}


	/**
	 * 用户注册
	 */
	public void getUserRegistrationRequestProcess() {
		userCenterCheckService.getUserRegistrationRequestCheck();
		userCenterBusinessService.getUserRegistrationRequestProcess();
	}

	/**
	 * 忘记密码
	 */
	public void forgetPasswordRequestProcess() {
		userCenterCheckService.forgetPasswordRequestCheck();
		userCenterBusinessService.forgetPasswordRequestProcess();
	}
}
