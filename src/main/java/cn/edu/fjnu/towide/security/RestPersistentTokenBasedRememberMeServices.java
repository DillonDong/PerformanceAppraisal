package cn.edu.fjnu.towide.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.CookieTheftException;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;

import cn.edu.fjnu.towide.util.HttpServletRequestUtil;
public class RestPersistentTokenBasedRememberMeServices extends PersistentTokenBasedRememberMeServices {
	
	//令牌的存储操作对象，方法中会用到这个对象
	private PersistentTokenRepository tokenRepository;
	public RestPersistentTokenBasedRememberMeServices(String key, UserDetailsService userDetailsService,
			PersistentTokenRepository tokenRepository) {
		super(key, userDetailsService, tokenRepository);
		this.tokenRepository=tokenRepository;
	}

	
	/**
	 * 用于将令牌序列号和令牌值封装成一个令牌，此令牌将在其他的方法中，返回给客户端
	 */
	@Override
	protected void setCookie(String[] tokens, int maxAge, HttpServletRequest request, HttpServletResponse response) {
		//获取令牌序列号
		String series=tokens[0];
		//从数据库获取令牌值，数据库中的令牌值是最新的，所以需要从数据库中读取
		PersistentRememberMeToken token = tokenRepository.getTokenForSeries(series);
		String tokenValue=token.getTokenValue();
		//将读取到的最新的令牌值更新到数组
		tokens[1]=tokenValue;
		//生成令牌，并写入响应的头部，在后续的过滤器中，会将这个head的内容，写入到json中，返回给客户端，降低客户端的开发难度
		response.setHeader("token", encodeCookie(tokens));
		//下面语句用于将生成的令牌写入客户端cookie，restful系统不需要，所以关闭此语句
//		super.setCookie(tokens, maxAge, request, response);
	}

	/**
	 * 当执行退出登录操作，会调用此方法，在实际的运行中发现authentication对象为空，在方法中要先创建
	 */
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		//获取令牌
		String remeber_me=extractRememberMeCookie(request);
		if (remeber_me==null) {
			response.setHeader("logout", "failure");
			return;
		}
		//解析令牌，获得令牌序列号和令牌值
		String[] tokens=decodeCookie(remeber_me);
		String series=tokens[0];
		//从数据库获取令牌对象
		PersistentRememberMeToken token = tokenRepository.getTokenForSeries(series);
		
		if (token==null) {
			response.setHeader("logout", "failure");
			return;
		}
		response.setHeader("logout", "success");
		//从令牌对象，获取用户名
		String userName=token.getUsername();
		//根据用户名删除数据库令牌
		tokenRepository.removeUserTokens(userName);
		System.out.println("super.logout");
		
