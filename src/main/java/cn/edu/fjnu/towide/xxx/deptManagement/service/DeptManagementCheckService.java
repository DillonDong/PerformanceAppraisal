package cn.edu.fjnu.towide.xxx.deptManagement.service;

import cn.edu.fjnu.towide.entity.Department;
import cn.edu.fjnu.towide.service.DataCenterService;
import cn.edu.fjnu.towide.util.CheckVariableUtil;
import cn.edu.fjnu.towide.util.ExceptionUtil;
import cn.edu.fjnu.towide.util.IdGenerator;
import cn.edu.fjnu.towide.xxx.deptManagement.enums.ReasonOfFailure;
import cn.edu.fjnu.towide.xxx.deptManagement.vo.AssessmentItemVo;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptManagementCheckService {

	@Autowired
    DataCenterService dataCenterService;


    public void addDeptRequestCheck() {
        JSONObject jsonObject = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("dept");

        if (jsonObject==null){
            ExceptionUtil.throwRequestFailureException();
        }
        Department department = jsonObject.toJavaObject(Department.class);
        if (CheckVariableUtil.stringVariableIsEmpty(department.getName())){
            ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.NAME_IS_EMPTY);
        }

        if (CheckVariableUtil.stringVariableIsEmpty(department.getId())){
            department.setId(IdGenerator.getId());
        }
        dataCenterService.setData("department",department);


    }

    public void getDepartmentListRequestCheck() {
        Integer pageNum = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageNum");
        Integer pageSize = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageSize");
        String name = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("name");
        if(CheckVariableUtil.pageParamIsIllegal(pageNum,pageSize)){
            ExceptionUtil.throwRequestFailureException();
        }

        if(CheckVariableUtil.stringVariableIsEmpty(name)){
            name = null;
        }
        dataCenterService.setData("pageNum",pageNum);
        dataCenterService.setData("pageSize",pageSize);
        dataCenterService.setData("name",name);
    }


    public void addAssessmentItemForDepartmentRequestCheck() {
        JSONArray jsonArray =dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("assessmentItems");
        String deptId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("deptId");
        if (CheckVariableUtil.stringVariableIsEmpty(deptId)){
            ExceptionUtil.throwRequestFailureException();
        }
        if(jsonArray==null){
            ExceptionUtil.throwRequestFailureException();
        }
        List<AssessmentItemVo> assessmentItems = jsonArray.toJavaList(AssessmentItemVo.class);

        assessmentItems.stream().forEach(
                assessmentItemVo -> {
                    if (assessmentItemVo.getWeight()==null||assessmentItemVo.getWeight()<=0){
                        ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.WEIGHT_SHOULD_OVER_ZERO);
                    }
                }
        );

        dataCenterService.setData("deptId",deptId);
        dataCenterService.setData("assessmentItems",assessmentItems);

    }

    public void getAssessmentItemsByDepartmentIdRequestCheck() {
        String deptId = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("deptId");
        if (CheckVariableUtil.stringVariableIsEmpty(deptId)){
            ExceptionUtil.throwRequestFailureException();
        }
        dataCenterService.setData("deptId",deptId);

    }

    public void deleteDepartmentByDepartmentIdRequestCheck() {
        JSONArray jsonArray = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("deptId");
        if (jsonArray==null){
            ExceptionUtil.throwRequestFailureException();
        }
        List<String> deptId = jsonArray.toJavaList(String.class);
        dataCenterService.setData("deptId",deptId);
    }
}
