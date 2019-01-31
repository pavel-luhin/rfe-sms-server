package by.bsu.rfe.smsservice.v2.sms;

import by.bsu.rfe.smsservice.v2.domain.Sms;
import by.bsu.rfe.smsservice.v2.domain.SmsResult;

public interface SmsService {

  SmsResult process(Sms sms);
}
