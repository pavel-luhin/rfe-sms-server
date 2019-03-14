package by.bsu.rfe.smsservice.v2.domain.message;

import by.bsu.rfe.smsservice.v2.domain.recipient.Recipient;
import by.bsu.rfe.smsservice.v2.parameters.ParametersCollectorResolver;
import by.bsu.rfe.smsservice.v2.recipient.RecipientFetcherResolver;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DefaultMessageBuilder implements MessageBuilder {

  private final ParametersCollectorResolver parametersCollectorResolver;
  private final RecipientFetcherResolver recipientFetcherResolver;

  public DefaultMessageBuilder(
      ParametersCollectorResolver parametersCollectorResolver,
      RecipientFetcherResolver recipientFetcherResolver) {
    this.parametersCollectorResolver = parametersCollectorResolver;
    this.recipientFetcherResolver = recipientFetcherResolver;
  }

  @Override
  public List<Message> build(List<Recipient> recipients, String template) {
    return recipients
        .stream()
        .map(recipient -> {
          Map<String, String> parameters =
              parametersCollectorResolver.resolve(recipient.type()).collectParameters(recipient);
          return buildMessage(parameters, template, recipient);
        })
        .collect(Collectors.toList());
  }

  private Message buildMessage(Map<String, String> parameters, String template, Recipient recipient) {
    String message = null;
    return new DefaultMessage(recipient, message);
  }
}
