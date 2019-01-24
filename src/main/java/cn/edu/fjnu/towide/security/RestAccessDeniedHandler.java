package cn.edu.fjnu.towide.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	public void handle(HttpServletRequest arg0, HttpServletResponse arg1, AccessDeniedException arg2){
		logger.debug("--RestAccessDeniedHandler--:access deny");
	}

}
