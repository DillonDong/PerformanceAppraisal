package cn.edu.fjnu.towide.czx.AuthorityGroupManagement.constant;

public interface ReasonOfFailureConstant {

	String GENERATE_LOGIN_VERIFICATION_CODE_FAILURE = "生成登录验证码失败!";
	String UPDATE_PHONE_VERIFICATION_CODE_FAILURE = "更新数据库手机验证码失败!";
	
	String THE_PARAMETERS_SUBMITTED_ARE_INCORRECT_CODE ="The parameters submitted are incorrect";
	String THE_PARAMETERS_SUBMITTED_ARE_INCORRECT_DESCRIPTION ="提交的参数有误！";
	
	String USER_NOT_EXIST_DESCRIPTION= "用户不存在!";
	String USER_NOT_EXIST_CODE="User does not exist";
	
	String MEMBERS_IN_THE_GROUP_ARE_NOT_ALLOWED_TO_DELETE_CODE = "Members in the group are not allowed to delete";
	String MEMBERS_IN_THE_GROUP_ARE_NOT_ALLOWED_TO_DELETE_DESCRIPTION = "组内有成员请先删除成员！";
	
	String THE_PERMISSIONS_CANNOT_BE_EMPTY_CODE = "The permissions can not be empty";
	String THE_PERMISSIONS_CANNOT_BE_EMPTY_DESCRIPTION = "权限不能为空";


}
