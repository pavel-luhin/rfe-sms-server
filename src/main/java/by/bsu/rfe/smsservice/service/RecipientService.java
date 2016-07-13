package by.bsu.rfe.smsservice.service;

import by.bsu.rfe.smsservice.common.dto.RecipientDTO;
import by.bsu.rfe.smsservice.common.entity.GroupEntity;
import by.bsu.rfe.smsservice.common.entity.PersonEntity;

import java.util.List;

/**
 * Created by pluhin on 3/20/16.
 */
public interface RecipientService {
    void addGroup(String groupName);
    void addPersons(List<PersonEntity> personEntities);
    void assignPersonToGroup(Integer personId, Integer groupId);
    List<RecipientDTO> getAllRecpients();
    GroupEntity getGroup(Integer groupId);
    PersonEntity getPerson(String name);

}
