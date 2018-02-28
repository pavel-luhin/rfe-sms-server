package by.bsu.rfe.smsservice.common.dto.sms;

import lombok.Data;

@Data
public class SmsQueueRequestDTO extends BaseSmsRequestDTO {

  private Integer id;
  private String numbers;
  private String content;
}
