package by.bsu.rfe.smsservice.v2.domain.sms;

import by.bsu.rfe.smsservice.v2.domain.SmsType;
import by.bsu.rfe.smsservice.v2.domain.recipient.Recipient;
import java.util.List;

public interface Sms {

  List<Message> getMessages();

  List<Recipient> getRecipients();

  SmsType getType();

  String getTemplate();
}
