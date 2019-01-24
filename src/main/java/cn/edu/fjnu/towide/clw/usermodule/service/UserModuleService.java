package cn.edu.fjnu.towide.clw.usermodule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class UserModuleService {

	@Autowired
	UserModuleCheckService userModuleCheckService;
	@Autowired
	UserModuleBusinessService userModuleBusinessService;
	

	public void getUserInfoRequestProcess() {
		userModuleCheckService.getUserInfoRequestCheck();
		userModuleBusinessService.getUserInfoRequestProcess();
	}

	/**
	 * @Description: 判断用户是否存在
	 */
	public void isUserExistRequestProcess() {
		userModuleCheckService.isUserExistRequestCheck();
		userModuleBusinessService.isUserExistRequestProcess();
	}


}
