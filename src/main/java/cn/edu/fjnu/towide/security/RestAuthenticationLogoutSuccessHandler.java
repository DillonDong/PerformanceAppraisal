package cn.edu.fjnu.towide.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//这个类主要实现登出时，向客户端反馈json数据，一旦设定，可以避免登出后，重定向的问题
@Component
public class RestAuthenticationLogoutSuccessHandler implements LogoutSuccessHandler {

	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
		response.setCharacterEncoding("UTF-8");  
		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");

//		System.out.println(authentication.getName());
/*		PrintWriter out;
		try {
			ResponseHead responseHead=null;
			String logout=response.getHeader("logout");
			if (logout.equals("failure"))
				responseHead=ResponseHeadUtil.createResponseHead(ResponseHeadEnums.RESPONSEHEAD_OPERATION_FAILURE);
			else
				responseHead=ResponseHeadUtil.createResponseHead(ResponseHeadEnums.RESPONSEHEAD_OPERATION_SUCCESS);
			out = response.getWriter();
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("head", responseHead);
			out.println(jsonObject.toJSONString());
			} catch (IOException e) {
			e.printStackTrace();
		}
*/
	}		

/*	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication arg2) {
		System.out.println("我logout啦啦啦");
		response.setCharacterEncoding("UTF-8");  
		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");

		PrintWriter out;
		try {
			out = response.getWriter();
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("logou", "json");
			out.println(jsonObject.toJSONString());
			out.flush();
			out.close();

			} catch (IOException e) {
			e.printStackTrace();
		}

	}*/
}