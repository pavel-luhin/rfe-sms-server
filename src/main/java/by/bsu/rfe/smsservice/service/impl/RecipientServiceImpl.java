package by.bsu.rfe.smsservice.service.impl;

import by.bsu.rfe.smsservice.common.dto.GroupDTO;
import by.bsu.rfe.smsservice.common.dto.PageResponseDTO;
import by.bsu.rfe.smsservice.common.dto.PersonDTO;
import by.bsu.rfe.smsservice.common.dto.RecipientDTO;
import by.bsu.rfe.smsservice.common.entity.GroupEntity;
import by.bsu.rfe.smsservice.common.entity.PersonEntity;
import by.bsu.rfe.smsservice.common.enums.RecipientType;
import by.bsu.rfe.smsservice.common.pagination.ChunkRequest;
import by.bsu.rfe.smsservice.repository.GroupRepository;
import by.bsu.rfe.smsservice.repository.PersonRepository;
import by.bsu.rfe.smsservice.service.RecipientService;
import by.bsu.rfe.smsservice.util.DozerUtil;

import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    @Autowired
    private Mapper mapper;

    @Override
    public void addGroup(GroupDTO groupDTO) {
        GroupEntity groupEntity = mapper.map(groupDTO, GroupEntity.class);

        if (groupEntity.isNew()) {
            groupRepository.saveAndFlush(groupEntity);
            return;
        }

        GroupEntity oldGroupEntity = groupRepository.findOne(groupEntity.getId());
        oldGroupEntity.setName(groupEntity.getName());
        oldGroupEntity.setPersons(groupEntity.getPersons());

        groupRepository.saveAndFlush(oldGroupEntity);
    }

    @Override
    public void removeGroup(Integer groupId) {
        groupRepository.delete(groupId);
    }

    @Override
    public void addPersons(List<PersonEntity> personEntities) {
        personRepository.save(personEntities);
    }

    @Override
    public void assignPersonToGroup(Integer personId, Integer groupId) {
        GroupEntity groupEntity = groupRepository.findOne(groupId);
        PersonEntity personEntity = personRepository.findOne(personId);

        groupEntity.getPersons().add(personEntity);
        groupRepository.saveAndFlush(groupEntity);
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
    public void removePerson(Integer personId) {
        personRepository.delete(personId);
    }

    @Override
    public GroupDTO getGroup(Integer groupId) {
        return mapper.map(groupRepository.findOne(groupId), GroupDTO.class);
    }

    @Override
    public PersonEntity getPerson(String[] name) {
        return personRepository.getPersonByFirstNameAndLastName(name[0], name[1]);
    }

    @Override
    public GroupEntity getGroupByName(String groupName) {
        return groupRepository.getGroupsByQuery(groupName).get(0);
    }

    @Override
    public PageResponseDTO<PersonDTO> getPersons(int skip, int offset, String sortField, String sortDirection, String query) {
        Pageable pageable = getPage(skip, offset, sortField, sortDirection);

        if (StringUtils.isEmpty(query)) {
            Page<PersonEntity> personEntitiesPage = personRepository.findAll(pageable);
            return new PageResponseDTO<>(DozerUtil.mapList(mapper, personEntitiesPage.getContent(), PersonDTO.class), personEntitiesPage.getTotalElements());
        }

        Page<PersonEntity> personEntitiesPage = personRepository.findPageByQuery(query, pageable);
        return new PageResponseDTO<>(DozerUtil.mapList(mapper, personEntitiesPage.getContent(), PersonDTO.class), personEntitiesPage.getTotalElements());
    }

    @Override
    public List<PersonDTO> getAllPersons() {
        return DozerUtil.mapList(mapper, personRepository.findAll(), PersonDTO.class);
    }

    @Override
    public List<PersonDTO> getPersonsWithGroup(Integer groupId) {
        return DozerUtil.mapList(mapper, personRepository.getPersonsWithGroup(groupId), PersonDTO.class);
    }

    @Override
    public List<PersonDTO> getPersonsWithoutGroup(Integer groupId) {
        return DozerUtil.mapList(mapper, personRepository.getPersonsWithoutGroup(groupId), PersonDTO.class);
    }

    @Override
    public PageResponseDTO<GroupDTO> getGroups(int skip, int offset, String sortField, String sortDirection, String query) {
        Pageable pageable = getPage(skip, offset, sortField, sortDirection);

        if (StringUtils.isEmpty(query)) {
            Page<GroupEntity> groupEntitiesPage = groupRepository.findAll(pageable);
            return new PageResponseDTO<>(DozerUtil.mapList(mapper, groupEntitiesPage.getContent(), GroupDTO.class), groupEntitiesPage.getTotalElements());
        }

        Page<GroupEntity> groupEntityPage = groupRepository.findPageByQuery(query, pageable);
        return new PageResponseDTO<>(DozerUtil.mapList(mapper, groupEntityPage.getContent(), GroupDTO.class), groupEntityPage.getTotalElements());
    }

    private List<RecipientDTO> mapRecipientListsToRecipientDTOs(List<PersonEntity> personEntities, List<GroupEntity> groupEntities) {
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

    private Pageable getPage(int skip, int offset, String sortField, String sortDirection) {
        Sort sort = new Sort(new Sort.Order(Sort.Direction.fromString(sortDirection), sortField));
        return new ChunkRequest(skip, offset, sort);
    }
}
