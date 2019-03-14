package by.bsu.rfe.smsservice.v2.sms;

import static by.bsu.rfe.smsservice.common.websms.WebSMSParam.APIKEY;
import static by.bsu.rfe.smsservice.common.websms.WebSMSParam.MESSAGES;
import static by.bsu.rfe.smsservice.common.websms.WebSMSParam.SENDER;
import static by.bsu.rfe.smsservice.common.websms.WebSMSParam.USER;

import by.bsu.rfe.smsservice.cache.credentials.CredentialsCache;
import by.bsu.rfe.smsservice.common.entity.CredentialsEntity;
import by.bsu.rfe.smsservice.common.websms.WebSMSRest;
import by.bsu.rfe.smsservice.v2.domain.DefaultSmsResult;
import by.bsu.rfe.smsservice.v2.domain.SmsResult;
import by.bsu.rfe.smsservice.v2.domain.message.Message;
import by.bsu.rfe.smsservice.v2.domain.sms.tmp.sms.PreparedSmsDTO;
import by.bsu.rfe.smsservice.v2.domain.websms.DefaultRequest;
import by.bsu.rfe.smsservice.v2.domain.websms.Request;
import by.bsu.rfe.smsservice.v2.domain.websms.Response;
import by.bsu.rfe.smsservice.v2.websms.WebSmsService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SendSmsService implements SmsService<PreparedSmsDTO> {

  private final WebSmsService webSmsService;
  private final CredentialsCache credentialsCache;

  public SendSmsService(WebSmsService webSmsService,
      CredentialsCache credentialsCache) {
    this.webSmsService = webSmsService;
    this.credentialsCache = credentialsCache;
  }

  @Override
  public SmsResult process(PreparedSmsDTO sms) {
    Request request = createRequest(sms);
    Response response = webSmsService.execute(request);
    return convertResponse(response);
  }

  private Request createRequest(PreparedSmsDTO sms) {
    Request request = new DefaultRequest(WebSMSRest.BULK_SEND_MESSAGE.getApiEndpoint());
    String senderName = sms.getSenderName();
    CredentialsEntity credentialsEntity = credentialsCache.getCredentialsBySenderNameForCurrentUser(senderName);

    String body = createBody(credentialsEntity.getSender(), sms.getMessages());

    request.addParameter(USER.getRequestParam(), credentialsEntity.getUsername());
    request.addParameter(APIKEY.getRequestParam(), credentialsEntity.getApiKey());
    request.addParameter(SENDER.getRequestParam(), credentialsEntity.getSender());
    request.addParameter(MESSAGES.getRequestParam(), body);

    return request;
  }

  private String createBody(String senderName, List<Message> messages) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("{");

    messages
        .forEach(message -> {
          stringBuilder.append("{\"recipient\":\"").append(message.getRecipient()).append("\",");
          stringBuilder.append("\"sender\":\"").append(senderName).append("\",");
          stringBuilder.append("\"message\":\"").append(message.getMessage()).append("\"},");
        });

    return stringBuilder.toString();
  }

  private SmsResult convertResponse(Response response) {
    return new DefaultSmsResult();
  }
}
