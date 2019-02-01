package by.bsu.rfe.smsservice.v2.domain.sms;

import by.bsu.rfe.smsservice.v2.domain.SmsType;
import by.bsu.rfe.smsservice.v2.domain.recipient.RequestRecipient;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public final class SmsRequest {

  private final SmsType smsType;
  private final List<RequestRecipient> recipients;
  private final String template;

  @JsonCreator
  public SmsRequest(
      @JsonProperty("smsType") SmsType smsType,
      @JsonProperty("recipients") List<RequestRecipient> recipients,
      @JsonProperty("template") String template) {
    this.smsType = smsType;
    this.recipients = recipients;
    this.template = template;
  }

  public SmsType getSmsType() {
    return smsType;
  }

  public List<RequestRecipient> getRecipients() {
    return recipients;
  }

  public String getTemplate() {
    return template;
  }
}
