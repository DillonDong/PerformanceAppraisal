package cn.edu.fjnu.towide.security;


import cn.edu.fjnu.towide.dao.AdminDetailDao;
import cn.edu.fjnu.towide.dao.UserDao;
import cn.edu.fjnu.towide.dao.UserDetailDao;
import cn.edu.fjnu.towide.entity.ResponseData;
import cn.edu.fjnu.towide.entity.ResponseHead;
import cn.edu.fjnu.towide.entity.User;
import cn.edu.fjnu.towide.enums.ResponseHeadEnum;
import cn.edu.fjnu.towide.util.HttpServletRequestUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class RestAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	@Autowired
	UserDao userDao;
	@Autowired
	UserDetailDao userDetailDao;
	@Autowired
	AdminDetailDao adminDetailDao;
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {
	    response.setCharacterEncoding("UTF-8");  
		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		String token=response.getHeader("token");

		String userName=authentication.getName();

		User user= userDetailDao.getUserDetailsByUserName(userName);
		if (user==null){
			user=adminDetailDao.getAdminDetailsByUserName(userName);
		}

		PrintWriter out = response.getWriter();

		String functionNo= HttpServletRequestUtil.getParamValueFromHeadByParamName(request, "functionNo");
		ResponseHead head=new ResponseHead();
		head.setFunctionNo(functionNo);
		head.setCode(ResponseHeadEnum.SUCCESS.getCode());
		head.setZh_msg(ResponseHeadEnum.SUCCESS.getZh_msg());
		head.setEn_msg(ResponseHeadEnum.SUCCESS.getEn_msg());
		head.setToken(token);

		JSONObject data=new JSONObject();
		data.put("user",user);

		ResponseData responseData=new ResponseData();
		responseData.setHead(head);
		responseData.setData(data);

		JSONObject result=(JSONObject) JSONObject.toJSON(responseData);
		out.println(result);

//		JSONObject head=new JSONObject();
//		head.put("code", "1000");
//		head.put("msg", "操作成功！");
//		head.put("token", token);
//		JSONObject responseJson=new JSONObject();
//		responseJson.put("head", head);
//		responseJson.put("data" ,data);
//		out.print(responseJson);





//		System.out.println("成功啦！！！");
/*		//创建head
		ResponseHead head=ResponseHeadUtil.createResponseHead(ResponseHeadEnums.RESPONSEHEAD_OPERATION_SUCCESS);
		head.setFunctionNo(HttpServletRequestUtil.getFunctionNo(request));
		//因为认证是在请求到达目标之前进行的，所以认证的结果保存在header中，此处拿出来放到json中，方便客户端的使用
		String token=response.getHeader("remember-me");
		head.setToken(token);
	
		
		
		JSONObject jsonObject=new JSONObject();
		
		jsonObject.put("head", head);
		
		String userName=authentication.getName();
		User user=UserDao.getUserByUserName(userName);
		if (user.getType()==User.TYPE_STUDENT||user.getType()==user.TYPE_TEACHER) {
			int unreadPublicMessageNumber=departmentUsersDao.getUnReadPublicMessageNumber(userName);
			user.setUnreadPublicMessageNumber(unreadPublicMessageNumber);
		}
		List<String>sectionIds=null;
		if (user.getType()==User.TYPE_RESERVATION_ASSESSOR) {
			sectionIds=userSectionsDao.getSectionIdsByUsername(userName);
		}

		JSONObject data=new JSONObject();
		data.put("user", user);
//		data.put("sectionIds", sectionIds);
		jsonObject.put("data", data);
		
		out.println(jsonObject.toJSONString());
*/		out.flush();
		out.close();
	}
}