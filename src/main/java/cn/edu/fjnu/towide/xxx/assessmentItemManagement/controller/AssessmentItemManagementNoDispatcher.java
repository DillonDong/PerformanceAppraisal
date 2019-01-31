package cn.edu.fjnu.towide.xxx.assessmentItemManagement.controller;

import cn.edu.fjnu.towide.entity.ResponseData;
import cn.edu.fjnu.towide.enums.ReasonOfFailure;
import cn.edu.fjnu.towide.util.ResponseDataUtil;
import cn.edu.fjnu.towide.xxx.assessmentItemManagement.constant.AssessmentItemManagementFunctionNoConstants;
import cn.edu.fjnu.towide.xxx.assessmentItemManagement.service.AssessmentItemManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssessmentItemManagementNoDispatcher {
    @Autowired
    AssessmentItemManagementService assessmentItemManagementService;

    public void dispatchByFunctionNo(String functionNo, ResponseData responseData) {
        switch (functionNo) {
            case AssessmentItemManagementFunctionNoConstants.ADD_ASSESSMENT_ITEM_REQUEST:
                assessmentItemManagementService.addAssessmentItemRequest();
                break;
            case AssessmentItemManagementFunctionNoConstants.GET_ASSESSMENT_ITEM_REQUEST:
                assessmentItemManagementService.getAssessmentItemRequest();
                break;
            case AssessmentItemManagementFunctionNoConstants.DELETE_ASSESSMENT_ITEM_BY_ASID:
                assessmentItemManagementService.deleteAssessmentItemByAsIdRequest();
                break;
            case AssessmentItemManagementFunctionNoConstants.GET_ASSESSMENT_ITEM_WITH_COUNT_BY_ASID:
                assessmentItemManagementService.getAssessmentItemWithCountByAsIdRequest();
                break;
            default:
                ResponseDataUtil.setResponseDataWithFailureInfo(responseData, ReasonOfFailure.FUNCTION_NO_ARE_INCORRECT);
                break;
        }
    }
}
