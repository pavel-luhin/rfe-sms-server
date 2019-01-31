package by.bsu.rfe.smsservice.v2.domain.sms;

import by.bsu.rfe.smsservice.v2.domain.SmsType;
import by.bsu.rfe.smsservice.v2.domain.recipient.Recipient;
import java.util.ArrayList;
import java.util.List;

public class DefaultSms implements Sms {

  private final SmsType smsType;
  private final List<Message> messages = new ArrayList<>();
  private final String template;
  private final List<Recipient> recipients;

  public DefaultSms(SmsType smsType, String template,
      List<Recipient> recipients) {
    this.smsType = smsType;
    this.template = template;
    this.recipients = recipients;
  }

  @Override
  public List<Message> getMessages() {
    return null;
  }

  @Override
  public List<Recipient> getRecipients() {
    return null;
  }

  @Override
  public SmsType getType() {
    return smsType;
  }

  @Override
  public String getTemplate() {
    return template;
  }
}
