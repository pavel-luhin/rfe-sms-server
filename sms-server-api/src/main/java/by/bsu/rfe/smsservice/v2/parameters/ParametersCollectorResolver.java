package by.bsu.rfe.smsservice.v2.parameters;

import by.bsu.rfe.smsservice.v2.domain.recipient.RecipientType;
import java.util.HashMap;
import java.util.Map;

public final class ParametersCollectorResolver {

  private Map<RecipientType, ParametersCollector> dictionary = new HashMap<>();

  public ParametersCollectorResolver(ParametersCollector parametersCollector) {
    dictionary.put(RecipientType.PERSON, parametersCollector);
  }

  public final ParametersCollector resolve(RecipientType recipientType) {
    return dictionary.get(recipientType);
  }
}
