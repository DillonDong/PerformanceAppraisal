package cn.edu.fjnu.towide.czx.AuthorityGroupManagement.enums;

import cn.edu.fjnu.towide.enums.IReasonOfFailure;

public enum ReasonOfFailure implements IReasonOfFailure{
	USER_DOES_NOT_EXIST("User does not exist","用户不存在"),
	FUNCTION_NO_ARE_INCORRECT("functionNoAreIncorrect","功能号不正确！"),
	THE_PARAMETERS_SUBMITTED_ARE_INCORRECT("The parameters submitted are incorrect","提交的参数有误"), 
	USER_ALREADY_EXISTS_DESCRIPTION("The parameters submitted are incorrect","用户已经存在"),
	THE_PERMISSIONS_CANNOT_BE_EMPTY("The parameters submitted are incorrect","权限不能为空"),
	THE_GROUP_NAME_CANNOT_BE_EMPTY("The parameters submitted are incorrect","权组名称不能为空"),
	MEMBERS_IN_THE_GROUP_ARE_NOT_ALLOWED_TO_DELETE("The parameters submitted are incorrect","组内有成员请先删除成员！"),
	THE_GROUP_CANNOT_BE_EMPTY("The parameters submitted are incorrect","组的ID不能为空"),
	PAGEINFO_ERROR("PageInfoError", "分页信息错误");//此处添加枚举值

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
