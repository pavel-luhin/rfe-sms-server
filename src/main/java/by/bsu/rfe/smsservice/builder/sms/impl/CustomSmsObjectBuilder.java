package by.bsu.rfe.smsservice.builder.sms.impl;

import by.bsu.rfe.smsservice.builder.parameters.ParametersCollectorResolver;
import by.bsu.rfe.smsservice.builder.sms.BaseSmsObjectBuilder;
import by.bsu.rfe.smsservice.common.dto.sms.CustomSmsRequestDTO;
import by.bsu.rfe.smsservice.common.entity.CredentialsEntity;
import by.bsu.rfe.smsservice.common.entity.SmsQueueEntity;
import by.bsu.rfe.smsservice.common.request.Request;
import by.bsu.rfe.smsservice.common.websms.WebSMSParam;
import by.bsu.rfe.smsservice.service.CredentialsService;
import by.bsu.rfe.smsservice.service.RecipientService;
import by.bsu.rfe.smsservice.validator.mobilenumber.MobileNumberValidator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomSmsObjectBuilder extends BaseSmsObjectBuilder<CustomSmsRequestDTO> {

  @Autowired
  public CustomSmsObjectBuilder(
      ParametersCollectorResolver parametersCollectorResolver,
      CredentialsService credentialsService,
      List<MobileNumberValidator> mobileNumberValidators,
      RecipientService recipientService,
      RecipientService recipientService1) {
    super(parametersCollectorResolver, credentialsService, mobileNumberValidators,
        recipientService);
  }

  @Override
  public Request build(CustomSmsRequestDTO requestDTO) {
    Request request = buildBaseRequest(requestDTO);
    Map<String, String> recipientsByMessages = new HashMap<>();

    requestDTO.getRecipients()
        .entrySet()
        .forEach(recipient -> {
          Map<String, String> recipientParameters = new HashMap<>();

          parametersCollectorResolver.resolve(recipient.getValue())
              .collectParameters(recipient, recipientParameters);
          recipientsByMessages.putAll(processMessagesAndRecipients(recipientParameters,
              recipient, requestDTO.getContent()));
        });

    String finalMessage = createArrayOfMessages(recipientsByMessages);
    request
        .addParameter(new BasicNameValuePair(WebSMSParam.MESSAGES.getRequestParam(), finalMessage));

    return request;
  }

  @Override
  public SmsQueueEntity buildQueue(CustomSmsRequestDTO requestDTO) {
    SmsQueueEntity smsQueueEntity = new SmsQueueEntity();
    CredentialsEntity credentialsEntity = credentialsService
        .getCredentialsForSenderName(requestDTO.getSenderName());
    smsQueueEntity.setCredentials(credentialsEntity);
    smsQueueEntity.setMessage(requestDTO.getContent());

    StringBuilder numbersString = new StringBuilder();

    requestDTO.getRecipients()
        .entrySet()
        .forEach(recipient -> {
          List<String> numbers = fetchNumbers(recipient);
          numbersString.append(String.join(",", numbers));
        });
    smsQueueEntity.setNumbers(numbersString.toString());
    return smsQueueEntity;
  }
}
