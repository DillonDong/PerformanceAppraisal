package cn.edu.fjnu.towide.entity;

import lombok.Data;

import java.util.List;

@Data
public class Group {
    private Long id;
    private String groupName;
    private String description;
    private String remark;
    private List<User> users;
    private List<GroupAuthorities> groupAuthorities;

}