package by.bsu.rfe.smsservice.v2.domain.sms;

import by.bsu.rfe.smsservice.v2.domain.SmsType;
import by.bsu.rfe.smsservice.v2.domain.recipient.RecipientType;
import java.util.Map;

public final class SmsRequest {

  private final SmsType smsType;
  private final Map<String, RecipientType> recipients;
  private final String template;

  public SmsRequest(SmsType smsType,
      Map<String, RecipientType> recipients, String template) {
    this.smsType = smsType;
    this.recipients = recipients;
    this.template = template;
  }

  public SmsType getSmsType() {
    return smsType;
  }

  public Map<String, RecipientType> getRecipients() {
    return recipients;
  }

  public String getTemplate() {
    return template;
  }
}
