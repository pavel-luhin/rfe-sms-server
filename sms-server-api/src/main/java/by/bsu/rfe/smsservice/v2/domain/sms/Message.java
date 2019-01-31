package by.bsu.rfe.smsservice.v2.domain.sms;

import by.bsu.rfe.smsservice.v2.domain.recipient.Recipient;

public interface Message {

  Recipient getRecipient();

  String getMessage();
}
