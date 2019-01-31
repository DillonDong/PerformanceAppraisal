package cn.edu.fjnu.towide.xxx.assessmentItemManagement.service;

import cn.edu.fjnu.towide.dao.AssessmentItemDao;
import cn.edu.fjnu.towide.entity.AssessmentItem;
import cn.edu.fjnu.towide.entity.ResponseData;
import cn.edu.fjnu.towide.service.DataCenterService;
import cn.edu.fjnu.towide.util.*;
import cn.edu.fjnu.towide.xxx.assessmentItemManagement.enums.ReasonOfFailure;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AssessmentItemManagementBusinessService {
    @Autowired
    private DataCenterService dataCenterService;

    @Autowired
    private AssessmentItemDao assessmentItemDao;

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
    public void addAssessmentItemRequestProcess() {
        AssessmentItem assessmentItem = dataCenterService.getData("assessmentItem");

        boolean res = assessmentItemDao.AddAssessmentItem(assessmentItem);
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        if (!res){
            ResponseDataUtil.setResponseDataWithFailureInfo(responseData, ReasonOfFailure.INSERT_IS_FAILURE);
        }
        setReturnDataOfSuccess();


    }

    public void getAssessmentItemRequestProcess() {
        Integer pageNum = dataCenterService.getData("pageNum");
        Integer pageSize = dataCenterService.getData("pageSize");
        String name = dataCenterService.getData("name");
        PageHelper.startPage(pageNum,pageSize);
        List<AssessmentItem> assessmentItemList = assessmentItemDao.getAssessmentItemList(name);

        PageInfo<AssessmentItem> pageInfo = new PageInfo<>(assessmentItemList);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("assessmentItemList",pageInfo);
        setReturnDataOfSuccess(jsonObject);

    }

    @Transactional
    public void deleteAssessmentItemByAsIdRequestProcess() {
        List<String> asId = dataCenterService.getData("asId");
        Boolean res = assessmentItemDao.DeleteAssessmentItemByAsId(asId);
        if (!res){
            ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.DELETE_IS_FAILURE);
        }
        setReturnDataOfSuccess();
    }
}
