package cn.edu.fjnu.towide.czx.AuthorityGroupManagement.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.edu.fjnu.towide.czx.AuthorityGroupManagement.constant.AuthorityGroupManagementFunctionNoConstants;
import cn.edu.fjnu.towide.czx.AuthorityGroupManagement.service.AuthorityGroupManagementService;
import cn.edu.fjnu.towide.entity.ResponseData;
import cn.edu.fjnu.towide.enums.ReasonOfFailure;
import cn.edu.fjnu.towide.service.DataCenterService;
import cn.edu.fjnu.towide.util.ResponseDataUtil;

@Component
public class AuthorityGroupManagementFunctionNoDispatcher {

	@Autowired
	AuthorityGroupManagementService authorityGroupManagementService;
	@Autowired
	DataCenterService dataCenterService;

	static Logger logger = LoggerFactory.getLogger(AuthorityGroupManagementFunctionNoDispatcher.class);

	public void dispatchByFunctionNo(String functionNo, ResponseData responseData) {
		switch (functionNo) {
		// 获取权组列表
		case AuthorityGroupManagementFunctionNoConstants.GET_AUTHORITY_GROUP_LIST:
			authorityGroupManagementService.getAuthorityGroupListRequestProcess();
			break;
		//
		case AuthorityGroupManagementFunctionNoConstants.ADD_AUTHORITY_GROUP:
			authorityGroupManagementService.addAuthorityGroupRequestProcess();
			break;
		//
		case AuthorityGroupManagementFunctionNoConstants.DELETE_AUTHORITY_GROUP:
			authorityGroupManagementService.deleteAuthorityGroupRequestProcess();
			break;
		case AuthorityGroupManagementFunctionNoConstants.MODIFY_AUTHORITY_GROUP:
			authorityGroupManagementService.modifyAuthorityGroupRequestProcess();
			break;
		default:
			ResponseDataUtil.setResponseDataWithFailureInfo(responseData, ReasonOfFailure.FUNCTION_NO_ARE_INCORRECT);
			break;
		}
	}

}
