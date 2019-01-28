package cn.edu.fjnu.towide.xxx.deptManagement.controller;

import cn.edu.fjnu.towide.entity.ResponseData;
import cn.edu.fjnu.towide.enums.ReasonOfFailure;
import cn.edu.fjnu.towide.util.ResponseDataUtil;
import cn.edu.fjnu.towide.xxx.deptManagement.constant.DeptManagementFunctionNoConstants;
import cn.edu.fjnu.towide.xxx.deptManagement.service.DeptManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeptManagementNoDispatcher {
    @Autowired
    DeptManagementService deptManagementService;

    public void dispatchByFunctionNo(String functionNo, ResponseData responseData) {
        switch (functionNo) {
            case DeptManagementFunctionNoConstants.ADD_DEPT_REQUEST:
                deptManagementService.addDeptRequest();
                break;
            case  DeptManagementFunctionNoConstants.GET_DEPARTMENT_LIST:
                deptManagementService.getDepartmentListRequest();
                break;
            case  DeptManagementFunctionNoConstants.ADD_ASSESSMENT_ITEM_FOR_DEPARTMENT:
                deptManagementService.addAssessmentItemForDepartmentRequest();
                break;
            case DeptManagementFunctionNoConstants.GET_ASSESSMENT_ITEMS_BY_DEPARTMENT_ID:
                deptManagementService.getAssessmentItemsByDepartmentIdRequest();
                break;
            case DeptManagementFunctionNoConstants.DELETE_DEPARTMENT_BY_DEPARTMENT_ID:
                deptManagementService.deleteDepartmentByDepartmentIdRequest();
                break;
            default:
                ResponseDataUtil.setResponseDataWithFailureInfo(responseData, ReasonOfFailure.FUNCTION_NO_ARE_INCORRECT);
                break;
        }
    }
}
