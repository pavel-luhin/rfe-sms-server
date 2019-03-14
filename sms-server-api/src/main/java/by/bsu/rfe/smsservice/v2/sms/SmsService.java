package by.bsu.rfe.smsservice.v2.sms;

import by.bsu.rfe.smsservice.v2.domain.SmsResult;
import by.bsu.rfe.smsservice.v2.domain.sms.tmp.sms.BaseSmsRequestDTO;

public interface SmsService<T extends BaseSmsRequestDTO> {

  SmsResult process(T sms);
}
