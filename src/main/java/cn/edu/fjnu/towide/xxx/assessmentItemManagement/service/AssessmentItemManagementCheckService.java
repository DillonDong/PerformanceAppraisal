package cn.edu.fjnu.towide.xxx.assessmentItemManagement.service;

import cn.edu.fjnu.towide.dao.AssessmentItemDao;
import cn.edu.fjnu.towide.entity.AssessmentItem;
import cn.edu.fjnu.towide.service.DataCenterService;
import cn.edu.fjnu.towide.util.CheckVariableUtil;
import cn.edu.fjnu.towide.util.ExceptionUtil;
import cn.edu.fjnu.towide.util.IdGenerator;
import cn.edu.fjnu.towide.xxx.assessmentItemManagement.enums.ReasonOfFailure;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssessmentItemManagementCheckService {

	@Autowired
    DataCenterService dataCenterService;
	@Autowired
	private AssessmentItemDao assessmentItemDao;

    public void addAssessmentItemRequestCheck() {

        JSONObject jsonObject = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("assessmentItem");
        if (jsonObject==null){
            ExceptionUtil.throwRequestFailureException();
        }
        AssessmentItem assessmentItem = jsonObject.toJavaObject(AssessmentItem.class);
        if (CheckVariableUtil.stringVariableIsEmpty(assessmentItem.getName())){
            ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.NAME_IS_EMPTY);
        }
        if (CheckVariableUtil.stringVariableIsEmpty(assessmentItem.getId())){
            assessmentItem.setId(IdGenerator.getId());
        }
        dataCenterService.setData("assessmentItem",assessmentItem);

    }

    public void getAssessmentItemRequestCheck() {
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

    public void deleteAssessmentItemByAsIdRequestCheck() {
        JSONArray jsonArray = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("asId");
        if (jsonArray==null){
            ExceptionUtil.throwRequestFailureException();
        }
        List<String> asId = jsonArray.toJavaList(String.class);

        if (assessmentItemDao.GetAssessmentItemNumberByAsId(asId)>0){
            ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.ASSESSMENT_ITEM_IS_NOT_DELETE);
        }
        dataCenterService.setData("asId",asId);
    }
}
