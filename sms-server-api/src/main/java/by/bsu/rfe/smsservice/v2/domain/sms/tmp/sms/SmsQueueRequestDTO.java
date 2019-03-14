package by.bsu.rfe.smsservice.v2.domain.sms.tmp.sms;

import by.bsu.rfe.smsservice.common.enums.RecipientType;
import by.bsu.rfe.smsservice.v2.domain.SmsType;
import java.util.Map;
import lombok.Data;
import org.dozer.Mapping;

public class SmsQueueRequestDTO extends BaseSmsRequestDTO {

  private Integer id;
  private String recipient;
  private RecipientType recipientType;
  @Mapping("message")
  private String content;
  private Map<String, String> parameters;
  private String smsType;
  private String initiatedBy;

  public SmsQueueRequestDTO(String senderName, boolean duplicateEmail, boolean skipQueue,
      SmsType smsType, Integer id, String recipient,
      RecipientType recipientType, String content, Map<String, String> parameters, String smsType1,
      String initiatedBy) {
    super(senderName, duplicateEmail, skipQueue, smsType);
    this.id = id;
    this.recipient = recipient;
    this.recipientType = recipientType;
    this.content = content;
    this.parameters = parameters;
    this.smsType = smsType1;
    this.initiatedBy = initiatedBy;
  }

  @Override
  public boolean isSkipQueue() {
    return true;
  }
}
