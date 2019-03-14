package by.bsu.rfe.smsservice.v2.domain.message;

import by.bsu.rfe.smsservice.v2.domain.recipient.Recipient;

public interface Message {

  String getRecipient();

  String getMessage();
}
