package by.bsu.rfe.smsservice.builder.parameters.impl;

import static by.bsu.rfe.smsservice.common.enums.SmsParams.GROUP_NAME;

import by.bsu.rfe.smsservice.builder.parameters.ParametersCollector;
import by.bsu.rfe.smsservice.common.entity.GroupEntity;
import by.bsu.rfe.smsservice.common.enums.RecipientType;
import by.bsu.rfe.smsservice.common.enums.SmsParams;
import by.bsu.rfe.smsservice.service.RecipientService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GroupParametersCollector extends ParametersCollector {

  @Autowired
  private RecipientService recipientService;

  @Override
  protected void collectSpecificParameters(Map.Entry<String, RecipientType> recipient,
      Map<String, String> parameters) {
    GroupEntity groupEntity = recipientService.getGroupByName(recipient.getKey());

    parameters.put(GROUP_NAME.getKey(), groupEntity.getName());
  }
}
