package by.bsu.rfe.smsservice.v2.domain.sms;

import by.bsu.rfe.smsservice.v2.domain.SmsType;
import by.bsu.rfe.smsservice.v2.domain.message.Message;
import by.bsu.rfe.smsservice.v2.domain.recipient.Recipient;
import java.util.ArrayList;
import java.util.List;

public final class DefaultSms implements Sms {

  private final SmsType smsType;
  private final List<Message> messages = new ArrayList<>();
  private final String template;
  private final List<Recipient> recipients;
  private final String senderName;

  public DefaultSms(SmsType smsType, String template,
      List<Recipient> recipients, String senderName) {
    this.smsType = smsType;
    this.template = template;
    this.recipients = recipients;
    this.senderName = senderName;
  }

  @Override
  public List<Message> getMessages() {
    return messages;
  }

  @Override
  public List<Recipient> getRecipients() {
    return recipients;
  }

  @Override
  public SmsType getType() {
    return smsType;
  }

  @Override
  public String getTemplate() {
    return template;
  }

  public String getSenderName() {
    return senderName;
  }


}
