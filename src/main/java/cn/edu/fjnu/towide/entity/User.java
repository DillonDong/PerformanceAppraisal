package cn.edu.fjnu.towide.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
public class User {
	public static final String VERIFICATION_CODE_MAXIMUM_PERIOD = "5*60";

	public static final String DEFAULT_PASSWORD = "888888";// 初始密码

	private String username;
	private String realName;

	private String email;
	private String phone;
	private String sex;
	private Date birthday;
	private Date createTime;
	private Date updateTime;
	private String password;
	private Integer enabled;
	private String verificationCode;
	private Date verificationCodeGenerateTime;
	private String ip;


	public static String getDefaultPassword() {
		return DEFAULT_PASSWORD;
	}
}
