package cn.edu.fjnu.towide.xxx.assessmentItemManagement.service;

import cn.edu.fjnu.towide.dao.AssessmentItemDao;
import cn.edu.fjnu.towide.entity.AssessmentItem;
import cn.edu.fjnu.towide.entity.ResponseData;
import cn.edu.fjnu.towide.service.DataCenterService;
import cn.edu.fjnu.towide.util.*;
import cn.edu.fjnu.towide.xxx.assessmentItemManagement.enums.ReasonOfFailure;
import cn.edu.fjnu.towide.xxx.assessmentItemManagement.vo.CountVo;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
        List<CountVo> count = dataCenterService.getData("count");

        boolean res = assessmentItemDao.AddAssessmentItem(assessmentItem);
        ResponseData responseData = dataCenterService.getResponseDataFromDataLocal();
        if (!res){
            ResponseDataUtil.setResponseDataWithFailureInfo(responseData, ReasonOfFailure.INSERT_IS_FAILURE);
            return;
        }

        assessmentItemDao.DeleteCountByAsId(assessmentItem.getId());

        Boolean res2 = assessmentItemDao.InsertAssessmentItemCount(count,assessmentItem.getId());
        if (!res2) {
            ResponseDataUtil.setResponseDataWithFailureInfo(responseData, ReasonOfFailure.INSERT_IS_FAILURE);
            return;
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
            return;
        }
        setReturnDataOfSuccess();
    }

    public void getAssessmentItemWithCountByAsIdRequestProcess() {
        String asId = dataCenterService.getData("asId");

        List<CountVo> count = assessmentItemDao.GetAssessmentItemLevelAndCountByAsId(asId);
        AssessmentItem assessmentItem = assessmentItemDao.GetAssessmentItemByAsId(asId);

        count= count.stream().sorted(Comparator.comparing(CountVo::getLevel).reversed()).collect(Collectors.toList());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("count",count);
        jsonObject.put("assessmentItem",assessmentItem);
        setReturnDataOfSuccess(jsonObject);

    }
}
