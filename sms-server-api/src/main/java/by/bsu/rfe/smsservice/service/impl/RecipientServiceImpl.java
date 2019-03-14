package by.bsu.rfe.smsservice.service.impl;

import by.bsu.rfe.smsservice.common.dto.RecipientDTO;
import by.bsu.rfe.smsservice.common.entity.GroupEntity;
import by.bsu.rfe.smsservice.common.entity.PersonEntity;
import by.bsu.rfe.smsservice.common.enums.RecipientType;
import by.bsu.rfe.smsservice.repository.GroupRepository;
import by.bsu.rfe.smsservice.repository.PersonRepository;
import by.bsu.rfe.smsservice.service.RecipientService;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

@Service
public class RecipientServiceImpl implements RecipientService {

  private final PersonRepository personRepository;
  private final GroupRepository groupRepository;

  public RecipientServiceImpl(PersonRepository personRepository,
      GroupRepository groupRepository) {
    this.personRepository = personRepository;
    this.groupRepository = groupRepository;
  }

  @Override
  public List<RecipientDTO> getAllRecpients() {
    List<PersonEntity> persons = personRepository.findAll();
    List<GroupEntity> groups = groupRepository.findAll();
    return mapRecipientListsToRecipientDTOs(persons, groups);
  }

  @Override
  public List<RecipientDTO> getRecipientByQuery(String query) {
    if (StringUtils.isNotEmpty(query)) {
      List<PersonEntity> persons = personRepository.getPersonsByQuery(query);
      List<GroupEntity> groups = groupRepository.getGroupsByQuery(query);
      return mapRecipientListsToRecipientDTOs(persons, groups);
    } else {
      return getAllRecpients();
    }
  }

  @Override
  public List<String> fetchNumbers(RecipientDTO recipient) {
    List<String> finalRecipients = new ArrayList<>();

    switch (recipient.getRecipientType()) {
      case NUMBER:
        finalRecipients.add(recipient.getName());
        break;
      case GROUP:
        GroupEntity groupEntity = groupRepository.getGroupsByQuery(recipient.getName()).get(0);
        finalRecipients.add(getAllRecipientsFromGroup(groupEntity));
        break;
      case PERSON:
        String[] recipientName = recipient.getName().split("-");
        PersonEntity personEntity = personRepository.getPersonByFirstNameAndLastName(
            recipientName[0],
            recipientName[1]
        );
        finalRecipients.add(personEntity.getPhoneNumber());
        break;
      default:
        throw new NotImplementedException(
            "Not supported recipient type: " + recipient.getRecipientType());
    }

    return finalRecipients;
  }

  private String getAllRecipientsFromGroup(GroupEntity groupEntity) {
    StringBuilder stringBuilder = new StringBuilder();
    for (PersonEntity person : groupEntity.getPersons()) {
      stringBuilder.append(person.getPhoneNumber());
      stringBuilder.append(",");
    }
    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
    return stringBuilder.toString();
  }

  private List<RecipientDTO> mapRecipientListsToRecipientDTOs(List<PersonEntity> personEntities,
      List<GroupEntity> groupEntities) {
    List<RecipientDTO> recipients = new ArrayList<>(personEntities.size() + groupEntities.size());

    for (PersonEntity personEntity : personEntities) {
      RecipientDTO recipientDTO = new RecipientDTO();
      recipientDTO.setId(personEntity.getId());
      recipientDTO.setName(personEntity.getFirstName() + " " + personEntity.getLastName());
      recipientDTO.setRecipientType(RecipientType.PERSON);
      recipients.add(recipientDTO);
    }

    for (GroupEntity groupEntity : groupEntities) {
      RecipientDTO recipientDTO = new RecipientDTO();
      recipientDTO.setId(groupEntity.getId());
      recipientDTO.setName(groupEntity.getName());
      recipientDTO.setRecipientType(RecipientType.GROUP);
      recipients.add(recipientDTO);
    }

    return recipients;
  }
}
