package cn.edu.fjnu.towide.exceptionhandler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.alibaba.fastjson.JSONObject;

import cn.edu.fjnu.towide.entity.ResponseData;
import cn.edu.fjnu.towide.enums.ReasonOfFailure;
import cn.edu.fjnu.towide.util.ResponseDataUtil;

@ControllerAdvice
public class GlobalExceptionHandler {
	static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	// 处理因客户端传递的格式有误引起的异常
	@ExceptionHandler(value = Exception.class)
	public void defaultExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
		logger.debug(e.getMessage());
		logger.debug("参数传递有误");
		PrintWriter out;
		String exceptionName=e.getClass().getSimpleName();
		try {
			ResponseData responseData;
			JSONObject jsonObject;
			switch (exceptionName) {
			case "AccessDeniedException":
				
				responseData=ResponseDataUtil.createResponseData(request, response);
				ResponseDataUtil.setResponseDataWithFailureInfo(responseData, ReasonOfFailure.NO_AUTHORITY);
				
				response.setCharacterEncoding("UTF-8");  
				response.setContentType("application/json; charset=utf-8");
				response.setHeader("Access-Control-Allow-Origin", "*");
				
				jsonObject=new JSONObject();
				jsonObject.put("head", responseData.getHead());
				jsonObject.put("data", responseData.getData());
				
				logger.debug(jsonObject.toJSONString());
				logger.debug(response.getCharacterEncoding());
				
				out = response.getWriter();
				out.println(jsonObject.toJSONString());
				out.flush();
				out.close();
			break;

			default:
				responseData=ResponseDataUtil.createResponseData(request, response);
				ResponseDataUtil.setResponseDataWithFailureInfo(responseData, ReasonOfFailure.DATA_NOT_MATCH);
				
				response.setCharacterEncoding("UTF-8");  
				response.setContentType("application/json; charset=utf-8");
				response.setHeader("Access-Control-Allow-Origin", "*");
				
				jsonObject=new JSONObject();
				jsonObject.put("head", responseData.getHead());
				jsonObject.put("data", responseData.getData());
				
				logger.debug(jsonObject.toJSONString());
				logger.debug(response.getCharacterEncoding());
				
				out = response.getWriter();
				out.println(jsonObject.toJSONString());
				out.flush();
				out.close();
				break;
			}
/*			response.setCharacterEncoding("UTF-8");  
			response.setContentType("application/json; charset=utf-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
*/			

/*			ResponseHead responseHead=ResponseHeadUtil.createResponseHead(ResponseHeadEnums.RESPONSEHEAD_NOT_MEET_REQUIREMENTS_OF_INTERFACE);
			String functionNo=HttpServletRequestUtil.getFunctionNo(request);
			responseHead.setFunctionNo(functionNo);
			out = response.getWriter();
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("head", responseHead);
			logger.debug(jsonObject.toJSONString());
			logger.debug(response.getCharacterEncoding());
			out.println(jsonObject.toJSONString());
			out.flush();
			out.close();
*/
			} catch (IOException ee) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
