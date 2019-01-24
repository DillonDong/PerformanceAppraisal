package cn.edu.fjnu.towide.security;

import cn.edu.fjnu.towide.entity.ResponseHead;
import cn.edu.fjnu.towide.enums.ResponseHeadEnum;
import cn.edu.fjnu.towide.util.ResponseHeadUtil;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private Logger logger= LoggerFactory.getLogger(getClass());

    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        logger.debug("---RestAuthenticationEntryPoint--访问入口点---");
        PrintWriter out;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            out = response.getWriter();

            ResponseHead responseHead = ResponseHeadUtil.createResponseHead(ResponseHeadEnum.RESPONSEHEAD_NEED_LOGIN_ERROR);
//		String functionNo= HttpServletRequestUtil.getFunctionNo(request);
//		responseHead.setFunctionNo(functionNo);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("head", responseHead);

            out.println(jsonObject.toJSONString());
            out.flush();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}