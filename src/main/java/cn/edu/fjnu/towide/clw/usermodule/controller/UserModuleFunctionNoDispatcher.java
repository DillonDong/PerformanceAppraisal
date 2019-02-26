package cn.edu.fjnu.towide.clw.usermodule.controller;

import cn.edu.fjnu.towide.xxx.assessmentItemManagement.constant.AssessmentItemManagementFunctionNoConstants;
import cn.edu.fjnu.towide.xxx.assessmentItemManagement.service.AssessmentItemManagementService;
import cn.edu.fjnu.towide.xxx.authorityGroupManagement.constant.AuthorityGroupManagementFunctionNoConstants;
import cn.edu.fjnu.towide.xxx.authorityGroupManagement.service.AuthorityGroupManagementService;
import cn.edu.fjnu.towide.xxx.deptManagement.constant.DeptManagementFunctionNoConstants;
import cn.edu.fjnu.towide.xxx.deptManagement.service.DeptManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.edu.fjnu.towide.clw.usermodule.constant.UserModuleFunctionNoConstants;
import cn.edu.fjnu.towide.clw.usermodule.service.UserModuleService;
import cn.edu.fjnu.towide.entity.ResponseData;
import cn.edu.fjnu.towide.enums.ReasonOfFailure;
import cn.edu.fjnu.towide.service.DataCenterService;
import cn.edu.fjnu.towide.util.ResponseDataUtil;

@Component
public class UserModuleFunctionNoDispatcher {
    @Autowired
    UserModuleService userModuleService;
    @Autowired
    DataCenterService dataCenterService;
    @Autowired
    AssessmentItemManagementService assessmentItemManagementService;
    @Autowired
    AuthorityGroupManagementService authorityGroupManagementService;
    @Autowired
    DeptManagementService deptManagementService;

    static Logger logger = LoggerFactory.getLogger(UserModuleFunctionNoDispatcher.class);

    public void dispatchByFunctionNo(String functionNo, ResponseData responseData) {
        switch (functionNo) {

            case UserModuleFunctionNoConstants.GET_USER_INFO:
                userModuleService.getUserInfoRequestProcess();
                break;
            case UserModuleFunctionNoConstants.IS_USER_EXIST:
                userModuleService.isUserExistRequestProcess();
                break;
            case UserModuleFunctionNoConstants.USER_EXAMINATION_ITEMS_IN_ADD:
                userModuleService.getUserExaminationItemsInAddRequestProcess();
                break;
            case UserModuleFunctionNoConstants.USER_EXAMINATION_ITEMS_IN_GET:
                userModuleService.getUserExaminationItemsInGetRequestProcess();
                break;
            case UserModuleFunctionNoConstants.DELETE_EXAMINATION_ITEMS:
                userModuleService.deleteExaminationItemsRequestProcess();
                break;

            case UserModuleFunctionNoConstants.GET_USER_DETAILED_INFO:
                userModuleService.getUserDetailedInfoRequestProcess();
                break;
            case UserModuleFunctionNoConstants.UPDATE_USER_DETAILED_INFO:
                userModuleService.updateUserDetailedInfoRequestProcess();
                break;
            case UserModuleFunctionNoConstants.ADD_EXAMINATION_ITEMS:
                userModuleService.addExaminationItemsRequestProcess();
                break;

            case UserModuleFunctionNoConstants.UPDATE_EXAMINATION_ITEMS:
                userModuleService.updateExaminationItemsRequestProcess();
                break;
            case UserModuleFunctionNoConstants.GET_GRAPH_DATA:
                userModuleService.getGraphDataRequestProcess();
                break;


            case AssessmentItemManagementFunctionNoConstants.GET_ASSESSMENT_ITEM_REQUEST:
                assessmentItemManagementService.getAssessmentItemRequest();
                break;
            // 获取添加人员可选的权限与组
            case AuthorityGroupManagementFunctionNoConstants.GET_AVAILABLE_GROUPS:
                authorityGroupManagementService.getAvailableGroupsRequestProcess();
                break;
            case  DeptManagementFunctionNoConstants.GET_DEPARTMENT_LIST:
                deptManagementService.getDepartmentListRequest();
                break;
            default:
                ResponseDataUtil.setResponseDataWithFailureInfo(responseData, ReasonOfFailure.FUNCTION_NO_ARE_INCORRECT);
                break;
        }
    }

}
