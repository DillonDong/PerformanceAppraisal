package cn.edu.fjnu.towide;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@MapperScan("cn.edu.fjnu.towide.dao")
public class PerformanceAppraisal {

	public static void main(String[] args) {
		SpringApplication.run(PerformanceAppraisal.class, args);
    }
	
	/**
     * 文件上传配置
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大
        factory.setMaxFileSize("1024000KB"); //1000M
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("10240000KB");//1000M
        return factory.createMultipartConfig();
    }
	
}
