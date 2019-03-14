package by.bsu.rfe.smsservice.v2.domain.sms.tmp.sms;

import by.bsu.rfe.smsservice.v2.domain.SmsType;
import by.bsu.rfe.smsservice.v2.domain.message.Message;
import java.util.List;

public class PreparedSmsDTO extends BaseSmsRequestDTO {

  private final List<Message> messages;

  public PreparedSmsDTO(String senderName, boolean duplicateEmail, boolean skipQueue,
      SmsType smsType, List<Message> messages) {
    super(senderName, duplicateEmail, skipQueue, smsType);
    this.messages = messages;
  }

  public List<Message> getMessages() {
    return messages;
  }
}
