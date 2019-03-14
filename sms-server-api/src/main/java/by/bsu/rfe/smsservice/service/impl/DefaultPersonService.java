package by.bsu.rfe.smsservice.service.impl;

import static by.bsu.rfe.smsservice.util.DozerUtil.mapList;
import static by.bsu.rfe.smsservice.util.PageUtil.createPage;

import by.bsu.rfe.smsservice.common.dto.PersonDTO;
import by.bsu.rfe.smsservice.common.dto.page.PageRequestDTO;
import by.bsu.rfe.smsservice.common.dto.page.PageResponseDTO;
import by.bsu.rfe.smsservice.common.entity.GroupEntity;
import by.bsu.rfe.smsservice.common.entity.PersonEntity;
import by.bsu.rfe.smsservice.repository.GroupRepository;
import by.bsu.rfe.smsservice.repository.PersonRepository;
import by.bsu.rfe.smsservice.service.PersonService;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DefaultPersonService implements PersonService {

  private final PersonRepository personRepository;
  private final GroupRepository groupRepository;
  private final Mapper mapper;

  public DefaultPersonService(PersonRepository personRepository,
      GroupRepository groupRepository, Mapper mapper) {
    this.personRepository = personRepository;
    this.groupRepository = groupRepository;
    this.mapper = mapper;
  }

  @Override
  public void addPerson(PersonDTO personDTO) {
    PersonEntity personEntity = mapper.map(personDTO, PersonEntity.class);
    personRepository.save(personEntity);
  }

  @Override
  public void assignPersonToGroup(Integer personId, Integer groupId) {
    GroupEntity groupEntity = groupRepository.findOne(groupId);
    PersonEntity personEntity = personRepository.findOne(personId);

    groupEntity.getPersons().add(personEntity);
    groupRepository.saveAndFlush(groupEntity);
  }

  @Override
  public void removePerson(Integer personId) {
    personRepository.delete(personId);
  }

  @Override
  public PersonEntity getPerson(String[] name) {
    return personRepository.getPersonByFirstNameAndLastName(name[0], name[1]);
  }

  @Override
  public PageResponseDTO<PersonDTO> getPersons(PageRequestDTO pageRequestDTO, String query) {
    Pageable pageable = createPage(pageRequestDTO);

    if (StringUtils.isEmpty(query)) {
      Page<PersonEntity> personEntitiesPage = personRepository.findAll(pageable);
      return new PageResponseDTO<>(
          mapList(mapper, personEntitiesPage.getContent(), PersonDTO.class),
          personEntitiesPage.getTotalElements());
    }

    Page<PersonEntity> personEntitiesPage = personRepository.findPageByQuery(query, pageable);
    return new PageResponseDTO<>(
        mapList(mapper, personEntitiesPage.getContent(), PersonDTO.class),
        personEntitiesPage.getTotalElements());  }

  @Override
  public List<PersonDTO> getAllPersons() {
    return mapList(mapper, personRepository.findAll(), PersonDTO.class);
  }

  @Override
  public List<PersonDTO> getPersonsWithGroup(Integer groupId) {
    return mapList(mapper, personRepository.getPersonsWithGroup(groupId), PersonDTO.class);
  }

  @Override
  public List<PersonDTO> getPersonsWithoutGroup(Integer groupId) {
    return mapList(mapper, personRepository.getPersonsWithoutGroup(groupId), PersonDTO.class);
  }

  @Override
  public List<PersonEntity> findTemporaryPersons() {
    return personRepository.findTemporaryPersons();
  }
}
