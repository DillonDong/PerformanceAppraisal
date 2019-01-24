package cn.edu.fjnu.towide.entity;


public class UserWages {

  private String id;
  private String userId;
  private double turnover;
  private double totalScore;
  private double meritsPay;
  private double percentage;
  private double totalWages;
  private String time;
  private long examine;


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


  public long getExamine() {
    return examine;
  }

  public void setExamine(long examine) {
    this.examine = examine;
  }

}
