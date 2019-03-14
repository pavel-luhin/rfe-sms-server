package by.bsu.rfe.smsservice.service.impl;

import static by.bsu.rfe.smsservice.common.constants.GeneralConstants.EXAMPLE_EMAIL_POSTFIX;
import static by.bsu.rfe.smsservice.common.constants.GeneralConstants.GENERATED_GROUP_NAME_PREFIX;
import static by.bsu.rfe.smsservice.common.constants.GeneralConstants.GENERATED_NAME;
import static by.bsu.rfe.smsservice.util.DozerUtil.mapList;
import static by.bsu.rfe.smsservice.util.PageUtil.createPage;

import by.bsu.rfe.smsservice.common.dto.GroupDTO;
import by.bsu.rfe.smsservice.common.dto.PersonDTO;
import by.bsu.rfe.smsservice.common.dto.page.PageRequestDTO;
import by.bsu.rfe.smsservice.common.dto.page.PageResponseDTO;
import by.bsu.rfe.smsservice.common.entity.GroupEntity;
import by.bsu.rfe.smsservice.common.entity.PersonEntity;
import by.bsu.rfe.smsservice.repository.GroupRepository;
import by.bsu.rfe.smsservice.repository.PersonRepository;
import by.bsu.rfe.smsservice.service.GroupService;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DefaultGroupService implements GroupService {

  private final GroupRepository groupRepository;
  private final PersonRepository personRepository;
  private final Mapper mapper;

  public DefaultGroupService(GroupRepository groupRepository,
      PersonRepository personRepository, Mapper mapper) {
    this.groupRepository = groupRepository;
    this.personRepository = personRepository;
    this.mapper = mapper;
  }

  @Override
  public void addGroup(GroupDTO groupDTO) {
    GroupEntity groupEntity = mapper.map(groupDTO, GroupEntity.class);
    groupEntity.setPersons(
        personRepository.findAll(
            groupDTO.getPersons().stream().map(PersonDTO::getId).collect(Collectors.toSet())
        )
    );

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
  public GroupDTO getGroup(Integer groupId) {
    return mapper.map(groupRepository.findOne(groupId), GroupDTO.class);
  }

  @Override
  public GroupEntity getGroupByName(String groupName) {
    return groupRepository.getGroupsByQuery(groupName).get(0);
  }

  @Override
  public PageResponseDTO<GroupDTO> getGroups(PageRequestDTO pageRequestDTO, String query) {
    Pageable pageable = createPage(pageRequestDTO);
    Page<GroupEntity> entities = null;

    if (StringUtils.isEmpty(query)) {
      entities = groupRepository.findAll(pageable);
    } else {
      entities = groupRepository.findPageByQuery(query, pageable);
    }

    return new PageResponseDTO<>(
        mapList(mapper, entities.getContent(), GroupDTO.class),
        entities.getTotalElements());  }

  @Override
  public GroupEntity createGroupFromNumbers(List<String> numbers) {
    GroupEntity groupEntity = new GroupEntity();
    groupEntity.setName(GENERATED_GROUP_NAME_PREFIX + System.currentTimeMillis());
    groupEntity.setTemporary(true);

    numbers.forEach(number -> {
      PersonEntity personEntity = new PersonEntity();
      personEntity.setFirstName(GENERATED_NAME);
      personEntity.setLastName(GENERATED_NAME);
      personEntity.setEmail(number + EXAMPLE_EMAIL_POSTFIX);
      personEntity.setPhoneNumber(number);
      personEntity.setTemporary(true);
      personRepository.saveAndFlush(personEntity);
      groupEntity.getPersons().add(personEntity);
    });

    groupRepository.saveAndFlush(groupEntity);
    return groupEntity;  }

  @Override
  public List<GroupEntity> findTemporaryGroups() {
    return groupRepository.findTemporaryGroups();
  }
}
