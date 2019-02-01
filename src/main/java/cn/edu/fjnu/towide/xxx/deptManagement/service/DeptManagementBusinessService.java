package cn.edu.fjnu.towide.xxx.deptManagement.service;

import cn.edu.fjnu.towide.dao.DepartmentDao;
import cn.edu.fjnu.towide.entity.Department;
import cn.edu.fjnu.towide.entity.ResponseData;
import cn.edu.fjnu.towide.service.DataCenterService;
import cn.edu.fjnu.towide.util.ExceptionUtil;
import cn.edu.fjnu.towide.util.ResponseDataUtil;
import cn.edu.fjnu.towide.xxx.deptManagement.enums.ReasonOfFailure;
import cn.edu.fjnu.towide.xxx.deptManagement.vo.AssessmentItemVo;
import cn.edu.fjnu.towide.xxx.deptManagement.vo.AssessmentItemWithWeightVo;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@SuppressWarnings("all")
@Service
public class DeptManagementBusinessService {
    @Autowired
    private DataCenterService dataCenterService;


    @Autowired
    private DepartmentDao departmentDao;

    /**
     * 设置成功返回信息
     */
    private void setReturnDataOfSuccess() {

        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
    }

    /**
     * 设置成功返回信息
     */
    private void setReturnDataOfSuccess(JSONObject jsonObject) {

        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        responseData.setData(jsonObject);
        ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
    }

    @Transactional
    public void addDeptRequestProcess() {
        Department department = dataCenterService.getData("department");
        boolean res = departmentDao.AddDepartment(department);
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        if (!res) {
            ResponseDataUtil.setResponseDataWithFailureInfo(responseData, ReasonOfFailure.INSERT_IS_FAILURE);
            return;
        }
        List<AssessmentItemVo> assessmentItems = dataCenterService.getData("assessmentItems");
        String deptId = department.getId();

        if (assessmentItems.size()==0){
            setReturnDataOfSuccess();
            return;
        }
        departmentDao.DeleteAssessmentItemByDepartmentId(deptId);
        Boolean res2 = departmentDao.InsertAssessmentItems(assessmentItems, deptId);
        if (!res2) {
            ResponseDataUtil.setResponseDataWithFailureInfo(responseData, ReasonOfFailure.INSERT_IS_FAILURE);
            return;
        }
        setReturnDataOfSuccess();
    }

    public void getDepartmentListRequestProcess() {
        Integer pageNum = dataCenterService.getData("pageNum");
        Integer pageSize = dataCenterService.getData("pageSize");
        String name = dataCenterService.getData("name");

        PageHelper.startPage(pageNum,pageSize);
        List<Department> departmentList= departmentDao.GetDepartmentList(name);
        PageInfo<Department> page = new PageInfo<>(departmentList);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("departmentList",page);
        setReturnDataOfSuccess(jsonObject);
    }



    public void getAssessmentItemsByDepartmentIdRequestProcess() {
        String deptId = dataCenterService.getData("deptId");

        List<AssessmentItemWithWeightVo> assessmentItems = departmentDao.GetAssessmentItemsByDepartmentId(deptId);

        Department department = departmentDao.GetDepartmentByDepartmentId(deptId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("department",department);
        jsonObject.put("assessmentItems",assessmentItems);
        setReturnDataOfSuccess(jsonObject);

    }

    @Transactional
    public void deleteDepartmentByDepartmentIdRequestProcess() {
        List<String> deptId = dataCenterService.getData("deptId");
        departmentDao.DeleteAssessmentItemByDeptId(deptId);
        Boolean res = departmentDao.DeleteDepartmentByDeptId(deptId);
        if (!res){
            ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.DELETE_IS_FAILURE);
        }
        setReturnDataOfSuccess();
    }
}
