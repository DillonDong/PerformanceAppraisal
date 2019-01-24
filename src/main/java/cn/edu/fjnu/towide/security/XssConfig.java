package cn.edu.fjnu.towide.security;

import com.google.common.collect.Maps;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * Created by caozhengxi Cotter on 2018/9/6.
 */
@Configuration
public class XssConfig {
    /**
     * xss过滤拦截器
     */
    @Bean
    public FilterRegistrationBean xssFilterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new XssFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.addUrlPatterns("/*");
        Map<String, String> initParameters = Maps.newHashMap();
        initParameters.put("excludes", "/favicon.ico,/img/*,/js/*,/css/*,/netInfoSetting");//排除url
        initParameters.put("isIncludeRichText", "false");//是否开启富文本框
        filterRegistrationBean.setInitParameters(initParameters);
        return filterRegistrationBean;
    }
}
