package by.bsu.rfe.smsservice.service.impl;

import by.bsu.rfe.smsservice.common.dto.RecipientDTO;
import by.bsu.rfe.smsservice.common.entity.GroupEntity;
import by.bsu.rfe.smsservice.common.entity.PersonEntity;
import by.bsu.rfe.smsservice.common.enums.RecipientType;
import by.bsu.rfe.smsservice.repository.GroupRepository;
import by.bsu.rfe.smsservice.repository.PersonRepository;
import by.bsu.rfe.smsservice.service.RecipientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pluhin on 3/20/16.
 */
@Service
public class RecipientServiceImpl implements RecipientService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private GroupRepository groupRepository;

    @Override
    public void addGroup(String groupName) {
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setName(groupName);
        groupRepository.saveAndFlush(groupEntity);
    }

    @Override
    public void addPersons(List<PersonEntity> personEntities) {
        personRepository.save(personEntities);
    }

    @Override
    public void assignPersonToGroup(Integer personId, Integer groupId) {
        PersonEntity personEntity = personRepository.findOne(personId);
        GroupEntity groupEntity = groupRepository.findOne(groupId);
        personEntity.getGroups().add(groupEntity);
        personRepository.saveAndFlush(personEntity);
    }

    @Override
    public List<RecipientDTO> getAllRecpients() {
        List<PersonEntity> persons = personRepository.findAll();
        List<GroupEntity> groups = groupRepository.findAll();

        List<RecipientDTO> recipients = new ArrayList<>(persons.size() + groups.size());

        for (PersonEntity personEntity : persons) {
            RecipientDTO recipientDTO = new RecipientDTO();
            recipientDTO.setName(personEntity.getFirstName());
            recipientDTO.setRecipientType(RecipientType.PERSON);
            recipients.add(recipientDTO);
        }

        for (GroupEntity groupEntity : groups) {
            RecipientDTO recipientDTO = new RecipientDTO();
            recipientDTO.setName(groupEntity.getName());
            recipientDTO.setRecipientType(RecipientType.GROUP);
            recipients.add(recipientDTO);
        }

        return recipients;
    }

    @Override
    public List<RecipientDTO> getRecipientByQuery(String query) {
        List<PersonEntity> persons = personRepository.getPersonsByQuery(query);
        List<GroupEntity> groups = groupRepository.getGroupsByQuery(query);

        List<RecipientDTO> recipients = new ArrayList<>(persons.size() + groups.size());

        for (PersonEntity personEntity : persons) {
            RecipientDTO recipientDTO = new RecipientDTO();
            recipientDTO.setName(personEntity.getFirstName() + " " + personEntity.getLastName());
            recipientDTO.setRecipientType(RecipientType.PERSON);
            recipients.add(recipientDTO);
        }

        for (GroupEntity groupEntity : groups) {
            RecipientDTO recipientDTO = new RecipientDTO();
            recipientDTO.setName(groupEntity.getName());
            recipientDTO.setRecipientType(RecipientType.GROUP);
            recipients.add(recipientDTO);
        }

        return recipients;
    }

    @Override
    public GroupEntity getGroup(Integer groupId) {
        return groupRepository.findOne(groupId);
    }

    @Override
    public PersonEntity getPerson(String[] name) {
        return personRepository.getPersonByFirstNameAndLastName(name[0], name[1]);
    }
}
