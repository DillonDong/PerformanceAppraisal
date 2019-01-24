package cn.edu.fjnu.towide.entity;


import cn.edu.fjnu.towide.util.DateTimeUtil;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
public class AdminDetails {

  private String username;
  private String realName;
//  private String question;
//  private String answer;
  private String email;
  private String phone;
  private String sex;
  private Date birthday;
  private Date createTime;
  private String createTimeString;
  private Date updateTime;
  private String updateTimeString;

  private Integer enabled;
  private Set<Authority> authorities;
  private List<Group> groups;

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
    this.createTimeString = DateTimeUtil.getDateTimeString(createTime);
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
    this.updateTimeString = DateTimeUtil.getDateTimeString(updateTime);
  }
}
