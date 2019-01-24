package cn.edu.fjnu.towide.entity;

import java.util.Date;

import cn.edu.fjnu.towide.util.DateTimeUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class OperationLog {

  private String username;

  private String ip;

  private String examinee;

  private String operationContent;

  @JsonIgnore
  private Date operationDateTime;

  private String operationDateTimeString;

  private String operationResult;

  private String reason;


  public String getOperationDateTimeString() {
    return DateTimeUtil.getDateTimeString(getOperationDateTime());
  }



}