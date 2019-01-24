package cn.edu.fjnu.towide.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class ResponseData {
	private ResponseHead head;
	private JSONObject data;

}
