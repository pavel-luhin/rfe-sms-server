package by.bsu.rfe.smsservice.v2.parameters;

import static by.bsu.rfe.smsservice.common.enums.SmsParams.EMAIL;
import static by.bsu.rfe.smsservice.common.enums.SmsParams.FIRST_NAME;
import static by.bsu.rfe.smsservice.common.enums.SmsParams.LAST_NAME;
import static by.bsu.rfe.smsservice.common.enums.SmsParams.PHONE_NUMBER;

import by.bsu.rfe.smsservice.common.entity.PersonEntity;
import by.bsu.rfe.smsservice.service.RecipientService;
import by.bsu.rfe.smsservice.v2.domain.recipient.Recipient;
import java.util.HashMap;
import java.util.Map;

public class PersonParametersCollector implements ParametersCollector {

  private final RecipientService recipientService;

  public PersonParametersCollector(RecipientService recipientService) {
    this.recipientService = recipientService;
  }

  @Override
  public Map<String, String> collectParameters(Recipient recipient) {
    PersonEntity person = recipientService.getPerson(recipient.getName().split("-"));
    Map<String, String> parameters = new HashMap<>();

    parameters.put(FIRST_NAME.name(), person.getFirstName());
    parameters.put(LAST_NAME.name(), person.getLastName());
    parameters.put(PHONE_NUMBER.name(), person.getPhoneNumber());
    parameters.put(EMAIL.name(), person.getEmail());

    return parameters;
  }
}
