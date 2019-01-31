package by.bsu.rfe.smsservice.v2.parameters;

import by.bsu.rfe.smsservice.v2.domain.recipient.Recipient;
import java.util.Map;

public interface ParametersCollector {

  Map<String, String> collectParameters(Recipient recipient);
}
