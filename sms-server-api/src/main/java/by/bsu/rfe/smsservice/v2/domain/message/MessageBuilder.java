package by.bsu.rfe.smsservice.v2.domain.message;

import by.bsu.rfe.smsservice.v2.domain.recipient.Recipient;
import by.bsu.rfe.smsservice.v2.domain.sms.Message;
import java.util.List;

public interface MessageBuilder {

  List<Message> build(List<Recipient> recipients, String template);
}
