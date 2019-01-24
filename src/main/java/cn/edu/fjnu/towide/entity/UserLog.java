package cn.edu.fjnu.towide.entity;

import lombok.Data;

import java.util.Date;

@Data
public class UserLog {

  private Long id;
  private String username;
  private String ip;
  private String operationContent;
  private Date operationDateTime;

}
