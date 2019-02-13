package cn.edu.fjnu.towide.entity;

import lombok.Data;

@Data
public class UserAssessment {

  private String id;
  private String userId;
  private String assessmentItem;
  private double count;
  private String time;
  private double level;

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


  public String getAssessmentItem() {
    return assessmentItem;
  }

  public void setAssessmentItem(String assessmentItem) {
    this.assessmentItem = assessmentItem;
  }


  public double getCount() {
    return count;
  }

  public void setCount(double count) {
    this.count = count;
  }


  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

}
