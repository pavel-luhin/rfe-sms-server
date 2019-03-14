package by.bsu.rfe.smsservice.v2.domain.message;

import by.bsu.rfe.smsservice.v2.domain.recipient.Recipient;

public class DefaultMessage implements Message {

  private final Recipient recipient;
  private final String message;

  public DefaultMessage(Recipient recipient, String message) {
    this.recipient = recipient;
    this.message = message;
  }

  @Override
  public Recipient getRecipient() {
    return recipient;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
