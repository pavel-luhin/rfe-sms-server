package by.bsu.rfe.smsservice.v2.sms;

import by.bsu.rfe.smsservice.v2.domain.SmsResult;
import by.bsu.rfe.smsservice.v2.domain.message.MessageBuilder;
import by.bsu.rfe.smsservice.v2.domain.sms.Message;
import by.bsu.rfe.smsservice.v2.domain.sms.Sms;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrepareSmsService implements SmsService {

  private final SmsService delegate;
  private final MessageBuilder messageBuilder;

  @Autowired
  public PrepareSmsService(SmsService delegate, MessageBuilder messageBuilder) {
    this.delegate = delegate;
    this.messageBuilder = messageBuilder;
  }

  @Override
  public SmsResult process(Sms sms) {
    List<Message> messages = messageBuilder.build(sms.getRecipients(), sms.getTemplate());
    sms.getMessages().addAll(messages);
    return delegate.process(sms);
  }
}
