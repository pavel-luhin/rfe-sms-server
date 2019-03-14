package by.bsu.rfe.smsservice.service;

import by.bsu.rfe.smsservice.common.dto.PersonDTO;
import by.bsu.rfe.smsservice.common.dto.page.PageRequestDTO;
import by.bsu.rfe.smsservice.common.dto.page.PageResponseDTO;
import by.bsu.rfe.smsservice.common.entity.PersonEntity;
import java.util.List;

public interface PersonService {

  void addPerson(PersonDTO personDTO);

  void assignPersonToGroup(Integer personId, Integer groupId);

  void removePerson(Integer personId);

  PersonEntity getPerson(String[] name);

  PageResponseDTO<PersonDTO> getPersons(PageRequestDTO pageRequestDTO, String query);

  List<PersonDTO> getAllPersons();

  List<PersonDTO> getPersonsWithGroup(Integer groupId);

  List<PersonDTO> getPersonsWithoutGroup(Integer groupId);

  List<PersonEntity> findTemporaryPersons();
}
