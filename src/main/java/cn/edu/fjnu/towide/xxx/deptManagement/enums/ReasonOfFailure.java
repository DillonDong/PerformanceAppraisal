package cn.edu.fjnu.towide.xxx.deptManagement.enums;

import cn.edu.fjnu.towide.enums.IReasonOfFailure;

public enum ReasonOfFailure implements IReasonOfFailure{

	NAME_IS_EMPTY("The parameters submitted are incorrect", "名称不能为空"),
	WEIGHT_SHOULD_OVER_ZERO("The weight should over zero", "权重填写的应该是一个大于0的值"),
	ASSESSMENT_ITEM_SHOULD_OVER_ZERO("the assessment item should over zero", "请为部门至少添加一个考核项"),

	DELETE_IS_FAILURE("delete_is_failure","删除失败"),
	INSERT_IS_FAILURE("insert_is_failure", "插入失败");//此处添加枚举值

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
