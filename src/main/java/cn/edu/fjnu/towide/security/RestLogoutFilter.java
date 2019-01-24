package cn.edu.fjnu.towide.security;

import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RestLogoutFilter extends LogoutFilter{

	public RestLogoutFilter(LogoutSuccessHandler logoutSuccessHandler, LogoutHandler...handlers) {
		super(logoutSuccessHandler, handlers);
	}

	@Override
	protected boolean requiresLogout(HttpServletRequest request, HttpServletResponse response) {
		String url=request.getRequestURI();
		System.out.println(url);
		if (url.equals("/logout")) {
			return true;
		}
		logger.debug("--require logout--");
		return false;
	}
	
}
