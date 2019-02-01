package cn.edu.fjnu.towide.xxx.parameterManagement.enums;

import cn.edu.fjnu.towide.enums.IReasonOfFailure;

public enum ReasonOfFailure implements IReasonOfFailure {

	THE_PARAMETERS_SUBMITTED_ARE_INCORRECT("The parameters submitted are incorrect","提交的参数有误"),
	THE_PARAMETERS_EXIST_NULL("The parameters exist null","提交的参数存在未填写的"),
	UPDATE_IS_FAILURE("update_is_failure", "修改失败"),//此处添加枚举值
	THE_PARAMETERS_DO_NOT_CONFORM_TO_SPECIFICATIONS("The parameter do not conform to specifications","区间参数请满足左侧小于右侧")
	;


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
