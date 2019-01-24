package cn.edu.fjnu.towide.enums;

public enum ResponseHeadEnum {

	SUCCESS("2000","操作成功!","SUCCESS"),
	FAILURE("5000","操作失败!","FAILURE"),
	PASSWORD_OR_VERIFICATION_CODE_ERROR("5000","密码或验证码错误！","password or verification code error"),
	RESPONSEHEAD_NEED_LOGIN_ERROR("4000","未登录或登录失败!","Not logged in or failed to login!");
//	RESPONSEHEAD_WRONG_JSON_FORMAT_ERROR("C0001","发送的数据不符合json规范!"),
	//		RESPONSEHEAD_NOT_MEET_REQUIREMENTS_OF_INTERFACE("C0002","发送的数据符合json规范，但不符合接口要求！"),
//		RESPONSEHEAD_APPVERNO_ERROR("C0001","app版本号有误！"),
//		RESPONSEHEAD_FUNCTIONNO_ERROR("C0003","功能号有误！"),
//		RESPONSEHEAD_VERIFICATION_CODE_INCORRECT_ERROR("C0006","验证码错误！"),
//		RESPONSEHEAD_VERIFICATION_CODE_EXPIRED_ERROR("C0007","验证码过期！")
//
//
//		RESPONSEHEAD_PERMISSION_DENIED_ERROR("C0009","没有访问权限！"),
//
//		RESPONSEHEAD_USER_NO_EXIST("UserNoExist","用户不存在！"),
//
//
//		//操作失败
//		RESPONSEHEAD_OPERATION_FAILURE("C1000","抱歉，操作失败，请检查输入参数的正确性！"),
//
//		//操作成功
//		RESPONSEHEAD_OPERATION_SUCCESS("C2000","恭喜，操作成功！")

	private String code;
	private String zh_msg;
	private String en_msg;

	ResponseHeadEnum(String code,String zh_msg,String en_msg) {
		this.code = code;
		this.zh_msg=zh_msg;
		this.en_msg=en_msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getZh_msg() {
		return zh_msg;
	}

	public void setZh_msg(String zh_msg) {
		this.zh_msg = zh_msg;
	}

	public String getEn_msg() {
		return en_msg;
	}

	public void setEn_msg(String en_msg) {
		this.en_msg = en_msg;
	}
}
