package cn.edu.fjnu.towide.clw.usermodule.enums;

import cn.edu.fjnu.towide.enums.IReasonOfFailure;

public enum ReasonOfFailure implements IReasonOfFailure{
	USER_DOES_NOT_EXIST("User does not exist","用户不存在"),
	UPDATE_PASSWORD_FAILURE("The parameters submitted are incorrect","更新密码失败"),
	OLD_PASSWORD_ERROR("The parameters submitted are incorrect","原密码错误"),
	IDCARD_IS_IMPTY("The parameters submitted are incorrect","身份证号不能为空" ),
	PHONE_NUM_IS_EMPTY("The parameters submitted are incorrect","手机号不能为空" ),
	ADMISSION_NOTICE_ID_IS_EMPTY("The parameters submitted are incorrect", "录取通知书编号不能为空"), 
	PASSWORD_IS_EMPTY("The parameters submitted are incorrect", "密码不能为空"),
	OPENID_IS_WRONG("The parameters submitted are incorrect", "OpenId错误"),
	TIME_IS_EMPTY("The parameters submitted are incorrect", "时间不能为空"),
	DELETE_IS_WRONG("The parameters submitted are incorrect", "删除失败"),
	EXAMINE_IS_WRONG("The parameters submitted are incorrect", "已审核不能删除"),

	PAGEINFO_ERROR("PageInfoError", "分页信息错误"),//此处添加枚举值
	UPDATE_ERROR("update_error","更新对象出错"),
	EXAMINATION_ITEMS_EMPTY("examination_items_empty","考核项为空"),
	ADD_EXAMINATION_ITEMS_WRONG("add_examination_items_wrong","添加绩效考核失败"),
	ADD_WAGE_WRONG("add_wage_wrong","添加薪资失败"),
	DELETE_ERROR("update_error","删除用户失败"),
	EXAMINATION_ITEMS_IS_EXIST("examination_items_is_exist","该月绩效考核已存在"),
    COUNT_ERR("COUNT_ERR","请输入数字类型"),
    TURNOVER_ERR("TURNOVER_ERR","营业额不能为空"),
    EXAMINATION_ITEM_ERR("EXAMINATION_ITEM_ERR","考核项数值不能为空"),

    UPDATE_EXAMINATION_ITEMS_WRONG("update_examination_items_wrong","更新绩效考核失败"),
	UPDATE_WAGE_WRONG("update_wage_wrong","更新薪资失败"),
	EXAMINATION_ITEM_IS_EMPTY("examination_item_is_empty", "考核项为空"),
	ADD_GROUPS_WRONG("add_groups_wrong","添加权组失败"),
	GROUP_EXIST("GROUP_EXIST","权组已存在"),

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
