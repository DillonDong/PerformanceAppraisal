package cn.edu.fjnu.towide.xxx.authorityGroupManagement.controller;

import cn.edu.fjnu.towide.entity.ResponseData;
import cn.edu.fjnu.towide.enums.ReasonOfFailure;
import cn.edu.fjnu.towide.service.DataCenterService;
import cn.edu.fjnu.towide.util.ResponseDataUtil;
import cn.edu.fjnu.towide.xxx.authorityGroupManagement.constant.AuthorityGroupManagementFunctionNoConstants;
import cn.edu.fjnu.towide.xxx.authorityGroupManagement.service.AuthorityGroupManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

            // 获取添加人员可选的权限与组
            case AuthorityGroupManagementFunctionNoConstants.GET_AVAILABLE_GROUPS:
                authorityGroupManagementService.getAvailableGroupsRequestProcess();
                break;
            case AuthorityGroupManagementFunctionNoConstants.GET_AVAILABLE_PERMISSIONS:
                authorityGroupManagementService.getAvailablePermissionsRequestProcess();
                break;
            default:
                ResponseDataUtil.setResponseDataWithFailureInfo(responseData, ReasonOfFailure.FUNCTION_NO_ARE_INCORRECT);
                break;
        }
    }

}
