package by.bsu.rfe.smsservice.v2.sms.prepare;

import by.bsu.rfe.smsservice.v2.domain.SmsResult;
import by.bsu.rfe.smsservice.v2.domain.sms.tmp.sms.BulkSmsRequestDTO;
import by.bsu.rfe.smsservice.v2.sms.SmsService;
import org.springframework.stereotype.Service;

@Service
public class PrepareBulkSmsService implements SmsService<BulkSmsRequestDTO> {

  private final SmsService delegate;

  public PrepareBulkSmsService(SmsService delegate) {
    this.delegate = delegate;
  }

  @Override
  public SmsResult process(BulkSmsRequestDTO sms) {
    return null;
  }
}
