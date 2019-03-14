package by.bsu.rfe.smsservice.builder.sms;

import static by.bsu.rfe.smsservice.common.websms.WebSMSParam.APIKEY;
import static by.bsu.rfe.smsservice.common.websms.WebSMSParam.SENDER;
import static by.bsu.rfe.smsservice.common.websms.WebSMSParam.TEST;
import static by.bsu.rfe.smsservice.common.websms.WebSMSParam.USER;
import static by.bsu.rfe.smsservice.util.MessageUtil.createMessage;
import static java.util.stream.Collectors.toMap;

import by.bsu.rfe.smsservice.builder.WebSmsRequestBuilder;
import by.bsu.rfe.smsservice.builder.parameters.ParametersCollectorResolver;
import by.bsu.rfe.smsservice.common.dto.RecipientDTO;
import by.bsu.rfe.smsservice.common.dto.sms.BaseSmsRequestDTO;
import by.bsu.rfe.smsservice.common.entity.CredentialsEntity;
import by.bsu.rfe.smsservice.common.entity.GroupEntity;
import by.bsu.rfe.smsservice.common.entity.PersonEntity;
import by.bsu.rfe.smsservice.common.request.Request;
import by.bsu.rfe.smsservice.common.websms.WebSMSRest;
import by.bsu.rfe.smsservice.exception.CredentialsNotFoundException;
import by.bsu.rfe.smsservice.service.CredentialsService;
import by.bsu.rfe.smsservice.service.RecipientService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.http.message.BasicNameValuePair;

@Slf4j
public abstract class BaseSmsRequestBuilder<T extends BaseSmsRequestDTO> extends
    WebSmsRequestBuilder<T> {

  protected ParametersCollectorResolver parametersCollectorResolver;
  protected CredentialsService credentialsService;
  protected RecipientService recipientService;

  public BaseSmsRequestBuilder(
      ParametersCollectorResolver parametersCollectorResolver,
      CredentialsService credentialsService,
      RecipientService recipientService) {
    this.parametersCollectorResolver = parametersCollectorResolver;
    this.credentialsService = credentialsService;
    this.recipientService = recipientService;
  }

  protected Request buildBaseRequest(T requestDTO) {
    Request request = new Request();

    CredentialsEntity credentials = credentialsService
        .getUserCredentialsForSenderName(requestDTO.getSenderName());

    if (credentials == null) {
      throw new CredentialsNotFoundException(
          "Credentials with sender name " + requestDTO.getSenderName() + " not found");
    }

    request.addParameter(new BasicNameValuePair(USER.getRequestParam(), credentials.getUsername()));
    request.addParameter(new BasicNameValuePair(APIKEY.getRequestParam(), credentials.getApiKey()));
    request.addParameter(new BasicNameValuePair(SENDER.getRequestParam(), credentials.getSender()));
    request.addParameter(
        new BasicNameValuePair(TEST.getRequestParam(), System.getProperty("sms.test")));
    request.setApiEndpoint(WebSMSRest.BULK_SEND_MESSAGE.getApiEndpoint());

    return request;
  }

  protected String createArrayOfMessages(Map<String, String> messages, String senderName) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("[");

    for (Map.Entry<String, String> message : messages.entrySet()) {
      stringBuilder.append("{\"recipient\":\"").append(message.getKey()).append("\",");

      //TODO add bulk sms sending only for bulk types, use single sms sending for custom, template and queue
      stringBuilder.append("\"sender\":\"").append(senderName).append("\",");
      stringBuilder.append("\"message\":\"").append(message.getValue()).append("\"},");
    }

    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
    stringBuilder.append("]");
    return stringBuilder.toString();
  }

  protected Map<String, String> processMessagesAndRecipients(Map<String, String> parameters,
      RecipientDTO recipient, String messageTemplate) {
    List<String> recipients = recipientService.fetchNumbers(recipient);
    String message = createMessage(messageTemplate, parameters);

    return recipients
        .stream()
        .collect(toMap(
            (rec) -> rec,
            (rec) -> message)
        );
  }
}
