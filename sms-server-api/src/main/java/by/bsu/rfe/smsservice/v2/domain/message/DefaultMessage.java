package by.bsu.rfe.smsservice.v2.domain.message;

public class DefaultMessage implements Message {

  private final String recipient;
  private final String message;

  public DefaultMessage(String recipient, String message) {
    this.recipient = recipient;
    this.message = message;
  }

  @Override
  public String getRecipient() {
    return recipient;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
