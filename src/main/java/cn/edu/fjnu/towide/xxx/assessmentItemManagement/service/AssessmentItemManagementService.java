package cn.edu.fjnu.towide.xxx.assessmentItemManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssessmentItemManagementService {


	@Autowired
	AssessmentItemManagementCheckService assessmentItemManagementCheckService;

	@Autowired
	AssessmentItemManagementBusinessService assessmentItemManagementBusinessService;


	public void addAssessmentItemRequest() {
		assessmentItemManagementCheckService.addAssessmentItemRequestCheck();
		assessmentItemManagementBusinessService.addAssessmentItemRequestProcess();

	}

	public void getAssessmentItemRequest() {
		assessmentItemManagementCheckService.getAssessmentItemRequestCheck();
		assessmentItemManagementBusinessService.getAssessmentItemRequestProcess();
	}

    public void deleteAssessmentItemByAsIdRequest() {
		assessmentItemManagementCheckService.deleteAssessmentItemByAsIdRequestCheck();
		assessmentItemManagementBusinessService.deleteAssessmentItemByAsIdRequestProcess();
    }

    public void getAssessmentItemWithCountByAsIdRequest() {
		assessmentItemManagementCheckService.getAssessmentItemWithCountByAsIdRequestCheck();
		assessmentItemManagementBusinessService.getAssessmentItemWithCountByAsIdRequestProcess();
    }
}
