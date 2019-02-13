package cn.edu.fjnu.towide.ws.deleteUser.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteUserService {

	@Autowired
	DeleteUserCheckService deleteUserCheckService;
	@Autowired
	DeleteUserBusinessService deleteUserBusinessService;




	/**
	 * @Description: 删除用户
	 */
	public void deleteUserRequestProcess() {
		deleteUserCheckService.deleteUserRequestCheck();
		deleteUserBusinessService.deleteUserRequestProcess();
	}
}
