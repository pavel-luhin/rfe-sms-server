package by.bsu.rfe.smsservice.builder.sms.impl;

import by.bsu.rfe.smsservice.builder.parameters.ParametersCollectorResolver;
import by.bsu.rfe.smsservice.builder.sms.BaseSmsObjectBuilder;
import by.bsu.rfe.smsservice.common.dto.sms.TemplateSmsRequestDTO;
import by.bsu.rfe.smsservice.common.entity.CredentialsEntity;
import by.bsu.rfe.smsservice.common.entity.SmsQueueEntity;
import by.bsu.rfe.smsservice.common.entity.SmsTemplateEntity;
import by.bsu.rfe.smsservice.common.request.Request;
import by.bsu.rfe.smsservice.common.websms.WebSMSParam;
import by.bsu.rfe.smsservice.service.CredentialsService;
import by.bsu.rfe.smsservice.service.RecipientService;
import by.bsu.rfe.smsservice.service.SmsTemplateService;
import by.bsu.rfe.smsservice.validator.mobilenumber.MobileNumberValidator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemplateSmsObjectBuilder extends BaseSmsObjectBuilder<TemplateSmsRequestDTO> {

  private SmsTemplateService smsTemplateService;

  @Autowired
  public TemplateSmsObjectBuilder(
      ParametersCollectorResolver parametersCollectorResolver,
      CredentialsService credentialsService,
      List<MobileNumberValidator> mobileNumberValidators,
      RecipientService recipientService,
      SmsTemplateService smsTemplateService) {
    super(parametersCollectorResolver, credentialsService, mobileNumberValidators,
        recipientService);
    this.smsTemplateService = smsTemplateService;
  }

  @Override
  public Request build(TemplateSmsRequestDTO smsRequestDTO) {
    SmsTemplateEntity smsTemplateEntity = smsTemplateService
        .findSMSTemplate(smsRequestDTO.getTemplateName());
    String message = smsTemplateEntity.getTemplate();

    Request request = buildBaseRequest(smsRequestDTO);
    Map<String, String> recipientsByMessages = new HashMap<>();

    smsRequestDTO.getRecipients()
        .entrySet()
        .forEach(recipient -> {
          Map<String, String> recipientParameters = new HashMap<>();

          parametersCollectorResolver.resolve(recipient.getValue())
              .collectParameters(recipient, recipientParameters);
          recipientsByMessages.putAll(processMessagesAndRecipients(recipientParameters,
              recipient, message));
        });

    String finalMessage = createArrayOfMessages(recipientsByMessages);
    request
        .addParameter(new BasicNameValuePair(WebSMSParam.MESSAGES.getRequestParam(), finalMessage));

    return request;
  }

  @Override
  public SmsQueueEntity buildQueue(TemplateSmsRequestDTO smsRequestDTO) {
    SmsTemplateEntity smsTemplateEntity = smsTemplateService
        .findSMSTemplate(smsRequestDTO.getTemplateName());
    String message = smsTemplateEntity.getTemplate();

    SmsQueueEntity smsQueueEntity = new SmsQueueEntity();
    CredentialsEntity credentialsEntity = credentialsService
        .getCredentialsForSenderName(smsRequestDTO.getSenderName());
    smsQueueEntity.setCredentials(credentialsEntity);
    smsQueueEntity.setMessage(message);

    StringBuilder numbersString = new StringBuilder();

    smsRequestDTO.getRecipients()
        .entrySet()
        .forEach(recipient -> {
          List<String> numbers = fetchNumbers(recipient);
          numbersString.append(String.join(",", numbers));
        });
    smsQueueEntity.setNumbers(numbersString.toString());
    return smsQueueEntity;
  }
}
