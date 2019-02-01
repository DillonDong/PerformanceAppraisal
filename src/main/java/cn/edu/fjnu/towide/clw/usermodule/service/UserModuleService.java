package cn.edu.fjnu.towide.clw.usermodule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class UserModuleService {

	@Autowired
	UserModuleCheckService userModuleCheckService;
	@Autowired
	UserModuleBusinessService userModuleBusinessService;

    /**
     * @Description: 获取用户薪资信息
     */
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

	/**
	 * @Description: 在添加绩效时获取用户项目考核
	 */
	public void getUserExaminationItemsInAddRequestProcess() {
		userModuleCheckService.getUserExaminationItemsInAddRequestCheck();
		userModuleBusinessService.getUserExaminationItemsInAddRequestProcess();
	}


    /**
     * @Description: 在详情中获取用户项目考核
     */
    public void getUserExaminationItemsInGetRequestProcess() {
        userModuleCheckService.getUserExaminationItemsInGetRequestCheck();
        userModuleBusinessService.getUserExaminationItemsInGetRequestProcess();
    }


    /**
     * @Description: 删除用户未审核的某月业绩考核
     */
    public void deleteExaminationItemsRequestProcess() {
        userModuleCheckService.deleteExaminationItemsRequestCheck();
        userModuleBusinessService.deleteExaminationItemsRequestProcess();
    }


	/**
	 * @Description: 获取用户信息列表
	 */
	public void getUserInfoListRequestProcess() {
		userModuleCheckService.getUserInfoListRequestCheck();
		userModuleBusinessService.getUserInfoListRequestProcess();
	}

	/**
	 * @Description: 获取用户详细信息
	 */
	public void getUserDetailedInfoRequestProcess() {
		userModuleCheckService.getUserDetailedInfoRequestCheck();
		userModuleBusinessService.getUserDetailedInfoRequestProcess();
	}


	/**
	 * @Description: 更新用户详细信息
	 */
	public void updateUserDetailedInfoRequestProcess() {
		userModuleCheckService.updateUserDetailedInfoRequestCheck();
		userModuleBusinessService.updateUserDetailedInfoRequestProcess();
	}

	/**
	 * @Description: 添加绩效
	 */
	public void addExaminationItemsRequestProcess() {
		userModuleCheckService.addExaminationItemsRequestCheck();
		userModuleBusinessService.addExaminationItemsRequestProcess();
	}
}
