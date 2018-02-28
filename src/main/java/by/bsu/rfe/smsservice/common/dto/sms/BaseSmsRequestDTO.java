package by.bsu.rfe.smsservice.common.dto.sms;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseSmsRequestDTO {

  private boolean duplicateEmail;
  private String senderName;
}
