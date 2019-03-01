package cn.edu.fjnu.towide.clw.usermodule.service;

import cn.edu.fjnu.towide.dao.*;
import cn.edu.fjnu.towide.entity.*;
import cn.edu.fjnu.towide.util.CheckVariableUtil;
import cn.edu.fjnu.towide.util.IdGenerator;
import cn.edu.fjnu.towide.vo.*;
import cn.edu.fjnu.towide.xxx.fileupload.constant.LogConstant;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

import cn.edu.fjnu.towide.clw.usermodule.enums.ReasonOfFailure;
import cn.edu.fjnu.towide.service.DataCenterService;
import cn.edu.fjnu.towide.util.ExceptionUtil;
import cn.edu.fjnu.towide.util.ResponseDataUtil;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.edu.fjnu.towide.constant.FilePathNameTemplate.uploadFileLocalPath;

@Service
public class UserModuleBusinessService {

	@Autowired
	GroupAuthoritiesDao groupAuthoritiesDao;

	@Autowired
	GroupDao groupDao;
	@Autowired
	DepartmentDao departmentDao;
	@Autowired
	DataCenterService dataCenterService;
	@Autowired
	UserDao userDao;
	@Autowired
	UserDetailDao userDetailDao;
	@Autowired
	AuthoritiesDao authoritiesDao;
	@Autowired
	GroupMembersDao groupMembersDao;

	@Autowired
	AssessmentItemDao assessmentItemDao;
	/**
	 * @Description: 返回数据封装
	 */
	public void responseUtil(String dataKey, Object dataValue) {
		ResponseData responseData = dataCenterService.getData("responseData");
		ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
		if (dataKey != null) {
			JSONObject data = new JSONObject();
			data.put(dataKey, dataValue);
			responseData.setData(data);
		}
	}
    /**
     * @Description: 获取用户信息 管理员界面进入要传username；员工界面进入不要传，直接获取当前用户
     */
	public void getUserInfoRequestProcess() {
		String startTime = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("startTime");
		String endTime = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("endTime");
		int pageNum = dataCenterService.getData("pageNum");
		int pageSize = dataCenterService.getData("pageSize");

		String username = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("username");
		if(CheckVariableUtil.stringVariableIsEmpty(username)){
			User currentLoginUser = dataCenterService.getCurrentLoginUserFromDataLocal();
			username = currentLoginUser.getUsername();
		}

		PageHelper.startPage(pageNum, pageSize);

		List<UserWages> userInfoVo=userDetailDao.getUserInfoByUsername(startTime,endTime,username);
		PageInfo<UserWages> pageResult = new PageInfo<UserWages>(userInfoVo);

		responseUtil("pageResult",pageResult);
	}

	/**
	 * @Description: 判断用户是否存在
	 */
	public void isUserExistRequestProcess() {
		String openid =  dataCenterService.getData("openid");

		boolean isUserExistCheck=userDao.isUserExist(openid);

		responseUtil("isUserExistCheck",isUserExistCheck);
	}


	/**
	 * @Description: 获取用户项目考核用于添加考核业绩
	 */
	public void getUserExaminationItemsInAddRequestProcess() {
		User currentLoginUser = dataCenterService.getCurrentLoginUserFromDataLocal();
		String username = currentLoginUser.getUsername();
		List<AssessmentItem> examinationItems=userDetailDao.getUserExaminationItemsInAdd(username);

		responseUtil("examinationItems",examinationItems);
	}


    /**
     * @Description: 在详情中获取用户项目考核
     */
    public void getUserExaminationItemsInGetRequestProcess() {
        String time = dataCenterService.getData("time");
        String username = dataCenterService.getData("username");

        List<UserAssessment> examinationItems=userDetailDao.getUserExaminationItemsInGet(username,time);

        responseUtil("examinationItems",examinationItems);
    }


