package cn.edu.fjnu.towide.entity;

import lombok.Data;

@Data
public class Parameter {

  private double meritsPay;//绩效奖
  private double jxblLeft;//绩效奖正比递增左边
  private double minimumTurnover;//保底营业额
  private double jxqjLeft;//绩效区间左边
  private double jxqjRight;//绩效区间右边
  private double ccqjLeft;//抽成区间左边分数
  private double ccqjMiddle;//抽成区间中间
  private double ccqjRight;//抽成区间右边
  private double ccqjzzLeft;//抽成区间左中区间的左比例
  private double ccqjzzRight;//抽成区间左中区间的右比例
  private double ccqjzyLeft;//抽成区间中右区间的左比例
  private double ccqjzyRight;//抽成区间中右区间的右比例


  public double getMeritsPay() {
    return meritsPay;
  }

  public void setMeritsPay(double meritsPay) {
    this.meritsPay = meritsPay;
  }


  public double getJxblLeft() {
    return jxblLeft;
  }

  public void setJxblLeft(double jxblLeft) {
    this.jxblLeft = jxblLeft;
  }


  public double getMinimumTurnover() {
    return minimumTurnover;
  }

  public void setMinimumTurnover(double minimumTurnover) {
    this.minimumTurnover = minimumTurnover;
  }


  public double getJxqjLeft() {
    return jxqjLeft;
  }

  public void setJxqjLeft(double jxqjLeft) {
    this.jxqjLeft = jxqjLeft;
  }


  public double getJxqjRight() {
    return jxqjRight;
  }

  public void setJxqjRight(double jxqjRight) {
    this.jxqjRight = jxqjRight;
  }


  public double getCcqjLeft() {
    return ccqjLeft;
  }

  public void setCcqjLeft(double ccqjLeft) {
    this.ccqjLeft = ccqjLeft;
  }


  public double getCcqjMiddle() {
    return ccqjMiddle;
  }

  public void setCcqjMiddle(double ccqjMiddle) {
    this.ccqjMiddle = ccqjMiddle;
  }


  public double getCcqjRight() {
    return ccqjRight;
  }

  public void setCcqjRight(double ccqjRight) {
    this.ccqjRight = ccqjRight;
  }


  public double getCcqjzzLeft() {
    return ccqjzzLeft;
  }

  public void setCcqjzzLeft(double ccqjzzLeft) {
    this.ccqjzzLeft = ccqjzzLeft;
  }


  public double getCcqjzzRight() {
    return ccqjzzRight;
  }

  public void setCcqjzzRight(double ccqjzzRight) {
    this.ccqjzzRight = ccqjzzRight;
  }


  public double getCcqjzyLeft() {
    return ccqjzyLeft;
  }

  public void setCcqjzyLeft(double ccqjzyLeft) {
    this.ccqjzyLeft = ccqjzyLeft;
  }


  public double getCcqjzyRight() {
    return ccqjzyRight;
  }

  public void setCcqjzyRight(double ccqjzyRight) {
    this.ccqjzyRight = ccqjzyRight;
  }

}
