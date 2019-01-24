package cn.edu.fjnu.towide.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author: YuLin
 * @Description: 改变网报状态位的信息封装类
 * @Date: Created in 10:21 2018/9/19
 * @Modified By:
 */
@Data
public class ChangeState {
    Integer enrolState;//网报信息状态位
    Integer paymentState;//缴费状态位
    List<String> failedReasons;//常用失败原因集合
    String failedComment;//失败原因备注
    String networkComment;//网报备注
    String paymentComment;//缴费状态备注
}
