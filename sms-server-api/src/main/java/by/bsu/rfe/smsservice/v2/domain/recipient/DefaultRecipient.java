package by.bsu.rfe.smsservice.v2.domain.recipient;

import java.util.Map;

public final class DefaultRecipient implements Recipient {

  private final String name;
  private final RecipientType recipientType;
  private final Map<String, String> parameters;

  public DefaultRecipient(String name, RecipientType recipientType,
      Map<String, String> parameters) {
    this.name = name;
    this.recipientType = recipientType;
    this.parameters = parameters;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public RecipientType type() {
    return recipientType;
  }

  public Map<String, String> getParameters() {
    return parameters;
  }
}