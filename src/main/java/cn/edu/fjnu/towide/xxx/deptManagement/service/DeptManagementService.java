package cn.edu.fjnu.towide.xxx.deptManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeptManagementService {


	@Autowired
	DeptManagementCheckService deptManagementCheckService;

	@Autowired
	DeptManagementBusinessService deptManagementBusinessService;


	public void addDeptRequest() {
		deptManagementCheckService.addDeptRequestCheck();
		deptManagementBusinessService.addDeptRequestProcess();
	}

	public void getDepartmentListRequest() {
		deptManagementCheckService.getDepartmentListRequestCheck();
		deptManagementBusinessService.getDepartmentListRequestProcess();
	}

    public void addAssessmentItemForDepartmentRequest() {
		deptManagementCheckService.addAssessmentItemForDepartmentRequestCheck();
		deptManagementBusinessService.addAssessmentItemForDepartmentRequestProcess();
    }

	public void getAssessmentItemsByDepartmentIdRequest() {
		deptManagementCheckService.getAssessmentItemsByDepartmentIdRequestCheck();
		deptManagementBusinessService.getAssessmentItemsByDepartmentIdRequestProcess();
	}

	public void deleteDepartmentByDepartmentIdRequest() {
		deptManagementCheckService.deleteDepartmentByDepartmentIdRequestCheck();
		deptManagementBusinessService.deleteDepartmentByDepartmentIdRequestProcess();
	}
}
