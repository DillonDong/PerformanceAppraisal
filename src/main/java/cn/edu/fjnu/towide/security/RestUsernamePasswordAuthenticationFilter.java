package cn.edu.fjnu.towide.security;


import cn.edu.fjnu.towide.dao.KeyVerificationCodeDao;
import cn.edu.fjnu.towide.dao.UserDao;
import cn.edu.fjnu.towide.dao.UserDetailDao;
import cn.edu.fjnu.towide.security.constant.FunctionNoConstant;
import cn.edu.fjnu.towide.util.HttpServletRequestUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//@Component
public class RestUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	public static final String SPRING_SECURITY_RESTFUL_USERNAME_KEY = "username";
	public static final String SPRING_SECURITY_RESTFUL_PASSWORD_KEY = "password";
	public static final String SPRING_SECURITY_RESTFUL_LOGIN_URL = "/login";

	private String usernameParameter = SPRING_SECURITY_RESTFUL_USERNAME_KEY;
	private String passwordParameter = SPRING_SECURITY_RESTFUL_PASSWORD_KEY;
	private boolean postOnly = true;

	private KeyVerificationCodeDao keyVerificationCodeDao;
	
	@SuppressWarnings("unused")
	private UserDao userDao;

	private UserDetailDao userDetailDao;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	public void setKeyVerificationCodeDao(KeyVerificationCodeDao keyVerificationCodeDao) {
		this.keyVerificationCodeDao = keyVerificationCodeDao;
	}
	
	public void setUserDetailDao(UserDetailDao userDetailDao) {
		this.userDetailDao = userDetailDao;
	}
	
	//这个方法主要是用于判断是否是登录请求，如果返回为true，表示是登录请求，启动登录流程
	@Override
	protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("---requiresAuthentication---：判断是否是登录");
		String functionNo=HttpServletRequestUtil.getParamValueFromHeadByParamName(request, "functionNo");
		switch (functionNo) {
		case FunctionNoConstant.LOGIN_BY_PASSWORD:
		case FunctionNoConstant.LOGIN_BY_PHONE_VERIFICATION_CODE:
		case FunctionNoConstant.LOGIN_BY_PASSWORD_AND_VERIFICATION_CODE:
			return true;

		default:
			return false;
		}
		
	}

	protected RestUsernamePasswordAuthenticationFilter() {
		super(new AntPathRequestMatcher(SPRING_SECURITY_RESTFUL_LOGIN_URL, "POST"));
	}
	
//    @Autowired
    @Override
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        logger.debug("setAuthenticationManager(AuthenticationManager) - start"); //$NON-NLS-1$
        super.setAuthenticationManager(authenticationManager);
        logger.debug("setAuthenticationManager(AuthenticationManager) - end"); //$NON-NLS-1$
    }
    

/*	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		System.out.println("过滤过滤更健康！！！");
		super.doFilter(arg0, arg1, arg2);
	}
*/
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		if (postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}

		String functionNo=HttpServletRequestUtil.getParamValueFromHeadByParamName(request, "functionNo");

		//只有当param非空时，才进行userName和password的读取操作，否则会报空指针异常
		String usernameOrPhone=HttpServletRequestUtil.getParamValueFromParamByParamName(request, "usernameOrPhone");
		logger.debug("--usernameOrPhone--"+usernameOrPhone);

		String username=userDetailDao.getUsernameByPhone(usernameOrPhone);

		if (username==null) {
			username=usernameOrPhone;
		}
		String password=HttpServletRequestUtil.getParamValueFromParamByParamName(request, "password");

		if (username!=null) {
			switch (functionNo) {
			case FunctionNoConstant.LOGIN_BY_PASSWORD_AND_VERIFICATION_CODE:
				String key=HttpServletRequestUtil.getParamValueFromParamByParamName(request, "key");
				String verificationCode=HttpServletRequestUtil.getParamValueFromParamByParamName(request, "verificationCode");
				boolean result=checkKeyAndVerificationCode(key,verificationCode);
				if (!result) {
					username=null;
					break;
				}
			case FunctionNoConstant.LOGIN_BY_PASSWORD:
				password=HttpServletRequestUtil.getParamValueFromParamByParamName(request, "password");
				break;
			case FunctionNoConstant.LOGIN_BY_PHONE_VERIFICATION_CODE:
				password=HttpServletRequestUtil.getParamValueFromParamByParamName(request, "phoneVerificationCode");
			default:
				break;
			}
		}

		
		if (username == null) {
			username = "";
		}

		if (password == null) {
			password = "";
		}

		username = username.trim();

		logger.debug("--------RestUsernamePasswordAuthenticationFilter------username---------"+username);

		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);

		return this.getAuthenticationManager().authenticate(authRequest);

	}

	private boolean checkKeyAndVerificationCode(String key, String verificationCode) {
		boolean result=keyVerificationCodeDao.checkKeyAndVerificationCode(key,verificationCode);
		return result;
	}



	protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}

}
