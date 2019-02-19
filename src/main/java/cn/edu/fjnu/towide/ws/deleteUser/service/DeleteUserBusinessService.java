package cn.edu.fjnu.towide.ws.deleteUser.service;

import cn.edu.fjnu.towide.dao.AuthoritiesDao;
import cn.edu.fjnu.towide.dao.UserDao;
import cn.edu.fjnu.towide.dao.UserDetailDao;
import cn.edu.fjnu.towide.entity.*;
import cn.edu.fjnu.towide.service.DataCenterService;
import cn.edu.fjnu.towide.util.CheckVariableUtil;
import cn.edu.fjnu.towide.util.ExceptionUtil;
import cn.edu.fjnu.towide.util.IdGenerator;
import cn.edu.fjnu.towide.util.ResponseDataUtil;
import cn.edu.fjnu.towide.vo.*;
import cn.edu.fjnu.towide.ws.deleteUser.enums.ReasonOfFailure;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeleteUserBusinessService {
	
	@Autowired
	DataCenterService dataCenterService;
	@Autowired
	UserDao userDao;
	@Autowired
	UserDetailDao userDetailDao;
	@Autowired
	AuthoritiesDao authoritiesDao;

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
}
