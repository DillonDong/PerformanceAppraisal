package cn.edu.fjnu.towide.entity;


import cn.edu.fjnu.towide.util.DateTimeUtil;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
public class UserDetails {

  private String username;//微信openId
  private String realName;
  private String idCardNum;
  private String email;
  private String phone;
  private String sex;
  private Date birthday;
  private String birthdayString;
  private String checkSum;
  private Date createTime;
  private String createTimeString;
  private Date updateTime;
  private String updateTimeString;
  private String checkState;
  private Integer enrolState;
  private String head; //头像
  private String province; //省份
  private String city; //城市
  private String nickname; //昵称

  private Integer enabled;
  private Set<Authority> authorities;
  private List<Group> groups;
  private String schoolCode;  //院校编码
  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
    this.createTimeString= DateTimeUtil.getDateTimeString(createTime);
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
    this.birthdayString=DateTimeUtil.getDateTimeString(birthday);
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
    this.updateTimeString=DateTimeUtil.getDateTimeString(updateTime);
  }


}
