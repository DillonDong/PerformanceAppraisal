package cn.edu.fjnu.towide.xxx.parameterManagement.enums;

import cn.edu.fjnu.towide.enums.IReasonOfFailure;

public enum ReasonOfFailure implements IReasonOfFailure {

	THE_PARAMETERS_SUBMITTED_ARE_INCORRECT("The parameters submitted are incorrect","提交的参数有误"),
	JXKHLEFT_SHOULD_OVER_60("JXKHLEFT_SHOULD_OVER_60","绩效考核左区间应该大于60分"),
	MERITSPAY_OVER_0("MERITSPAY_OVER_0","绩效奖金额应该大于0"),
	MINIMUMTURNOVER_OVER_0("MINIMUMTURNOVER_OVER_0","保底营业额金额应该大于0"),
	CCFSQJL_OVER_0("CCFSQJL_OVER_0","抽成分数左区间应该大于0"),
	COUNT_OVER_0("COUNT_OVER_0","数值应该大于0"),
	CCFSQJR_OVER_0("CCFSQJR_OVER_100","抽成分数右区间应该小于等于100"),

	JXQJRIGHT_SHOULD_OVER_60("JXQJRIGHT_SHOULD_OVER_60","绩效考核右区间应该小于100分"),
	JXBLLEFT_ERROR("JXBLLEFT_IS_ERROR","绩效比例左区间应该在0-100之间"),
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
