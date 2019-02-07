package cn.edu.fjnu.towide.entity;

import lombok.Data;

@Data
public class Parameter {

  private Double meritsPay;//绩效奖
  private Double jxblLeft;//绩效奖正比递增左边
  private Double minimumTurnover;//保底营业额
  private Double jxqjLeft;//绩效区间左边
  private Double jxqjRight;//绩效区间右边
  private Double ccqjLeft;//抽成区间左边分数
  private Double ccqjMiddle;//抽成区间中间
  private Double ccqjRight;//抽成区间右边
  private Double ccqjzzLeft;//抽成区间左中区间的左比例
  private Double ccqjzzRight;//抽成区间左中区间的右比例
  private Double ccqjzyLeft;//抽成区间中右区间的左比例
  private Double ccqjzyRight;//抽成区间中右区间的右比例



}