    /**
     * @Description: 删除用户未审核的某月业绩考核
     */
    @Transactional
    public void deleteExaminationItemsRequestProcess() {
        String time = dataCenterService.getData("time");
        String username = dataCenterService.getData("username");
		int isExamine =userDetailDao.isExamineExaminationItems(username,time);
		if (isExamine==1) {
			ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.EXAMINE_IS_WRONG);
			return;
		}
		boolean deleteUserSalaryInfoCheck=userDetailDao.deleteUserSalaryInfo(username,time);
        if (!deleteUserSalaryInfoCheck) {
            ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.DELETE_IS_WRONG);
            return;
        }
        boolean deleteUserExaminationItemsCheck=userDetailDao.deleteUserExaminationItems(username,time);
        if (!deleteUserExaminationItemsCheck) {
            ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.DELETE_IS_WRONG);
			return;
		}
        responseUtil(null,null);
    }


    /**
     * @Description: 获取用户信息列表
     */
    public void getUserInfoListRequestProcess() {
        int pageNum = dataCenterService.getData("pageNum");
        int pageSize = dataCenterService.getData("pageSize");
        String keyword = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("keyword");

        PageHelper.startPage(pageNum, pageSize);

        List<UserInfoVo> userInfoVo=userDetailDao.getUserInfoList(keyword);
        PageInfo<UserInfoVo> pageResult = new PageInfo<UserInfoVo>(userInfoVo);

        responseUtil("pageResult",pageResult);
    }


	/**
	 * @Description: 获取用户详细信息
	 */
	public void getUserDetailedInfoRequestProcess() {
		String username = dataCenterService.getData("username");
		UserDetailInfoVo userDetailInfo=userDetailDao.getUserDetailedInfoList(username);
		userDetailInfo.setIdPre("PerformanceAppraisal/" +userDetailInfo.getIdPre());
		userDetailInfo.setIdAfter("PerformanceAppraisal/" +userDetailInfo.getIdAfter());

        Long groupId=userDetailDao.getGroupId(username);
        if(groupId!=null){
            userDetailInfo.setGroup(groupId);
        }
        responseUtil("userDetailInfo",userDetailInfo);
	}


	/**
	 * @Description: 添加绩效
	 */
	@Transactional
	public void addExaminationItemsRequestProcess() {
		JSONArray examinationItems = dataCenterService
				.getParamValueFromParamOfRequestParamJsonByParamName("examinationItemsList");
		if(examinationItems==null||examinationItems.size()==0) {
			ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.EXAMINATION_ITEMS_EMPTY);
			return;
		}
		User currentLoginUser = dataCenterService.getCurrentLoginUserFromDataLocal();
		String username = currentLoginUser.getUsername();
		//数组第一个为时间
		String time=JSONObject.parseObject(JSONObject.toJSONString(examinationItems.get(0))).getString("value");
		//数组第二个为营业额
        Double turnover=null;
        try {
            turnover=JSONObject.parseObject(JSONObject.toJSONString(examinationItems.get(1))).getDouble("value");
        }catch (Exception e){
            ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.COUNT_ERR);
        }
        if(turnover==null){
            ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.TURNOVER_ERR);
        }
        boolean istimeExist=userDao.isTimeExist(username,time);
		if(istimeExist){
			ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.EXAMINATION_ITEMS_IS_EXIST);
			return;
		}

		Map<String,Double> countMap=new HashMap<>();
		for(int i=2;i<examinationItems.size();i++){
			String examinationItemId=JSONObject.parseObject(JSONObject.toJSONString(examinationItems.get(i))).getString("examinationItemId");
			Double value=null;
            try {
                value=JSONObject.parseObject(JSONObject.toJSONString(examinationItems.get(i))).getDouble("value");
            }catch (Exception e){
                ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.COUNT_ERR);
            }
            if(value==null){
                ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.EXAMINATION_ITEM_ERR);
            }
			CountVo bigger=userDetailDao.getBiggerCount(examinationItemId,value);
			CountVo smaller=userDetailDao.getSmallerCount(examinationItemId,value);

			CountVo biggest=userDetailDao.getBiggestCount(examinationItemId);
			CountVo smallest=userDetailDao.getSmallestCount(examinationItemId);
			double count=0;
			if(bigger==null ||smaller==null){
				if(value>biggest.getLevel()){
					count=biggest.getCount();
				}
				if(value<=smallest.getLevel()){
					count=smallest.getCount();
				}

			}else{
				//计算比例
				double bili=(bigger.getLevel()-smaller.getLevel()) / (bigger.getCount()-smaller.getCount());
				//计算分数
				count=(value-smaller.getLevel()) / bili+smaller.getCount();
			}

			//把考核项目和分数放到map中
			countMap.put(examinationItemId,count);
			//把考核项目和分数存到user_assessment表中
			UserAssessment userAssessment=new UserAssessment();
			userAssessment.setId(IdGenerator.getId());
			userAssessment.setTime(time);
			String assessmentItemName=userDetailDao.getAssessmentItemName(examinationItemId);
			userAssessment.setAssessmentItem(assessmentItemName);
			BigDecimal b = new BigDecimal(count);
			count = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			userAssessment.setCount(count);
            userAssessment.setLevel(value);
			userAssessment.setUserId(username);
			boolean addSuccess=userDetailDao.addAssessmentItemAndCount(userAssessment);
			if(!addSuccess){
				ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.ADD_EXAMINATION_ITEMS_WRONG);
				return;
			}
		}
		//得到部门和薪水
		DeptSalaryVo deptSalaryVo=userDetailDao.getDeptSalaryByUserName(username);

		List<DeptAsVo> assessmentList=userDetailDao.getDeptAssessmentList(deptSalaryVo.getDeptId());
		//计算总分
		double totalCount=0;
		for (Map.Entry<String, Double> myItems : countMap.entrySet()) {
			for (DeptAsVo deptAsVo : assessmentList) {
				if(myItems.getKey().equals(deptAsVo.getAsId())){
					totalCount += (myItems.getValue()*deptAsVo.getWeight()*0.01);
				}
			}
		}

		//
		Parameter parameter=userDetailDao.getParameter();
		//计算绩效奖
		double meritsPay=0;
		if(totalCount>=parameter.getJxqjLeft() && totalCount<=parameter.getJxqjRight()){
			double bili1=(parameter.getJxqjRight()-parameter.getJxqjLeft()) / (100 - parameter.getJxblLeft());
			//计算正比递增的比例
			double jxbili=(totalCount-parameter.getJxqjLeft()) / bili1+parameter.getJxblLeft();
			meritsPay=parameter.getMeritsPay()*jxbili*0.01;
		}else if (totalCount > parameter.getJxqjRight()){
			//大于右区间则拿到全部的绩效奖
			meritsPay=parameter.getMeritsPay();
		}

		// 计算抽成
		double percentage=0;
		//营业额抽成金额
		double turnoverPercentage=(turnover-parameter.getMinimumTurnover())*0.01;
		//
		if(totalCount>=parameter.getCcqjLeft() && totalCount<=parameter.getCcqjMiddle()){
			double bili2=(parameter.getCcqjMiddle()-parameter.getCcqjLeft()) / (parameter.getCcqjzzRight() - parameter.getCcqjzzLeft());
			//计算左中正比递增的比例
			double ccbili1=(totalCount-parameter.getCcqjLeft()) / bili2 + parameter.getCcqjzzLeft();
			percentage=turnoverPercentage*ccbili1*0.01;
		}else if (totalCount>parameter.getCcqjMiddle() && totalCount<=parameter.getCcqjRight()){
			double bili3=(parameter.getCcqjRight()-parameter.getCcqjMiddle()) / (parameter.getCcqjzyRight() - parameter.getCcqjzyLeft());
			//计算中右正比递增的比例
			double ccbili2=(totalCount-parameter.getCcqjMiddle()) / bili3 + parameter.getCcqjzyLeft();
			percentage=turnoverPercentage*ccbili2*0.01;
		}
		//总工资
		double total_wages=deptSalaryVo.getBaseSalary()+meritsPay+percentage;
		BigDecimal b = new BigDecimal(turnover);
		turnover = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		BigDecimal e = new BigDecimal(totalCount);
		totalCount = e.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		BigDecimal c = new BigDecimal(meritsPay);
		meritsPay = c.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		BigDecimal f = new BigDecimal(percentage);
		percentage = f.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		BigDecimal d = new BigDecimal(total_wages);
		total_wages = d.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		UserWages userWages=new UserWages();
		userWages.setId(IdGenerator.getId());
		userWages.setUserId(username);
		userWages.setTurnover(turnover);
		userWages.setTotalScore(totalCount);
		userWages.setMeritsPay(meritsPay);
		userWages.setPercentage(percentage);
		userWages.setTotalWages(total_wages);
		userWages.setTime(time);
		userWages.setExamine(0);
		boolean addIsSuccess=userDetailDao.addUserWages(userWages);
		if(!addIsSuccess){
			ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.ADD_WAGE_WRONG);
			return;
		}

		responseUtil(null,null);
	}

	/**
	 * @Description: 更新绩效
	 */
	@Transactional
	public void updateExaminationItemsRequestProcess() {
		JSONArray examinationItems = dataCenterService
				.getParamValueFromParamOfRequestParamJsonByParamName("examinationItemsList");
		if(examinationItems==null||examinationItems.size()==0) {
			ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.EXAMINATION_ITEMS_EMPTY);
			return;
		}
		User currentLoginUser = dataCenterService.getCurrentLoginUserFromDataLocal();
		String username = currentLoginUser.getUsername();
		//数组第一个为时间
		String time=JSONObject.parseObject(JSONObject.toJSONString(examinationItems.get(0))).getString("value");
		//数组第二个为营业额
		Double turnover=JSONObject.parseObject(JSONObject.toJSONString(examinationItems.get(1))).getDouble("value");
        //先删除在添加
        boolean deleteExaminationItemsSuccess=userDetailDao.deleteUserExaminationItems(username,time);
        if(!deleteExaminationItemsSuccess){
            ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.UPDATE_EXAMINATION_ITEMS_WRONG);
            return;
        }
		Map<String,Double> countMap=new HashMap<>();
		for(int i=2;i<examinationItems.size();i++){
			String examinationItemId=JSONObject.parseObject(JSONObject.toJSONString(examinationItems.get(i))).getString("examinationItemId");
			Double value=JSONObject.parseObject(JSONObject.toJSONString(examinationItems.get(i))).getDouble("value");
			CountVo bigger=userDetailDao.getBiggerCount(examinationItemId,value);
			CountVo smaller=userDetailDao.getSmallerCount(examinationItemId,value);

			CountVo biggest=userDetailDao.getBiggestCount(examinationItemId);
			CountVo smallest=userDetailDao.getSmallestCount(examinationItemId);
			double count=0;
			if(bigger==null ||smaller==null){
				if(value>biggest.getLevel()){
					count=biggest.getCount();
				}
				if(value<=smallest.getLevel()){
					count=smallest.getCount();
				}

			}else{
				//计算比例
				double bili=(bigger.getLevel()-smaller.getLevel()) / (bigger.getCount()-smaller.getCount());
				//计算分数
				count=(value-smaller.getLevel()) / bili+smaller.getCount();
			}

			//把考核项目和分数放到map中
			countMap.put(examinationItemId,count);
			//把考核项目和分数存到user_assessment表中
			UserAssessment userAssessment=new UserAssessment();
			userAssessment.setId(IdGenerator.getId());
			userAssessment.setTime(time);
			String assessmentItemName=userDetailDao.getAssessmentItemName(examinationItemId);
			userAssessment.setAssessmentItem(assessmentItemName);
			BigDecimal b = new BigDecimal(count);
			count = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			userAssessment.setCount(count);
            userAssessment.setLevel(value);

			userAssessment.setUserId(username);
			//先删除在添加
			boolean addSuccess=userDetailDao.addAssessmentItemAndCount(userAssessment);
			if(!addSuccess){
				ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.UPDATE_EXAMINATION_ITEMS_WRONG);
				return;
			}
		}
		//得到部门和薪水
		DeptSalaryVo deptSalaryVo=userDetailDao.getDeptSalaryByUserName(username);

		List<DeptAsVo> assessmentList=userDetailDao.getDeptAssessmentList(deptSalaryVo.getDeptId());
		//计算总分
		double totalCount=0;
		for (Map.Entry<String, Double> myItems : countMap.entrySet()) {
			for (DeptAsVo deptAsVo : assessmentList) {
				if(myItems.getKey().equals(deptAsVo.getAsId())){
					totalCount += (myItems.getValue()*deptAsVo.getWeight()*0.01);
				}
			}
		}

		//
		Parameter parameter=userDetailDao.getParameter();
		//计算绩效奖
		double meritsPay=0;
		if(totalCount>=parameter.getJxqjLeft() && totalCount<=parameter.getJxqjRight()){
			double bili1=(parameter.getJxqjRight()-parameter.getJxqjLeft()) / (100 - parameter.getJxblLeft());
			//计算正比递增的比例
			double jxbili=(totalCount-parameter.getJxqjLeft()) / bili1+parameter.getJxblLeft();
			meritsPay=parameter.getMeritsPay()*jxbili*0.01;
		}else if (totalCount > parameter.getJxqjRight()){
			//大于右区间则拿到全部的绩效奖
			meritsPay=parameter.getMeritsPay();
		}

		// 计算抽成
		double percentage=0;
		//营业额抽成金额
		double turnoverPercentage=(turnover-parameter.getMinimumTurnover())*0.01;
		//
		if(totalCount>=parameter.getCcqjLeft() && totalCount<=parameter.getCcqjMiddle()){
			double bili2=(parameter.getCcqjMiddle()-parameter.getCcqjLeft()) / (parameter.getCcqjzzRight() - parameter.getCcqjzzLeft());
			//计算左中正比递增的比例
			double ccbili1=(totalCount-parameter.getCcqjLeft()) / bili2 + parameter.getCcqjzzLeft();
			percentage=turnoverPercentage*ccbili1*0.01;
		}else if (totalCount>parameter.getCcqjMiddle() && totalCount<=parameter.getCcqjRight()){
			double bili3=(parameter.getCcqjRight()-parameter.getCcqjMiddle()) / (parameter.getCcqjzyRight() - parameter.getCcqjzyLeft());
			//计算中右正比递增的比例
			double ccbili2=(totalCount-parameter.getCcqjMiddle()) / bili3 + parameter.getCcqjzyLeft();
			percentage=turnoverPercentage*ccbili2*0.01;
		}
		//总工资
		double total_wages=deptSalaryVo.getBaseSalary()+meritsPay+percentage;
		BigDecimal b = new BigDecimal(turnover);
		turnover = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		BigDecimal e = new BigDecimal(totalCount);
		totalCount = e.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		BigDecimal c = new BigDecimal(meritsPay);
		meritsPay = c.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		BigDecimal f = new BigDecimal(percentage);
		percentage = f.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		BigDecimal d = new BigDecimal(total_wages);
		total_wages = d.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		UserWages userWages=new UserWages();
		userWages.setId(IdGenerator.getId());
		userWages.setUserId(username);
		userWages.setTurnover(turnover);
		userWages.setTotalScore(totalCount);
		userWages.setMeritsPay(meritsPay);
		userWages.setPercentage(percentage);
		userWages.setTotalWages(total_wages);
		userWages.setTime(time);
		userWages.setExamine(0);
		//先删除在添加
		boolean deleteUserSalaryIsSuccess=userDetailDao.deleteUserSalaryInfo(username,time);
		boolean addIsSuccess=userDetailDao.addUserWages(userWages);
		if(!addIsSuccess|| !deleteUserSalaryIsSuccess){
			ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.ADD_WAGE_WRONG);
			return;
		}

		responseUtil(null,null);
	}

	/**
	 * @Description: 更新用户详细信息
	 */
	@Transactional
	public void updateUserDetailedInfoRequestProcess() {
		UserDetailInfoVo userDetailInfo = dataCenterService.getData("userDetailInfo");
		//boolean deleteUserFromGroupMembers=groupMembersDao.deleteUserFromGroupMembers(userDetailInfo.getUsername());
		boolean addGroupMembers=groupMembersDao.addGroupMembers(userDetailInfo.getUsername(),userDetailInfo.getGroup());
		if (!addGroupMembers) {
			ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.ADD_GROUPS_WRONG);
			return;
		}
		int groupExist=groupMembersDao.isGroupExist(userDetailInfo.getUsername());
		if (groupExist>=2) {
			ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.GROUP_EXIST);
			return;
		}
		boolean updateUserDetailResult =userDetailDao.updateUserDetailedInfo(userDetailInfo);
		if (!updateUserDetailResult) {
			ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.UPDATE_ERROR);
			return;
		}
		responseUtil(null,null);
	}

	/**
	 * @Description: 删除用户
	 */
	public void deleteUserRequestProcess() {
		String username = dataCenterService.getData("username");
		boolean deleteUser =userDetailDao.deleteUser(username);
		if (!deleteUser) {
			ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.DELETE_ERROR);
			return;
		}
		responseUtil(null,null);
	}


	/**
	 * @Description: 根据时间范围获得考核项柱形图数据
	 */
	public void getGraphDataRequestProcess() {
		String examinationItem = dataCenterService.getData("examinationItem");
		String username = dataCenterService.getData("username");
		String startTime = dataCenterService.getData("startTime");
		String endTime = dataCenterService.getData("endTime");
		List<GraphVo> graphVoList=userDetailDao.getGraphData(startTime, endTime, username, examinationItem);

		responseUtil("graphVoList",graphVoList);
	}

    public void getAssessmentItemRequestProcess() {
		List<AssessmentItem> assessmentItemList = assessmentItemDao.getAssessmentItemList(null);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("assessmentItemList",assessmentItemList);
		setReturnDataOfSuccess(jsonObject);

	}

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

	public void getAvailableGroupsRequestProcess() {
		List<Group> groups = groupDao.getGroupList();

		JSONObject data = new JSONObject();
		data.put("groups", groups);

		ResponseData responseData = dataCenterService.getData("responseData");
		ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
		responseData.setData(data);
	}

	public void getDepartmentListRequestProcess() {
		List<Department> departmentList= departmentDao.GetDepartmentList(null);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("departmentList",departmentList);
		setReturnDataOfSuccess(jsonObject);
	}

	public void getAuthorityGroupListRequestProcess() {
		List<Group> groups = groupDao.getGroupList();

		for (Group group : groups) {
			List<GroupAuthorities> groupAuthorities = groupAuthoritiesDao.getAuthoritiesByGroupId(group.getId());
			group.setGroupAuthorities(groupAuthorities);
		}
		JSONObject data = new JSONObject();
		data.put("groups", groups);

		ResponseData responseData=dataCenterService.getResponseDataFromDataLocal();

		ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
		responseData.setData(data);
	}
}
