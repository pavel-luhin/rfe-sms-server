package by.bsu.rfe.smsservice.v2.domain.recipient;

public class DefaultRecipient implements Recipient {

  private final String name;
  private final RecipientType recipientType;

  public DefaultRecipient(String name, RecipientType recipientType) {
    this.name = name;
    this.recipientType = recipientType;
  }

  @Override
  public String getName() {
    return null;
  }

  @Override
  public RecipientType type() {
    return null;
  }
}
