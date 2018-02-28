package by.bsu.rfe.smsservice.common.dto.sms;

import by.bsu.rfe.smsservice.common.enums.RecipientType;
import lombok.Data;

@Data
public class SmsQueueRequestDTO extends BaseSmsRequestDTO {

  private Integer id;
  private String recipient;
  private RecipientType recipientType;
  private String content;

  public SmsQueueRequestDTO() {
    this.setSkipQueue(true);
  }
}
