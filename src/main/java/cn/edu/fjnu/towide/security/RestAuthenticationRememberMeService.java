package cn.edu.fjnu.towide.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.RememberMeServices;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@Component
public class RestAuthenticationRememberMeService implements RememberMeServices {

	private Logger logger= LoggerFactory.getLogger(getClass());


	public Authentication autoLogin(HttpServletRequest request, HttpServletResponse response) {
		/*
		 * Object userName="13850138018"; Object password="111111";
		 * UsernamePasswordAuthenticationToken authRequest = new
		 * UsernamePasswordAuthenticationToken(userName, password);
		 * 
		 * System.out.println(authRequest); return authRequest;
		 */
		logger.debug("--autoLogin--");
		return null;

	}

	public void loginFail(HttpServletRequest arg0, HttpServletResponse arg1) {
		logger.debug("--loginFailure--");
	}

	public void loginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		logger.debug("--loginSuccess--");
	}

}
