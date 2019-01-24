package cn.edu.fjnu.towide.security;

import cn.edu.fjnu.towide.dao.KeyVerificationCodeDao;
import cn.edu.fjnu.towide.dao.UserDao;
import cn.edu.fjnu.towide.dao.UserDetailDao;
import cn.edu.fjnu.towide.security.constant.SecurityConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启方法的权限注解式设置
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {

    public static final int TOKEN_VALIDITY_SECONDS = 1 * 24 * 3600;
    @Autowired
    private DataSource dataSource;//数据源
    @Autowired
    private RestAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private RestAuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private RestAuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private RestAuthenticationLogoutSuccessHandler restAuthenticationLogoutSuccessHandler;
    @Autowired
    private RestAccessDeniedHandler restAccessDeniedHandler;

    @Autowired
    private KeyVerificationCodeDao keyVerificationCodeDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    UserDetailDao userDetailDao;
    Logger logger= LoggerFactory.getLogger(getClass());



    public UserDetailsService jdbcUserDetailsServiceForLogin() {
        logger.debug("--JdbcUserDetailsManagerForLogin--");
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager();
        manager.setDataSource(dataSource);
        manager.setEnableGroups(true);
        manager.setUsersByUsernameQuery("select username,password,enabled from users where username = ? ");
        manager.setAuthoritiesByUsernameQuery("select username,authority from authorities where username = ? ");
        manager.setGroupAuthoritiesByUsernameQuery("select g.id, g.group_name, ga.authority from groups g, group_members gm, group_authorities ga where gm.username = ? and g.id = ga.group_id and g.id = gm.group_id");
        return manager;
    }

    public UserDetailsService jdbcUserDetailsServiceForVerificationCodeLogin() {
        logger.debug("--jdbcUserDetailsServiceForVerificationCodeLogin--");
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager();
        manager.setDataSource(dataSource);

        manager.setEnableGroups(true);
        //验证码的有效时间为5分钟
        manager.setUsersByUsernameQuery("select username,verification_code AS password,enabled from users where username = ?  AND UNIX_TIMESTAMP(NOW())-UNIX_TIMESTAMP(verification_code_generate_time)<" + SecurityConstants.MaximumPeriod);
        manager.setAuthoritiesByUsernameQuery("select username,authority from authorities where username = ? ");
        manager.setGroupAuthoritiesByUsernameQuery("select g.id, g.group_name, ga.authority from groups g, group_members gm, group_authorities ga where gm.username = ? and g.id = ga.group_id and g.id = gm.group_id");
        return manager;
    }


    public RestUsernamePasswordAuthenticationFilter restUsernamePasswordAuthenticationFilter() throws Exception {
        RestUsernamePasswordAuthenticationFilter restUsernamePasswordAuthenticationFilter = new RestUsernamePasswordAuthenticationFilter();
        restUsernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        restUsernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        restUsernamePasswordAuthenticationFilter.setRememberMeServices(restPersistentTokenBasedRememberMeServices());
        restUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManager());

        restUsernamePasswordAuthenticationFilter.setUserDao(userDao);
        restUsernamePasswordAuthenticationFilter.setKeyVerificationCodeDao(keyVerificationCodeDao);
        restUsernamePasswordAuthenticationFilter.setUserDetailDao(userDetailDao);

        return restUsernamePasswordAuthenticationFilter;

    }

    //	@Bean
    public RestPersistentTokenBasedRememberMeServices restPersistentTokenBasedRememberMeServices() {
        RestPersistentTokenBasedRememberMeServices rememberMeServices = new RestPersistentTokenBasedRememberMeServices("ww", jdbcUserDetailsServiceForLogin(), jdbcTokenRepositoryImpl());
        rememberMeServices.setTokenValiditySeconds(TOKEN_VALIDITY_SECONDS);
        rememberMeServices.setParameter("remember-me-request");
        return rememberMeServices;
    }


    //	@Bean
    public JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl() {
        JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl = new JdbcTokenRepositoryImpl();
        jdbcTokenRepositoryImpl.setDataSource(dataSource);
        return jdbcTokenRepositoryImpl;
    }


    //	@Bean
    public RememberMeAuthenticationFilter rememberMeAuthenticationFilter() throws Exception {
        RememberMeAuthenticationFilter rememberMeAuthenticationFilter = new RememberMeAuthenticationFilter(authenticationManager(), restPersistentTokenBasedRememberMeServices());
        return rememberMeAuthenticationFilter;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        AccessDeniedHandler accessDeniedHandler = new AccessDeniedHandlerImpl();
        return accessDeniedHandler;
    }




    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
        builder.authenticationProvider(rememberMeAuthenticationProvider());
        builder.userDetailsService(jdbcUserDetailsServiceForLogin()).passwordEncoder(bCryptPasswordEncoder());
        builder.userDetailsService(jdbcUserDetailsServiceForVerificationCodeLogin());
    }

    //	@Bean
    public RememberMeAuthenticationProvider rememberMeAuthenticationProvider() {
        RememberMeAuthenticationProvider rememberMeAuthenticationProvider = new RememberMeAuthenticationProvider("ww");
        return rememberMeAuthenticationProvider;
    }

//    SecurityContextPersistenceFilter  XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request,IS_INCLUDE_RICH_TEXT);


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .addFilterBefore(new XssFilter(),UsernamePasswordAuthenticationFilter.class)//添加xss过滤器
                .logout()
                .logoutUrl("/logout")
                .addLogoutHandler(restPersistentTokenBasedRememberMeServices())
                .logoutSuccessHandler(restAuthenticationLogoutSuccessHandler)
/*		.and()
		.requiresChannel().antMatchers("/security","/login","/logout").requiresSecure()
*/
                .and()
                .exceptionHandling().accessDeniedHandler(restAccessDeniedHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/login", "/register","/UserCenter","/**", "/security").permitAll()
		    //.antMatchers("/login","/UserCenter","/register","/fileUpload","/netInfoSetting","/security").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterAt(restUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(rememberMeAuthenticationFilter(), RememberMeAuthenticationFilter.class)
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);

    }

    //此处将所有的options请求全部直接放行，不纳入spring security的流程中，这个对于跨域请求，必须要进行设置
    //否则，spring security会对options请求也进行处理，添加不必要的麻烦
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
        web.ignoring().antMatchers("/images/**");
        super.configure(web);
    }
}