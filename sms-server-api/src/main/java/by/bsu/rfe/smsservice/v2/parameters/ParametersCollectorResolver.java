package by.bsu.rfe.smsservice.v2.parameters;

import by.bsu.rfe.smsservice.v2.domain.SmsType;
import java.util.HashMap;
import java.util.Map;

public final class ParametersCollectorResolver {

  private Map<SmsType, ParametersCollector> dictionary = new HashMap<>();

  public ParametersCollectorResolver(ParametersCollector parametersCollector) {
    dictionary.put(SmsType.CUSTOM, parametersCollector);
  }

  public final ParametersCollector resolve(SmsType smsType) {
    return dictionary.get(smsType);
  }
}
