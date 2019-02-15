package cn.edu.fjnu.towide.czx.AuthorityGroupManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.fjnu.towide.service.DataCenterService;

@Service
public class AuthorityGroupManagementService {

	@Autowired
	DataCenterService dataCenterService;
	@Autowired
	AuthorityGroupManagementCheckService authorityGroupManagementCheckService;
	@Autowired
	AuthorityGroupManagementBusinessService authorityGroupManagementBusinessService;

	/**  
	 *  获取权组列表
	 */  
	public void getAuthorityGroupListRequestProcess() {
		authorityGroupManagementCheckService.getAuthorityGroupListRequestCheck();
		authorityGroupManagementBusinessService.getAuthorityGroupListRequestProcess();
		
	}

	/**  
	 *  添加权组
	 */  
	public void addAuthorityGroupRequestProcess() {
		authorityGroupManagementCheckService.addAuthorityGroupRequestCheck();
		authorityGroupManagementBusinessService.addAuthorityGroupRequestProcess();
	}

	/**  
	 *  删除权组
	 */  
	public void deleteAuthorityGroupRequestProcess() {
		authorityGroupManagementCheckService.deleteAuthorityGroupRequestCheck();
		authorityGroupManagementBusinessService.deleteAuthorityGroupRequestProcess();
	}

	/**  
	 *  修改权组
	 */  
	public void modifyAuthorityGroupRequestProcess() {
		authorityGroupManagementCheckService.modifyAuthorityGroupRequestCheck();
		authorityGroupManagementBusinessService.modifyAuthorityGroupRequestProcess();
	}


}
