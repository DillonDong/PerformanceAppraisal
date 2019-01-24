package cn.edu.fjnu.towide.security;

import cn.edu.fjnu.towide.entity.ResponseData;
import cn.edu.fjnu.towide.entity.ResponseHead;
import cn.edu.fjnu.towide.enums.ResponseHeadEnum;
import cn.edu.fjnu.towide.util.HttpServletRequestUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class RestAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException {
	    response.setCharacterEncoding("UTF-8");  
		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();


		String functionNo=HttpServletRequestUtil.getParamValueFromHeadByParamName(request, "functionNo");


		ResponseHead head=new ResponseHead();
		head.setFunctionNo(functionNo);
		head.setCode(ResponseHeadEnum.PASSWORD_OR_VERIFICATION_CODE_ERROR.getCode());
		head.setZh_msg(ResponseHeadEnum.PASSWORD_OR_VERIFICATION_CODE_ERROR.getZh_msg());
		head.setEn_msg(ResponseHeadEnum.PASSWORD_OR_VERIFICATION_CODE_ERROR.getEn_msg());

		ResponseData responseData=new ResponseData();
		responseData.setHead(head);

		JSONObject result=(JSONObject) JSONObject.toJSON(responseData);
		out.println(result);
		
/*		//创建head
		ResponseHead head=ResponseHeadUtil.createResponseHead(ResponseHeadEnums.RESPONSEHEAD_OPERATION_FAILURE);
		head.setFunctionNo(HttpServletRequestUtil.getFunctionNo(request));
		//因为认证是在请求到达目标之前进行的，所以认证的结果保存在header中，此处拿出来放到json中，方便客户端的使用
		String token=response.getHeader("remember-me");
		head.setToken(token);
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("head", head);
		out.println(jsonObject.toJSONString());
*/		out.flush();
		out.close();

//		super.onAuthenticationFailure(request, response, exception);
	}
}