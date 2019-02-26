package cn.edu.fjnu.towide.service;

import cn.edu.fjnu.towide.dao.AdminDetailDao;
import cn.edu.fjnu.towide.dao.UserDao;
import cn.edu.fjnu.towide.dao.UserDetailDao;
import cn.edu.fjnu.towide.entity.ResponseData;
import cn.edu.fjnu.towide.entity.User;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
//@PropertySource("classpath:LossPrevention.property") // 指定本类中将要读取属性文件
/**
 * 此类主要用来暂存程序执行过程中产生的临时数据，供后续的程序中获取使用，以免数据的反复生成
 * 
 * @author 吴闻
 *
 */
public class DataCenterService {
	private Logger logger= LoggerFactory.getLogger(getClass());

	@Autowired
	UserDao userDao;
	@Autowired
	UserDetailDao userDetailDao;
	@Autowired
	AdminDetailDao adminDetailDao;



	// 多线程并发的环境，必须使用ThreadLocal存放中间结果，
	// 大部分的中间结果，保存到data中，dataLocal存放当前线程的data，配合setData和getData来读写data中存放的数据
	private ThreadLocal<JSONObject> dataLocal = new ThreadLocal<>();

	/**
	 * 将数据写入到data中
	 * 
	 * @param dataName
	 *            数据的名称，将来要通过它来获取存入的值
	 * @param dataValue
	 *            数据的值
	 */
	public <T> void setData(String dataName, T dataValue) {
		JSONObject data = dataLocal.get();
		data.put(dataName, dataValue);
	}

	/**
	 * 将数据从data中读出
	 * 
	 * @param dataName
	 *            要读取数据的名称
	 * @return 返回数据的值
	 */
	@SuppressWarnings("unchecked")
	public <T> T getData(String dataName) {
		JSONObject data = dataLocal.get();
		T tempData = (T) data.get(dataName);
		return tempData;
	}

	public void init(JSONObject requestParamJson, ResponseData responseData) {

		//Xss过滤
//		String str=requestParamJson.toString();
//		if (StringUtils.isNotBlank(str)) {
//			str = JsoupUtil.clean(str);
//		}
//		JSONObject cleanRequestParamJson=JSONObject.parseObject(str);

		JSONObject data = new JSONObject();
		this.dataLocal.set(data);

		User currentLoginUser=getCurrentLoginUser();
		//User currentLoginUser=new User();
		this.setData("currentLoginUser", currentLoginUser);
		this.setData("requestParamJson", requestParamJson);
		this.setData("responseData", responseData);

	}

	private User getCurrentLoginUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication==null) {
			logger.debug("--数据中心获取当前用户失败--请登录--");
			return null;
		}
		String userName = authentication.getName();
		User user = userDetailDao.getUserDetailsByUserName(userName);
		if (user==null){
			user=adminDetailDao.getAdminDetailsByUserName(userName);
		}
		return user;
	}

	//从datalocal中拿，保证拿到自己的
	public User getCurrentLoginUserFromDataLocal(){
		//TODO
		// User test=new User();
		// test.setUsername("ws");
		// test.setRealName("王胜");
		// test.setIp("192.168.0.1");
		// return test;
		User currentLoginUser=this.getData("currentLoginUser");
		currentLoginUser.setIp(getIpAddress());
		return currentLoginUser;
	}

	public String getIpAddress(){
		JSONObject data=dataLocal.get();
		JSONObject requestParamJson=data.getJSONObject("requestParamJson");
		@SuppressWarnings("unchecked")
		String ip=requestParamJson.get("ip").toString();
		return ip;
	}


	public ResponseData getResponseDataFromDataLocal(){
	    return  this.getData("responseData");
    }

	public <T> T getParamValueFromParamOfRequestParamJsonByParamName(String paramName) {
		JSONObject data=dataLocal.get();
		JSONObject requestParamJson=data.getJSONObject("requestParamJson");
		JSONObject param=requestParamJson.getJSONObject("param");
		@SuppressWarnings("unchecked")
		T paramValue=(T) param.get(paramName);
		return paramValue;
	}
	public <T> T getParamValueFromHeadOfRequestParamJsonByParamName(String paramName) {
		JSONObject data=dataLocal.get();
		JSONObject requestParamJson=data.getJSONObject("requestParamJson");
		JSONObject head=requestParamJson.getJSONObject("head");
		@SuppressWarnings("unchecked")
		T paramValue=(T) head.get(paramName);
		return paramValue;
	}

//	public void remove() {
//		this.dataLocal.remove();
//	}

}
