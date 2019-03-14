package by.bsu.rfe.smsservice.v2.domain.sms.tmp.sms;

import by.bsu.rfe.smsservice.v2.domain.SmsType;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;

public abstract class BaseSmsRequestDTO {

  @Size(min = 1)
  @Mapping("credentials.sender")
  private String senderName;
  private boolean duplicateEmail;
  private boolean skipQueue;
  private SmsType smsType;

  public BaseSmsRequestDTO(String senderName, boolean duplicateEmail, boolean skipQueue,
      SmsType smsType) {
    this.senderName = senderName;
    this.duplicateEmail = duplicateEmail;
    this.skipQueue = skipQueue;
    this.smsType = smsType;
  }

  public String getSenderName() {
    return senderName;
  }

  public boolean isDuplicateEmail() {
    return duplicateEmail;
  }

  public boolean isSkipQueue() {
    return skipQueue;
  }

  public SmsType getSmsType() {
    return smsType;
  }
}
