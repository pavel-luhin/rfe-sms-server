package by.bsu.rfe.smsservice.builder.sms.impl;

import by.bsu.rfe.smsservice.builder.parameters.ParametersCollectorResolver;
import by.bsu.rfe.smsservice.builder.sms.BaseSmsRequestBuilder;
import by.bsu.rfe.smsservice.common.dto.sms.SmsQueueRequestDTO;
import by.bsu.rfe.smsservice.common.request.Request;
import by.bsu.rfe.smsservice.service.CredentialsService;
import by.bsu.rfe.smsservice.service.RecipientService;
import by.bsu.rfe.smsservice.validator.mobilenumber.MobileNumberValidator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueueSmsRequestBuilder extends BaseSmsRequestBuilder<SmsQueueRequestDTO> {

  @Autowired
  public QueueSmsRequestBuilder(
      ParametersCollectorResolver parametersCollectorResolver,
      CredentialsService credentialsService,
      List<MobileNumberValidator> mobileNumberValidators,
      RecipientService recipientService) {
    super(parametersCollectorResolver, credentialsService, mobileNumberValidators,
        recipientService);
  }

  @Override
  public Request build(SmsQueueRequestDTO smsQueueRequestDTO) {
    Request request = buildBaseRequest(smsQueueRequestDTO);

    return null;
  }
}
