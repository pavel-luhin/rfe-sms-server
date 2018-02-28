package by.bsu.rfe.smsservice.builder;

import by.bsu.rfe.smsservice.builder.sms.impl.BulkSmsRequestBuilder;
import by.bsu.rfe.smsservice.builder.sms.impl.CustomSmsRequestBuilder;
import by.bsu.rfe.smsservice.builder.sms.impl.QueueSmsRequestBuilder;
import by.bsu.rfe.smsservice.builder.sms.impl.TemplateSmsRequestBuilder;
import by.bsu.rfe.smsservice.common.dto.sms.BulkSmsRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.CustomSmsRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.SmsQueueRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.TemplateSmsRequestDTO;
import by.bsu.rfe.smsservice.common.request.BalanceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RequestBuilderHolder {

  private BulkSmsRequestBuilder bulkSmsRequestBuilder;
  private CustomSmsRequestBuilder customSmsRequestBuilder;
  private TemplateSmsRequestBuilder templateSmsRequestBuilder;
  private QueueSmsRequestBuilder queueSmsRequestBuilder;
  private BalanceRequestBuilder balanceRequestBuilder;

  @Autowired
  public RequestBuilderHolder(
      BulkSmsRequestBuilder bulkSmsRequestBuilder,
      CustomSmsRequestBuilder customSmsRequestBuilder,
      TemplateSmsRequestBuilder templateSmsRequestBuilder,
      QueueSmsRequestBuilder queueSmsRequestBuilder,
      BalanceRequestBuilder balanceRequestBuilder) {
    this.bulkSmsRequestBuilder = bulkSmsRequestBuilder;
    this.customSmsRequestBuilder = customSmsRequestBuilder;
    this.templateSmsRequestBuilder = templateSmsRequestBuilder;
    this.queueSmsRequestBuilder = queueSmsRequestBuilder;
    this.balanceRequestBuilder = balanceRequestBuilder;
  }

  public WebSmsRequestBuilder<SmsQueueRequestDTO> getQueueSmsRequestBuilder() {
    return queueSmsRequestBuilder;
  }

  public WebSmsRequestBuilder<BulkSmsRequestDTO> getBulkSmsRequestBuilder() {
    return bulkSmsRequestBuilder;
  }

  public WebSmsRequestBuilder<CustomSmsRequestDTO> getCustomSmsRequestBuilder() {
    return customSmsRequestBuilder;
  }

  public WebSmsRequestBuilder<TemplateSmsRequestDTO> getTemplateSmsRequestBuilder() {
    return templateSmsRequestBuilder;
  }

  public WebSmsRequestBuilder<BalanceRequest> getBalanceRequestBuilder() {
    return balanceRequestBuilder;
  }
}
