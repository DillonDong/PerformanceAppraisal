package cn.edu.fjnu.towide.ww.usercenter.enums;

import cn.edu.fjnu.towide.enums.IReasonOfFailure;

public enum ReasonOfFailure implements IReasonOfFailure{
	OPENID_IS_EMPTY("The parameters submitted are incorrect", "OpenId不能为空"),//此处添加枚举值
	PHONE_IS_ERROR("The parameters submitted are incorrect", "手机号格式错误"),//此处添加枚举值
	IDNUM_IS_ERROR("The parameters submitted are incorrect", "身份证号格式错误"),//此处添加枚举值
	EMAIL_IS_EMPTY("The parameters submitted are incorrect", "邮箱号不能为空"),//此处添加枚举值
	USERINFO_IS_EMPTY("The parameters submitted are incorrect", "用户信息不能为空"),//此处添加枚举值

	PUSH_MAIL_ERROR("push_mail_error", "邮箱推送失败"),
	PASSWORD_UPDATE_ERROR("password_update_error", "密码更新失败"),
	USER_REGISTRATION_ERROR("user_registration_error", "用户注册失败"),

	USER_DOES_NOT_EXIST("User does not exist","用户不存在"),
	WECHAT_CODE_IS_EMPTY("The parameters submitted are incorrect","weChatCode不能为空" ),
	WECHAT_DETAIL_IS_EMPTY("The parameters submitted are incorrect","weChatDetail不能为空" ),
	REQUEST_TIMED_OUT_CODE("The parameters submitted are incorrect","请求超时"),
	INVALID_CODE_CODE("The parameters submitted are incorrect", "无效的code"),
	UPDATE_PASSWORD_FAILURE("The parameters submitted are incorrect","更新密码失败"),
	OLD_PASSWORD_ERROR("The parameters submitted are incorrect","原密码错误"),
	IDCARD_IS_ILLEGAL("The parameters submitted are incorrect","身份证号非法,'X'需大写"),
	PHONE_NUM_IS_ILLEGAL("The parameters submitted are incorrect","手机号非法" ),
	ADMISSION_NOTICE_ID_IS_EMPTY("The parameters submitted are incorrect", "录取通知书编号不能为空"),
	PASSWORD_IS_EMPTY("The parameters submitted are incorrect", "密码不能为空"),
	USER_NAME_IS_EMPTY("The parameters submitted are incorrect", "用户名不能为空");//此处添加枚举值

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
