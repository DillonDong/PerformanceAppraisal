package cn.edu.fjnu.towide.security;

import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.log.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class RestAuthenticationLogoutHandler implements LogoutHandler {
	 private Logger logger= LoggerFactory.getLogger(getClass());

	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		logger.debug("--logout--");
/*		response.setCharacterEncoding("UTF-8");  
		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
*/
		PrintWriter out;
		try {
			out = response.getWriter();
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("logout", "json");
			out.println(jsonObject.toJSONString());
/*			out.flush();
			out.close();
*/
			} catch (IOException e) {
			e.printStackTrace();
		}

	}
}