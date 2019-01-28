package cn.edu.fjnu.towide.xxx.assessmentItemManagement.enums;

import cn.edu.fjnu.towide.enums.IReasonOfFailure;

public enum ReasonOfFailure implements IReasonOfFailure{

	NAME_IS_EMPTY("The parameters submitted are incorrect", "名称不能为空"),
	DELETE_IS_FAILURE("delete_is_failure","删除失败"),
	ASSESSMENT_ITEM_IS_NOT_DELETE("assessment_item_is_not_delete","待删除的考核项中存在正在使用的考核项"),
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
