package cn.edu.fjnu.towide.entity;

import lombok.Data;

@Data
public class GroupAuthorities {
    private Long id;
    private Long groupId;
    private String authority;
    private String description;

}