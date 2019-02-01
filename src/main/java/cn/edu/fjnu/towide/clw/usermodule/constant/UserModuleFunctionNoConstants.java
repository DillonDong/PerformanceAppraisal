package cn.edu.fjnu.towide.clw.usermodule.constant;

public interface UserModuleFunctionNoConstants {
	// 获取用户信息
	String GET_USER_INFO = "GetUserInfo";
	String RESET_PASSWORD = "ResetPassword";
	String IS_USER_EXIST = "IsUserExist";  //用户是否存在
	String USER_EXAMINATION_ITEMS_IN_ADD = "GetUserExaminationItemsInAdd";  //在添加绩效时获取用户项目考核
    String USER_EXAMINATION_ITEMS_IN_GET = "GetUserExaminationItemsInGet";  //在详情中获取用户项目考核
    String DELETE_EXAMINATION_ITEMS = "DeleteExaminationItems";  //删除用户未审核的某月业绩考核
	String GET_USER_INFO_LIST = "GetUserInfoList";//获取用户信息列表
	String GET_USER_DETAILED_INFO = "GetUserDetailedInfo";//获取用户详细信息
	String UPDATE_USER_DETAILED_INFO = "UpdateUserDetailedInfo";//更新用户详细信息

    String ADD_EXAMINATION_ITEMS = "AddExaminationItems";  //添加绩效


}
