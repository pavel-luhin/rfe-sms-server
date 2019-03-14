package by.bsu.rfe.smsservice.v2.sms;

import by.bsu.rfe.smsservice.v2.domain.SmsResult;
import by.bsu.rfe.smsservice.v2.domain.sms.tmp.sms.PreparedSmsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveSmsService implements SmsService<PreparedSmsDTO> {

  private final SmsService delegate;

  @Autowired
  public SaveSmsService(SmsService delegate) {
    this.delegate = delegate;
  }

  @Override
  public SmsResult process(PreparedSmsDTO sms) {
    SmsResult result = delegate.process(sms);
    //TODO save v2
    return result;
  }
}
