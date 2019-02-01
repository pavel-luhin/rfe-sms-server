package by.bsu.rfe.smsservice.v2.controller;

import static by.bsu.rfe.smsservice.common.constants.AuthorityConstants.ROLE_APPLICATION;
import static by.bsu.rfe.smsservice.common.constants.AuthorityConstants.ROLE_USER;

import by.bsu.rfe.smsservice.v2.domain.SmsResult;
import by.bsu.rfe.smsservice.v2.domain.recipient.DefaultRecipient;
import by.bsu.rfe.smsservice.v2.domain.recipient.Recipient;
import by.bsu.rfe.smsservice.v2.domain.sms.DefaultSms;
import by.bsu.rfe.smsservice.v2.domain.sms.Sms;
import by.bsu.rfe.smsservice.v2.domain.sms.SmsRequest;
import by.bsu.rfe.smsservice.v2.sms.SmsService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/v2/sms")
@Secured({ROLE_USER, ROLE_APPLICATION})
public class SmsController {

  private final SmsService smsService;

  public SmsController(SmsService smsService) {
    this.smsService = smsService;
  }

  @PostMapping("/send")
  public SmsResult send(SmsRequest smsRequest) {
    Sms sms = createSms(smsRequest);
    return smsService.process(sms);
  }

  private Sms createSms(SmsRequest smsRequest) {
    List<Recipient> recipients = smsRequest.getRecipients()
        .stream()
        .map(rec -> new DefaultRecipient(rec.getName(), rec.getRecipientType(), rec.getParameters()))
        .collect(Collectors.toList());

    return new DefaultSms(smsRequest.getSmsType(), smsRequest.getTemplate(), recipients);
  }
}
