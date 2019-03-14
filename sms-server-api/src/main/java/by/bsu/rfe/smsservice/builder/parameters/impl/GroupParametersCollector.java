package by.bsu.rfe.smsservice.builder.parameters.impl;

import static by.bsu.rfe.smsservice.common.enums.SmsParams.GROUP_NAME;

import by.bsu.rfe.smsservice.builder.parameters.ParametersCollector;
import by.bsu.rfe.smsservice.common.dto.RecipientDTO;
import by.bsu.rfe.smsservice.common.entity.GroupEntity;
import by.bsu.rfe.smsservice.service.GroupService;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class GroupParametersCollector extends ParametersCollector {

  private final GroupService groupService;

  public GroupParametersCollector(GroupService groupService) {
    this.groupService = groupService;
  }

  @Override
  protected void collectSpecificParameters(RecipientDTO recipientDTO,
      Map<String, String> parameters) {
    GroupEntity groupEntity = groupService.getGroupByName(recipientDTO.getName());

    parameters.put(GROUP_NAME.getKey(), groupEntity.getName());
  }
}
