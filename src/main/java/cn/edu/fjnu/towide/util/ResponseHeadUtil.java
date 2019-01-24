package cn.edu.fjnu.towide.util;

import cn.edu.fjnu.towide.entity.ResponseHead;
import cn.edu.fjnu.towide.enums.ResponseHeadEnum;

public class ResponseHeadUtil {
	public static ResponseHead createResponseHead(ResponseHeadEnum responseHeadEnum) {
		ResponseHead responseHead = new ResponseHead();
		responseHead.setCode(responseHeadEnum.getCode());
		responseHead.setZh_msg(responseHeadEnum.getZh_msg());
		responseHead.setEn_msg(responseHeadEnum.getEn_msg());
		return responseHead;
	}

}
