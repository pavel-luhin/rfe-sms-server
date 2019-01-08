package by.bsu.rfe.smsservice.builder.parameters;

import by.bsu.rfe.smsservice.builder.parameters.impl.GroupParametersCollector;
import by.bsu.rfe.smsservice.builder.parameters.impl.NumberParametersCollector;
import by.bsu.rfe.smsservice.builder.parameters.impl.PersonParametersCollector;
import by.bsu.rfe.smsservice.common.enums.RecipientType;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParametersCollectorResolver {

  private final Map<RecipientType, ParametersCollector> dictionary = new HashMap<>();

  @Autowired
  public ParametersCollectorResolver(
      NumberParametersCollector numberParametersCollector,
      PersonParametersCollector personParametersCollector,
      GroupParametersCollector groupParametersCollector) {
    dictionary.put(RecipientType.GROUP, groupParametersCollector);
    dictionary.put(RecipientType.PERSON, personParametersCollector);
    dictionary.put(RecipientType.NUMBER, numberParametersCollector);
  }

  public ParametersCollector resolve(RecipientType type) {
    return dictionary.get(type);
  }
}
