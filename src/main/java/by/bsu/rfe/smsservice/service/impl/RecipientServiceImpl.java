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
        groupRepository.saveAndFlush(groupEntity);
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
        if (StringUtils.isNotEmpty(query)) {
            List<PersonEntity> persons = personRepository.getPersonsByQuery(query);
            List<GroupEntity> groups = groupRepository.getGroupsByQuery(query);

            List<RecipientDTO> recipients = new ArrayList<>(persons.size() + groups.size());

            for (PersonEntity personEntity : persons) {
                RecipientDTO recipientDTO = new RecipientDTO();
                recipientDTO.setId(personEntity.getId());
                recipientDTO.setName(personEntity.getFirstName() + " " + personEntity.getLastName());
                recipientDTO.setRecipientType(RecipientType.PERSON);
                recipients.add(recipientDTO);
            }

            for (GroupEntity groupEntity : groups) {
                RecipientDTO recipientDTO = new RecipientDTO();
                recipientDTO.setId(groupEntity.getId());
                recipientDTO.setName(groupEntity.getName());
                recipientDTO.setRecipientType(RecipientType.GROUP);
                recipients.add(recipientDTO);
            }

            return recipients;
        } else {
            return getAllRecpients();
        }
    }

    @Override
    public void removePerson(Integer personId) {
        personRepository.delete(personId);
    }

    @Override
    public GroupEntity getGroup(Integer groupId) {
        return groupRepository.findOne(groupId);
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
    public PageResponseDTO<PersonDTO> getPersons(int skip, int offset, String sortField, String sortDirection) {
        Pageable pageable = getPage(skip, offset, sortField, sortDirection);
        Page<PersonEntity> personEntitiesPage = personRepository.findAll(pageable);
        return new PageResponseDTO<>(DozerUtil.mapList(mapper, personEntitiesPage.getContent(), PersonDTO.class), personRepository.count());
    }

    @Override
    public PageResponseDTO<GroupDTO> getGroups(int skip, int offset, String sortField, String sortDirection) {
        Pageable pageable = getPage(skip, offset, sortField, sortDirection);
        Page<GroupEntity> groupEntitiesPage = groupRepository.findAll(pageable);
        return new PageResponseDTO<>(DozerUtil.mapList(mapper, groupEntitiesPage.getContent(), GroupDTO.class), groupRepository.count());
    }

    private Pageable getPage(int skip, int offset, String sortField, String sortDirection) {
        Sort sort = new Sort(new Sort.Order(Sort.Direction.fromString(sortDirection), sortField));
        return new ChunkRequest(skip, offset, sort);
    }
}
