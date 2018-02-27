package by.bsu.rfe.smsservice.builder.sms;

import by.bsu.rfe.smsservice.builder.sms.impl.BulkSmsObjectBuilder;
import by.bsu.rfe.smsservice.builder.sms.impl.CustomSmsObjectBuilder;
import by.bsu.rfe.smsservice.builder.sms.impl.TemplateSmsObjectBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SmsObjectBuilderHolder {

  private BulkSmsObjectBuilder bulkSmsRequestBuilder;
  private CustomSmsObjectBuilder customSmsRequestBuilder;
  private TemplateSmsObjectBuilder templateSmsRequestBuilder;

  @Autowired
  public SmsObjectBuilderHolder(
      BulkSmsObjectBuilder bulkSmsRequestBuilder,
      CustomSmsObjectBuilder customSmsRequestBuilder,
      TemplateSmsObjectBuilder templateSmsRequestBuilder) {
    this.bulkSmsRequestBuilder = bulkSmsRequestBuilder;
    this.customSmsRequestBuilder = customSmsRequestBuilder;
    this.templateSmsRequestBuilder = templateSmsRequestBuilder;
  }

  public BaseSmsObjectBuilder getBulkSmsRequestBuilder() {
    return bulkSmsRequestBuilder;
  }

  public BaseSmsObjectBuilder getCustomSmsRequestBuilder() {
    return customSmsRequestBuilder;
  }

  public BaseSmsObjectBuilder getTemplateSmsRequestBuilder() {
    return templateSmsRequestBuilder;
  }
}
