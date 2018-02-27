package by.bsu.rfe.smsservice.common.dto.sms;

import lombok.Getter;

@Getter
public abstract class BaseSmsRequestDTO {

  private boolean duplicateEmail;
  private String senderName;
}
