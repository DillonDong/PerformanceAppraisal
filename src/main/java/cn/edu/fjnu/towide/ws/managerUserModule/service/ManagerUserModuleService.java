package cn.edu.fjnu.towide.ws.managerUserModule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerUserModuleService {

	@Autowired
    ManagerUserModuleCheckService managerUserModuleCheckService;
	@Autowired
    ManagerUserModuleBusinessService managerUserModuleBusinessService;

    /**
     * @Description: 获取用户薪资信息
     */
	public void getUserInfoRequestProcess() {
		managerUserModuleCheckService.getUserInfoRequestCheck();
		managerUserModuleBusinessService.getUserInfoRequestProcess();
	}

	/**
	 * @Description: 判断用户是否存在
	 */
	public void isUserExistRequestProcess() {
		managerUserModuleCheckService.isUserExistRequestCheck();
		managerUserModuleBusinessService.isUserExistRequestProcess();
	}

	/**
	 * @Description: 在添加绩效时获取用户项目考核
	 */
	public void getUserExaminationItemsInAddRequestProcess() {
		managerUserModuleCheckService.getUserExaminationItemsInAddRequestCheck();
		managerUserModuleBusinessService.getUserExaminationItemsInAddRequestProcess();
	}


    /**
     * @Description: 在详情中获取用户项目考核
     */
    public void getUserExaminationItemsInGetRequestProcess() {
        managerUserModuleCheckService.getUserExaminationItemsInGetRequestCheck();
        managerUserModuleBusinessService.getUserExaminationItemsInGetRequestProcess();
    }


    /**
     * @Description: 删除用户未审核的某月业绩考核
     */
    public void deleteExaminationItemsRequestProcess() {
        managerUserModuleCheckService.deleteExaminationItemsRequestCheck();
        managerUserModuleBusinessService.deleteExaminationItemsRequestProcess();
    }


	/**
	 * @Description: 获取用户信息列表
	 */
	public void getUserInfoListRequestProcess() {
		managerUserModuleCheckService.getUserInfoListRequestCheck();
		managerUserModuleBusinessService.getUserInfoListRequestProcess();
	}

	/**
	 * @Description: 获取用户详细信息
	 */
	public void getUserDetailedInfoRequestProcess() {
		managerUserModuleCheckService.getUserDetailedInfoRequestCheck();
		managerUserModuleBusinessService.getUserDetailedInfoRequestProcess();
	}


	/**
	 * @Description: 更新用户详细信息
	 */
	public void updateUserDetailedInfoRequestProcess() {
		managerUserModuleCheckService.updateUserDetailedInfoRequestCheck();
		managerUserModuleBusinessService.updateUserDetailedInfoRequestProcess();
	}

	/**
	 * @Description: 添加绩效
	 */
	public void addExaminationItemsRequestProcess() {
		managerUserModuleCheckService.addExaminationItemsRequestCheck();
		managerUserModuleBusinessService.addExaminationItemsRequestProcess();
	}

	/**
	 * @Description: 更新绩效
	 */
	public void updateExaminationItemsRequestProcess() {
		managerUserModuleCheckService.updateExaminationItemsRequestCheck();
		managerUserModuleBusinessService.updateExaminationItemsRequestProcess();
	}


	/**
	 * @Description: 删除用户
	 */
	public void deleteUserRequestProcess() {
		managerUserModuleCheckService.deleteUserRequestCheck();
		managerUserModuleBusinessService.deleteUserRequestProcess();
	}


    /**
     * @Description: 审核薪资
     */
    public void examineSalaryRequestProcess() {
        managerUserModuleCheckService.examineSalaryRequestCheck();
        managerUserModuleBusinessService.examineSalaryRequestProcess();
    }
}
