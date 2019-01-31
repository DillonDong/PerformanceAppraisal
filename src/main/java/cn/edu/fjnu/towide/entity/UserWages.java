package cn.edu.fjnu.towide.entity;

import lombok.Data;

@Data
public class UserWages {

  private String id;
  private String userId;//用户名
  private double turnover;//营业额
  private double totalScore;//总分
  private double meritsPay;//绩效奖
  private double percentage;//抽成
  private double totalWages;//总工资
  private String time;
  private int examine;//是否审核


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }


  public double getTurnover() {
    return turnover;
  }

  public void setTurnover(double turnover) {
    this.turnover = turnover;
  }


  public double getTotalScore() {
    return totalScore;
  }

  public void setTotalScore(double totalScore) {
    this.totalScore = totalScore;
  }


  public double getMeritsPay() {
    return meritsPay;
  }

  public void setMeritsPay(double meritsPay) {
    this.meritsPay = meritsPay;
  }


  public double getPercentage() {
    return percentage;
  }

  public void setPercentage(double percentage) {
    this.percentage = percentage;
  }


  public double getTotalWages() {
    return totalWages;
  }

  public void setTotalWages(double totalWages) {
    this.totalWages = totalWages;
  }


  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }



}
