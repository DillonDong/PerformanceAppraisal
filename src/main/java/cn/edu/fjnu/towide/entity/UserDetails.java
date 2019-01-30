package cn.edu.fjnu.towide.entity;

import cn.edu.fjnu.towide.entity.Authority;
import cn.edu.fjnu.towide.entity.Group;
import cn.edu.fjnu.towide.util.DateTimeUtil;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
public class UserDetails {

    private String username;
    private String realName;
    //  private String question;
//  private String answer;
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
    private Date lastSubmitTime;
    private String lastSubmitTimeString;
    private Date lastCheckTime;
    private String lastCheckTimeString;

    private String remarks; //备注
    private String deptId;  //部门
    private double baseSalary; //底薪
    private String code;//编号
    private String idPre;//身份证正面
    private String idAfter;//身份证背面

    private Integer enabled;
    private Set<Authority> authorities;
    private List<Group> groups;

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

    public void setLastSubmitTime(Date lastSubmitTime) {
        this.lastSubmitTime = lastSubmitTime;
        this.lastSubmitTimeString=DateTimeUtil.getDateTimeString(lastSubmitTime);
    }

    public void setLastCheckTime(Date lastCheckTime) {
        this.lastCheckTime = lastCheckTime;
        this.lastCheckTimeString=DateTimeUtil.getDateTimeString(lastCheckTime);
    }
}
