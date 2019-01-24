package cn.edu.fjnu.towide.enums;

public enum ReasonOfFailure implements IReasonOfFailure{
	FUNCTION_NO_ARE_INCORRECT("functionNoAreIncorrect","功能号不正确！"),
	USER_DOES_NOT_EXIST("UserDoesNotExist","用户不存在"),
	ILLEGAL_ATTACK_DETECTEDRECORDED("Illegal attack detected, recorded","检测到非法攻击，已记录"),
	USER_DOES_NOT_EXIST_OR_PASSWORD_IS_INCORRECT("UserDoesNotExistOrPasswordIsIncorrect","用户不存在或密码错误"),
	FAILED_TO_GET_VERIFICATION_CODE("FailedToGetVerificationCode","获取验证码失败！"),
	LOGIN_ERROR("LoginError", "未登录或登录失败！"),
	DATA_NOT_MATCH("DataNotMatch", "数据与接口需求不匹配"),
	THE_PARAMETERS_SUBMITTED_ARE_INCORRECT("The parameters submitted are incorrect","提交的参数有误"),

	OPERATION_TIME_INTERVAL_ERROR("operation_time_interval_error", "操作时间间隔小于1分钟"),
    PUSH_MAIL_ERROR("push_mail_error", "邮箱推送失败"),
	MNS_MAIL_ERROR("mns_mail_error", "邮箱推送消息获取失败"),
	PASSWORD_UPDATE_ERROR("password_update_error", "密码更新失败"),
	USER_REGISTRATION_ERROR("user_registration_error", "用户注册失败"),
	USERNAME_UPDATE_ERROR("username_update_error", "用户名回填失败"),
	QUEUE_IS_NOT_EXIST("Queue is not exist", "消息队列不存在"),
	MNS_UNKNOWN_ERROR("mns_unknown_error", "mns未知错误"),
	THE_REQUEST_IS_TIME_EXPIRED("The request is time expired", "mns消息请求已过期"),
	PUSH_MAIL_NETWORK_ERROR("push_mail_network_error", "邮箱推送网络连接异常失败"),
	FAILED_TO_GET_LOGIN_VERIFICATION_CODE("FailedToGetLoginVerificationCode","获取登陆验证码失败！"),
	PASSWORD_OR_VERIFICATION_CODE_ERROR("PASSWORD_OR_VERIFICATION_CODE_ERROR","密码或验证码错误！");//此处添加枚举值
	
	private String en_msg;
	private String zh_msg;

	ReasonOfFailure(String en_msg, String zh_msg) {
		this.en_msg = en_msg;
		this.zh_msg = zh_msg;
	}

	@Override
	public String getZhMsgOfFailure() {
		return zh_msg;
	}

	@Override
	public String getEnMsgOfFailure() {
		return en_msg;
	}
}
