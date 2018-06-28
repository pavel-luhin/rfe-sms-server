package by.bsu.rfe.smsservice.service;

import by.bsu.rfe.smsservice.common.dto.ExternalApplicationDTO;
import by.bsu.rfe.smsservice.common.entity.ExternalApplicationEntity;
import java.util.List;

public interface ExternalApplicationService {

  List<ExternalApplicationDTO> getAllExternalApplications();

  void createExternalApplication(ExternalApplicationDTO externalApplicationDTO);

  void removeExternalApplication(Integer id);

  ExternalApplicationEntity getByToken(String token);

  List<ExternalApplicationEntity> getAllApplicationEntities();

  ExternalApplicationEntity getByName(String name);
}
