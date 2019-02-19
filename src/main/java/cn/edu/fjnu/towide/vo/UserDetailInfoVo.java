package cn.edu.fjnu.towide.vo;

import lombok.Data;

@SuppressWarnings("all")
@Data
public class UserDetailInfoVo {

    private String username;
    private String realName;
    private double baseSalary;//底薪
    private String code;//编号
    private String deptId;//部门名称
    private long group;//权组id
    private String remarks;//备注
    private String idPre;//身份证正面
    private String idAfter;//身份证背面

}
