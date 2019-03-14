package by.bsu.rfe.smsservice.v2.sms.prepare;

import by.bsu.rfe.smsservice.v2.domain.SmsResult;
import by.bsu.rfe.smsservice.v2.domain.message.MessageBuilder;
import by.bsu.rfe.smsservice.v2.domain.sms.tmp.sms.CustomSmsRequestDTO;
import by.bsu.rfe.smsservice.v2.sms.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrepareCustomSmsService implements SmsService<CustomSmsRequestDTO> {

  private final SmsService delegate;
  private final MessageBuilder messageBuilder;

  @Autowired
  public PrepareCustomSmsService(SmsService delegate, MessageBuilder messageBuilder) {
    this.delegate = delegate;
    this.messageBuilder = messageBuilder;
  }

  @Override
  public SmsResult process(CustomSmsRequestDTO sms) {
    return null;
  }
}
