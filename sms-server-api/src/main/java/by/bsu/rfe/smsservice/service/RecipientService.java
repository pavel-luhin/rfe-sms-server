package by.bsu.rfe.smsservice.service;

import by.bsu.rfe.smsservice.common.dto.GroupDTO;
import by.bsu.rfe.smsservice.common.dto.PersonDTO;
import by.bsu.rfe.smsservice.common.dto.RecipientDTO;
import by.bsu.rfe.smsservice.common.dto.page.PageRequestDTO;
import by.bsu.rfe.smsservice.common.dto.page.PageResponseDTO;
import by.bsu.rfe.smsservice.common.entity.GroupEntity;
import by.bsu.rfe.smsservice.common.entity.PersonEntity;
import java.util.List;

public interface RecipientService {

  List<RecipientDTO> getAllRecpients();

  List<RecipientDTO> getRecipientByQuery(String query);

  List<String> fetchNumbers(RecipientDTO recipient);
}
