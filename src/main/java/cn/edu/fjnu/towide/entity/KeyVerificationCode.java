package cn.edu.fjnu.towide.entity;

import lombok.Data;

import java.util.Date;


@Data
public class KeyVerificationCode {
  private String keyWord;

  private String verificationCode;

  private Date generateTime;

}