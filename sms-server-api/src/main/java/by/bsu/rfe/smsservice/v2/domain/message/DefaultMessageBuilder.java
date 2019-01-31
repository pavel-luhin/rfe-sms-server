package by.bsu.rfe.smsservice.v2.domain.message;

import by.bsu.rfe.smsservice.v2.domain.recipient.Recipient;
import by.bsu.rfe.smsservice.v2.domain.sms.Message;
import by.bsu.rfe.smsservice.v2.parameters.ParametersCollectorResolver;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DefaultMessageBuilder implements MessageBuilder {

  private final ParametersCollectorResolver parametersCollectorResolver;

  public DefaultMessageBuilder(
      ParametersCollectorResolver parametersCollectorResolver) {
    this.parametersCollectorResolver = parametersCollectorResolver;
  }

  @Override
  public List<Message> build(List<Recipient> recipients, String template) {
    return recipients
        .stream()
        .map(recipient -> parametersCollectorResolver.resolve(recipient.type()).collectParameters(recipient))
        .map(parameters -> buildMessage(parameters, template))
        .collect(Collectors.toList());
  }

  private Message buildMessage(Map<String, String> parameters, String template) {
    return null;
  }
}
