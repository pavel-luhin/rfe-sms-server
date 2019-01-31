package by.bsu.rfe.smsservice.v2.sms;

import by.bsu.rfe.smsservice.v2.domain.DefaultSmsResult;
import by.bsu.rfe.smsservice.v2.domain.Sms;
import by.bsu.rfe.smsservice.v2.domain.SmsResult;
import by.bsu.rfe.smsservice.v2.domain.websms.DefaultRequest;
import by.bsu.rfe.smsservice.v2.domain.websms.Request;
import by.bsu.rfe.smsservice.v2.domain.websms.Response;
import by.bsu.rfe.smsservice.v2.websms.WebSmsService;
import org.springframework.stereotype.Service;

@Service
public class SendSmsService implements SmsService {

  private final WebSmsService webSmsService;

  public SendSmsService(WebSmsService webSmsService) {
    this.webSmsService = webSmsService;
  }

  @Override
  public SmsResult process(Sms sms) {
    Request request = new DefaultRequest();//TODO convert sms to request here
    Response response = webSmsService.execute(request);
    return new DefaultSmsResult();
  }
}
