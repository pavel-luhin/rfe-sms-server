package by.bsu.rfe.smsservice.v2.domain.sms;

import by.bsu.rfe.smsservice.v2.domain.SmsType;
import java.util.ArrayList;
import java.util.List;

public class DefaultSms implements Sms {

  private final SmsType smsType;
  private final List<Message> messages = new ArrayList<>();
  private final String template;

  public DefaultSms(SmsType smsType, String template) {
    this.smsType = smsType;
    this.template = template;
  }

  @Override
  public List<Message> getMessages() {
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
