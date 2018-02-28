package by.bsu.rfe.smsservice.builder.sms.impl;

import static by.bsu.rfe.smsservice.util.MessageUtil.createMessage;
import static java.util.stream.Collectors.toMap;

import by.bsu.rfe.smsservice.builder.parameters.ParametersCollectorResolver;
import by.bsu.rfe.smsservice.builder.sms.BaseSmsRequestBuilder;
import by.bsu.rfe.smsservice.common.dto.sms.SmsQueueRequestDTO;
import by.bsu.rfe.smsservice.common.enums.RecipientType;
import by.bsu.rfe.smsservice.common.request.Request;
import by.bsu.rfe.smsservice.common.websms.WebSMSParam;
import by.bsu.rfe.smsservice.service.CredentialsService;
import by.bsu.rfe.smsservice.service.RecipientService;
import by.bsu.rfe.smsservice.validator.mobilenumber.MobileNumberValidator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.http.message.BasicNameValuePair;
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

    Map.Entry<String, RecipientType> recipientTypeEntry = new ImmutablePair<>(
        smsQueueRequestDTO.getRecipient(), smsQueueRequestDTO.getRecipientType());

    Map<String, String> parameters = new HashMap<>();
    parametersCollectorResolver.resolve(smsQueueRequestDTO.getRecipientType())
        .collectParameters(recipientTypeEntry, parameters);

    String message = createMessage(smsQueueRequestDTO.getContent(), parameters);
    List<String> numbers = fetchNumbers(recipientTypeEntry);

    Map<String, String> recipientsWithMessages = numbers
        .stream()
        .collect(toMap((rec) -> rec, (rec) -> message));

    String finalMessage = createArrayOfMessages(recipientsWithMessages);
    request
        .addParameter(new BasicNameValuePair(WebSMSParam.MESSAGES.getRequestParam(), finalMessage));

    return request;
  }
}
