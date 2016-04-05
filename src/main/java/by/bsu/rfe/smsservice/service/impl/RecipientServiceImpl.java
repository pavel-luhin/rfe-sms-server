package by.bsu.rfe.smsservice.service.impl;

import by.bsu.rfe.smsservice.common.entity.GroupEntity;
import by.bsu.rfe.smsservice.common.entity.PersonEntity;
import by.bsu.rfe.smsservice.repository.GroupRepository;
import by.bsu.rfe.smsservice.repository.PersonRepository;
import by.bsu.rfe.smsservice.service.RecipientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
//        groupEntity.setName(groupName);
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
    public List<PersonEntity> getAllPersons() {
        return personRepository.findAll();
    }

    @Override
    public GroupEntity getGroup(Integer groupId) {
        return groupRepository.findOne(groupId);
    }
}
