package cn.edu.fjnu.towide.xxx.authorityGroupManagement.service;

import cn.edu.fjnu.towide.dao.GroupDao;
import cn.edu.fjnu.towide.dao.GroupMembersDao;
import cn.edu.fjnu.towide.entity.User;
import cn.edu.fjnu.towide.service.DataCenterService;
import cn.edu.fjnu.towide.util.CheckVariableUtil;
import cn.edu.fjnu.towide.util.ExceptionUtil;
import cn.edu.fjnu.towide.util.RecordOperationLogUtil;
import cn.edu.fjnu.towide.xxx.authorityGroupManagement.constant.LogConstant;
import cn.edu.fjnu.towide.xxx.authorityGroupManagement.enums.ReasonOfFailure;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author CaoZhengxi
 * @date 2018年4月14日
 */
@Service
public class AuthorityGroupManagementCheckService {
	
	@Autowired
    DataCenterService dataCenterService;
	@Autowired
    GroupMembersDao groupMembersDao;
	@Autowired
    GroupDao groupDao;

	/**
	 *  获取权组列表
	 */
	public void getAuthorityGroupListRequestCheck() {

		int pageNum = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("pageNum");
		int pageSize = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName( "pageSize");
		if (CheckVariableUtil.pageParamIsIllegal(pageNum, pageSize)) {
			ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.PAGEINFO_ERROR);
		}
		dataCenterService.setData("pageNum", pageNum);
		dataCenterService.setData("pageSize", pageSize);
	}


	/**
	 *  添加权组
	 */
	public void addAuthorityGroupRequestCheck() {
		
		String groupName = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("groupName");
		String description = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName( "description");
		String remark = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName( "remark");

		if (CheckVariableUtil.stringVariableIsEmpty(groupName)) {
			ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.THE_GROUP_NAME_CANNOT_BE_EMPTY);
		}

		JSONArray permissionsJA= dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName( "permissions");

		List<String> permissions = permissionsJA.toJavaList(String.class);

		if (permissions.isEmpty()) {
			ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.THE_PERMISSIONS_CANNOT_BE_EMPTY);
		}
		dataCenterService.setData("groupName", groupName);
		dataCenterService.setData("description", description);
		dataCenterService.setData("remark", remark);
		dataCenterService.setData("permissions", permissions);

	}

	/**
	 *  删除权组检测，如果权组内有成员，禁止删除
	 */
	public void deleteAuthorityGroupRequestCheck() {
		JSONArray  groupJA=	dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("groupIdList");
		List<Long> groupIds=groupJA.toJavaList(Long.class);

		if (groupIds.isEmpty()) {
			ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.THE_GROUP_CANNOT_BE_EMPTY);
		}

		for (Long groupId : groupIds) {
			boolean result = groupMembersDao.checkMembersInGroup(groupId);
			if (result) {
				String groupName = groupDao.selectGroupNameByGtoupId(groupId);

				ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.MEMBERS_IN_THE_GROUP_ARE_NOT_ALLOWED_TO_DELETE);
				return;
			}
		}
		dataCenterService.setData("groupIds", groupIds);
	}

	/**
	 * @Description: 获取添加人员可选的权限与组
	 */
	public void getAvailableGroupsRequestCheck() {

	}
	public void getAvailablePermissionsRequestCheck() {

	}
	/**
	 *  修改权组检测
	 */
	public void modifyAuthorityGroupRequestCheck() {
		String groupIdString = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName( "groupId");
		Long groupId = CheckVariableUtil.parseLong(groupIdString,0L);
		String groupName = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName( "groupName");
		String description = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName( "description");
		String remark = dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName("remark");
		if (groupId.equals(0L)){
			ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.THE_PARAMETERS_SUBMITTED_ARE_INCORRECT);
		}

		if (CheckVariableUtil.stringVariableIsEmpty(groupName)) {
			ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.THE_GROUP_NAME_CANNOT_BE_EMPTY);
		}

		JSONArray permissionsJA= dataCenterService.getParamValueFromParamOfRequestParamJsonByParamName( "permissions");

		List<String> permissions=permissionsJA.toJavaList(String.class);
		if (permissions.isEmpty()) {
			ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.THE_PERMISSIONS_CANNOT_BE_EMPTY);
			return;
		}
		dataCenterService.setData("groupId", groupId);
		dataCenterService.setData("groupName", groupName);
		dataCenterService.setData("description", description);
		dataCenterService.setData("remark", remark);
		dataCenterService.setData("permissions", permissions);
	}

}
