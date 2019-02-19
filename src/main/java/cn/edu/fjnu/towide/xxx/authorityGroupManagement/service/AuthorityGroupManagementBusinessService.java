package cn.edu.fjnu.towide.xxx.authorityGroupManagement.service;

import cn.edu.fjnu.towide.dao.*;
import cn.edu.fjnu.towide.entity.Authority;
import cn.edu.fjnu.towide.entity.Group;
import cn.edu.fjnu.towide.entity.GroupAuthorities;
import cn.edu.fjnu.towide.entity.ResponseData;
import cn.edu.fjnu.towide.service.DataCenterService;
import cn.edu.fjnu.towide.util.ExceptionUtil;
import cn.edu.fjnu.towide.util.ResponseDataUtil;
import cn.edu.fjnu.towide.xxx.authorityGroupManagement.enums.ReasonOfFailure;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuthorityGroupManagementBusinessService {

	@Autowired
    DataCenterService dataCenterService;
	@Autowired
    UserDao userDao;
	@Autowired
    AuthoritiesDao authoritiesDao;
	@Autowired
    GroupMembersDao groupMembersDao;
	@Autowired
    GroupDao groupDao;
	@Autowired
    GroupAuthoritiesDao groupAuthoritiesDao;

	/**
	 *  获取权组列表
	 */
	public void getAuthorityGroupListRequestProcess() {
		int pageNum = dataCenterService.getData("pageNum");
		int pageSize = dataCenterService.getData("pageSize");

		PageHelper.startPage(pageNum, pageSize);
		List<Group> groups = groupDao.getGroupList();

		for (Group group : groups) {
			List<GroupAuthorities> groupAuthorities = groupAuthoritiesDao.getAuthoritiesByGroupId(group.getId());
			group.setGroupAuthorities(groupAuthorities);
//			List<User> users = groupMembersDao.getGroupMemberByGroupId(group.getId());
//			group.setUsers(users);
		}
		PageInfo<Group> pageResult = new PageInfo<Group>(groups);
		JSONObject data = new JSONObject();
		data.put("groups", pageResult);

		ResponseData responseData=dataCenterService.getResponseDataFromDataLocal();

		ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
		responseData.setData(data);
	}

	/**
	 *  添加权组
	 */
	@Transactional
	public void addAuthorityGroupRequestProcess() {
		String groupName = dataCenterService.getData("groupName");
		String description = dataCenterService.getData("description");
		String remark = dataCenterService.getData("remark");
		List<String> permissions = dataCenterService.getData("permissions");

		boolean result = false;
		boolean addResult = false;
		long id = 0;

		while (!addResult) {
			long groupAmount = groupDao.getGroupAmount();
			System.out.println("------groupAmount-------"+groupAmount);
			try {
				if (groupAmount == 0) {
					id = 1;
				} else {
					long maxId = groupDao.selectMaxGroupId();
					id = maxId + 1;
				}

				addResult = groupDao.addGroup(id, groupName, description, remark);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (addResult && (!permissions.isEmpty())) {
			for (String permission : permissions) {
				result = groupAuthoritiesDao.addAuthorityGroup(id, permission);
			}
		}

		ResponseData responseData=dataCenterService.getResponseDataFromDataLocal();

		if (!result) {
			ExceptionUtil.throwRequestFailureException();
		}
		ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);

	}

	/**
	 *  删除一个或多个权组
	 */
	@Transactional
	public void deleteAuthorityGroupRequestProcess() {
		List<Long> groupIds = dataCenterService.getData("groupIds");
		ResponseData responseData=dataCenterService.getResponseDataFromDataLocal();

		for (Long groupId : groupIds) {
			String groupName = groupDao.selectGroupNameByGtoupId(groupId);

			boolean result1 = groupAuthoritiesDao.deleteGroupAuthoritiesByGroupId(groupId);
			boolean result = groupDao.deleteGroupByGroupId(groupId);

			if (!(result && result1)) {
				ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.THE_PARAMETERS_SUBMITTED_ARE_INCORRECT);
				return;
			}
		}
		ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
	}

	/**
	 *  修改权组
	 */
	@Transactional
	public void modifyAuthorityGroupRequestProcess() {
		Long groupId = dataCenterService.getData("groupId");
		String groupName = dataCenterService.getData("groupName");
		String description = dataCenterService.getData("description");
		String remark = dataCenterService.getData("remark");
		List<String> permissions = dataCenterService.getData("permissions");

		boolean addAuthorityResult = false;
		boolean updateResult = groupDao.updateGroup(groupId, groupName, description, remark);
		groupAuthoritiesDao.deleteGroupAuthoritiesByGroupId(groupId);

		for (String permission : permissions) {
			addAuthorityResult = groupAuthoritiesDao.addAuthorityGroup(groupId, permission);
		}

		ResponseData responseData=dataCenterService.getResponseDataFromDataLocal();

		if (!(addAuthorityResult && updateResult)) {
			ExceptionUtil.setFailureMsgAndThrow(ReasonOfFailure.THE_PARAMETERS_SUBMITTED_ARE_INCORRECT);
			return;
		}
		ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);

	}


	/**
	 * @Description:获取添加人员可选的权限与组
	 */
	public void getAvailableGroupsRequestProcess() {
		List<Group> groups = groupDao.getGroupList();

		JSONObject data = new JSONObject();
		data.put("groups", groups);

		ResponseData responseData = dataCenterService.getData("responseData");
		ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
		responseData.setData(data);
	}

	public void getAvailablePermissionsRequestProcess() {
        List<Authority> permissions = authoritiesDao.getPermissionsList();
		JSONObject data = new JSONObject();
		data.put("permissions", permissions);

		ResponseData responseData = dataCenterService.getData("responseData");
		ResponseDataUtil.setHeadOfResponseDataWithSuccessInfo(responseData);
		responseData.setData(data);
	}
	
	
}