		//authentication初始为空，先调用父类autoLogin方法，创建authentication对象
//		authentication=super.autoLogin(request, response);
		//调用父类方法，完成退出登录操作，从数据库中删除令牌
//		super.logout(request, response, authentication);
	}

	//登录成功时调用
	@Override
	protected void onLoginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		//先从数据库中删除已有的令牌，避免多条数据
		tokenRepository.removeUserTokens(authentication.getName());
		super.onLoginSuccess(request, response, authentication);
	}

	/**
	 * 此方法主要是提取客户端发送过来的令牌，如果返回值非空，说明客户端有包含令牌，进入remember-me的处理流程
	 * 如果不需要启动这个流程，客户端不要发送令牌即可，这个令牌包含：key，令牌序列号，和令牌值三个数据，通过三者封装而得到客户端令牌
	 * 在此处，我是把令牌放在request的header中携带而来，header的mame为“remember-me”
	 */
	@Override
	protected String extractRememberMeCookie(HttpServletRequest request) {
		
		String token=HttpServletRequestUtil.getParamValueFromHeadByParamName(request, "token");
		System.out.println(token);
		
		return token;
	}
	/**
	 * 如果extractRememberMeCookie返回值非空，进入rememberme授权模式，执行此方法
	 * 1、进行令牌的合法性校验，如果不合法，抛出异常，且将此令牌从数据库删除，如果合法，生成新的令牌，更新数据库令牌，并写入response中，进入下一步
	 * 2、获取并返回用户的detail信息，准备下一步授权操作
	 */
	@Override
	protected UserDetails processAutoLoginCookie(String[] cookieStrings, HttpServletRequest request, HttpServletResponse response) {
		UserDetails userDetails=null; 
		try {
/*	
 * 为了实现不要每次都更新，将这条语句重写
 * 旧语句如下：		
 * userDetails=super.processAutoLoginCookie(cookieStrings, request, response);
*/
			/*
			 * 新语句开始
			 */
			
			userDetails=getUserDetailsAndUpdateTokenLastUsedTime(cookieStrings,request,response);
			/*
			 * 新语句结束
			 */
			
			} catch (CookieTheftException e) {
/*			try {
				
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("error", "tokenerror");
				PrintWriter out = response.getWriter();
				out.print(jsonObject.toJSONString());
				response.flushBuffer();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
*/
			//此处胡乱建了一个userDetails对象，主要是为了捕获异常，同时又能正常走完spring security流程
			//实现比较笨，希望以后能慢慢研究好的解决办法
			//如果直接返回空对象，会引发其他异常
			userDetails=new UserDetails() {

				/**  
				 * @Fields field:field:{todo}
				 */  
				private static final long serialVersionUID = 315201474664558388L;

				public Collection<? extends GrantedAuthority> getAuthorities() {
					return null;
				}

				public String getPassword() {
					return null;
				}

				public String getUsername() {
					return null;
				}

				public boolean isAccountNonExpired() {
					return false;
				}

				public boolean isAccountNonLocked() {
					return false;
				}

				public boolean isCredentialsNonExpired() {
					return false;
				}

				public boolean isEnabled() {
					return false;
				}

			};
			logger.debug("--令牌错误--");
		}

//		UserDetails userDetails= super.processAutoLoginCookie(cookieStrings, request, response);
		return userDetails;
	}
	/*
	 * 此方法是为了实现token不要每次更新，而新增的方法
	 */
	private UserDetails getUserDetailsAndUpdateTokenLastUsedTime(String[] cookieTokens, HttpServletRequest request,
			HttpServletResponse response) {

		if (cookieTokens.length != 2) {
			throw new InvalidCookieException("Cookie token did not contain " + 2
					+ " tokens, but contained '" + Arrays.asList(cookieTokens) + "'");
		}

		final String presentedSeries = cookieTokens[0];
		final String presentedToken = cookieTokens[1];

		PersistentRememberMeToken token = tokenRepository
				.getTokenForSeries(presentedSeries);

		if (token == null) {
			// No series match, so we can't authenticate using this cookie
			throw new RememberMeAuthenticationException(
					"No persistent token found for series id: " + presentedSeries);
		}

		// We have a match for this user/series combination
		if (!presentedToken.equals(token.getTokenValue())) {
			// Token doesn't match series value. Delete all logins for this user and throw
			// an exception to warn them.
			tokenRepository.removeUserTokens(token.getUsername());
			

			throw new CookieTheftException(
					messages.getMessage(
							"PersistentTokenBasedRememberMeServices.cookieStolen",
							"Invalid remember-me token (Series/token) mismatch. Implies previous cookie theft attack."));
		}
//		long t3=getTokenValiditySeconds();
//		long t1=token.getDate().getTime() + getTokenValiditySeconds() * 1000L ;
//		long t2=System.currentTimeMillis();

		if (token.getDate().getTime() + getTokenValiditySeconds() * 1000L < System
				.currentTimeMillis()) {
			throw new RememberMeAuthenticationException("Remember-me login has expired");
		}

		// Token also matches, so login is valid. Update the token value, keeping the
		// *same* series number.
		if (logger.isDebugEnabled()) {
			logger.debug("Refreshing persistent login token for user '"
					+ token.getUsername() + "', series '" + token.getSeries() + "'");
		}

/*
 * 为了使得token不要每次都更新，对此段代码进行修改
 * 旧代码如下
 * 		PersistentRememberMeToken newToken = new PersistentRememberMeToken(
				token.getUsername(), token.getSeries(), generateTokenData(), new Date());
*/
		
		/*
		 * 新代码开始
		 */
		PersistentRememberMeToken newToken = new PersistentRememberMeToken(
		    	token.getUsername(), token.getSeries(), token.getTokenValue(), new Date());
		
		/*
		 * 新代码结束		
		 */
		try {
			tokenRepository.updateToken(newToken.getSeries(), newToken.getTokenValue(),
					newToken.getDate());
			addCookie(newToken, request, response);
		}
		catch (Exception e) {
			logger.error("Failed to update token: ", e);
			throw new RememberMeAuthenticationException(
					"Autologin failed due to data access problem");
		}

		return getUserDetailsService().loadUserByUsername(token.getUsername());
	}


	/**
	 * processAutoLoginCookie成功返回后执行
	 * 主要用于创建授权对象，写入 容器中，完成授权操作
	 */
/*	@Override
	protected Authentication createSuccessfulAuthentication(HttpServletRequest request, UserDetails user) {
		Authentication authentication=super.createSuccessfulAuthentication(request, user);
//		SecurityContextHolder.getContext().setAuthentication(authentication);
		System.out.println("createSuccessful");
		System.out.println(authentication);
		return authentication;
	}
*/
	@Override
	protected String[] decodeCookie(String arg0) throws InvalidCookieException {
		return super.decodeCookie(arg0);
	}
	
	/**
	 * 应用于登录操作时，如果需要启动记住令牌机制，该方法的返回值要为true，此时才会产生令牌写入数据库，并返回给客户端
	 * 其他场合，此方法不调用
	 * parameter参数，默认值为remember-me，这个值应该而已在某处设置修改
	 * 在本实现中，读取请求request中的json数据，rememberMe，当该值为yes表示
	 */
	@Override
	protected boolean rememberMeRequested(HttpServletRequest request, String parameter) {
//		boolean rememberMe=paramJson.getBooleanValue("rememberMe");
		String rememberMe=HttpServletRequestUtil.getParamValueFromParamByParamName(request, "rememberMe");
		if (rememberMe==null) {
			return false;
		}
		boolean result=rememberMe.equals("yes");
		return result;
	}

	private void addCookie(PersistentRememberMeToken token, HttpServletRequest request,
			HttpServletResponse response) {
		setCookie(new String[] { token.getSeries(), token.getTokenValue() },
				getTokenValiditySeconds(), request, response);
	}

}
