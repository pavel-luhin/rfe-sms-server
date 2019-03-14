package by.bsu.rfe.smsservice.builder.parameters.impl;

import static by.bsu.rfe.smsservice.common.enums.SmsParams.EMAIL;
import static by.bsu.rfe.smsservice.common.enums.SmsParams.FIRST_NAME;
import static by.bsu.rfe.smsservice.common.enums.SmsParams.LAST_NAME;
import static by.bsu.rfe.smsservice.common.enums.SmsParams.PHONE_NUMBER;

import by.bsu.rfe.smsservice.builder.parameters.ParametersCollector;
import by.bsu.rfe.smsservice.common.dto.RecipientDTO;
import by.bsu.rfe.smsservice.common.entity.PersonEntity;
import by.bsu.rfe.smsservice.service.PersonService;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class PersonParametersCollector extends ParametersCollector {

  private final PersonService personService;

  public PersonParametersCollector(PersonService personService) {
    this.personService = personService;
  }

  @Override
  protected void collectSpecificParameters(RecipientDTO recipient,
      Map<String, String> parameters) {
    PersonEntity person = personService.getPerson(recipient.getName().split("-"));

    parameters.put(FIRST_NAME.getKey(), person.getFirstName());
    parameters.put(LAST_NAME.getKey(), person.getLastName());
    parameters.put(PHONE_NUMBER.getKey(), person.getPhoneNumber());
    parameters.put(EMAIL.getKey(), person.getEmail());
  }
}
