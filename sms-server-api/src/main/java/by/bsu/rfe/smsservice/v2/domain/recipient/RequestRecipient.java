package by.bsu.rfe.smsservice.v2.domain.recipient;

import java.util.Map;

public final class RequestRecipient {

  private final String name;
  private final RecipientType recipientType;
  private final Map<String, String> parameters;

  public RequestRecipient(String name, RecipientType recipientType,
      Map<String, String> parameters) {
    this.name = name;
    this.recipientType = recipientType;
    this.parameters = parameters;
  }

  public String getName() {
    return name;
  }

  public RecipientType getRecipientType() {
    return recipientType;
  }

  public Map<String, String> getParameters() {
    return parameters;
  }
}
