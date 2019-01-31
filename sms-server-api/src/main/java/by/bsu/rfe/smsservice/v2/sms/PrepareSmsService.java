package by.bsu.rfe.smsservice.v2.sms;

import by.bsu.rfe.smsservice.v2.domain.Sms;
import by.bsu.rfe.smsservice.v2.domain.SmsResult;
import by.bsu.rfe.smsservice.v2.parameters.ParametersCollectorResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrepareSmsService implements SmsService {

  private final SmsService delegate;
  private final ParametersCollectorResolver parametersCollectorResolver;

  @Autowired
  public PrepareSmsService(SmsService delegate,
      ParametersCollectorResolver parametersCollectorResolver) {
    this.delegate = delegate;
    this.parametersCollectorResolver = parametersCollectorResolver;
  }

  @Override
  public SmsResult process(Sms sms) {
    parametersCollectorResolver.resolve(sms.type()).collectParameters(sms);
    return delegate.process(sms);
  }
}
