package cn.edu.fjnu.towide.vo;

import lombok.Data;

@SuppressWarnings("all")
@Data
public class UserInfoVo {

    private String username;
    private String realName;
    private String baseSalary;//底薪
    private String code;//编号
    private String name;//部门名称
    private String remarks;//备注


}
