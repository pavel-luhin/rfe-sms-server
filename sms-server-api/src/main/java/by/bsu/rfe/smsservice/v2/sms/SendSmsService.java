package by.bsu.rfe.smsservice.v2.sms;

import static by.bsu.rfe.smsservice.common.websms.WebSMSParam.APIKEY;
import static by.bsu.rfe.smsservice.common.websms.WebSMSParam.MESSAGES;
import static by.bsu.rfe.smsservice.common.websms.WebSMSParam.SENDER;
import static by.bsu.rfe.smsservice.common.websms.WebSMSParam.USER;

import by.bsu.rfe.smsservice.common.websms.WebSMSRest;
import by.bsu.rfe.smsservice.v2.domain.DefaultSmsResult;
import by.bsu.rfe.smsservice.v2.domain.SmsResult;
import by.bsu.rfe.smsservice.v2.domain.sms.Message;
import by.bsu.rfe.smsservice.v2.domain.sms.Sms;
import by.bsu.rfe.smsservice.v2.domain.websms.DefaultRequest;
import by.bsu.rfe.smsservice.v2.domain.websms.Request;
import by.bsu.rfe.smsservice.v2.domain.websms.Response;
import by.bsu.rfe.smsservice.v2.websms.WebSmsService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SendSmsService implements SmsService {

  private final WebSmsService webSmsService;

  public SendSmsService(WebSmsService webSmsService) {
    this.webSmsService = webSmsService;
  }

  @Override
  public SmsResult process(Sms sms) {
    Request request = createRequest(sms);
    Response response = webSmsService.execute(request);
    return convertResponse(response);
  }

  private Request createRequest(Sms sms) {
    Request request = new DefaultRequest(WebSMSRest.BULK_SEND_MESSAGE.getApiEndpoint());
    String body = createBody(sms.getMessages());

    request.addParameter(USER.getRequestParam(), "mock");//TODO add support this
    request.addParameter(APIKEY.getRequestParam(), "mock");//TODO add support this
    request.addParameter(SENDER.getRequestParam(), "mock");//TODO add support this
    request.addParameter(MESSAGES.getRequestParam(), body);

    return request;
  }

  private String createBody(List<Message> messages) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("{");

    messages
        .forEach(message -> {
          stringBuilder.append("{\"recipient\":\"").append(message.getRecipient().getName()).append("\",");
          stringBuilder.append("\"sender\":\"").append("Vizitka").append("\",");//TODO add support multiple sender names
          stringBuilder.append("\"message\":\"").append(message.getMessage()).append("\"},");
        });

    return stringBuilder.toString();
  }

  private SmsResult convertResponse(Response response) {
    return new DefaultSmsResult();
  }
}
