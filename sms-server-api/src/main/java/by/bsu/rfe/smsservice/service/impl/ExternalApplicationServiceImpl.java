package by.bsu.rfe.smsservice.service.impl;

import by.bsu.rfe.smsservice.cache.credentials.CredentialsCache;
import by.bsu.rfe.smsservice.common.dto.ExternalApplicationDTO;
import by.bsu.rfe.smsservice.common.entity.CredentialsEntity;
import by.bsu.rfe.smsservice.common.entity.ExternalApplicationEntity;
import by.bsu.rfe.smsservice.repository.ExternalApplicationRepository;
import by.bsu.rfe.smsservice.service.ExternalApplicationService;
import by.bsu.rfe.smsservice.util.DozerUtil;
import java.util.List;
import java.util.UUID;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExternalApplicationServiceImpl implements ExternalApplicationService {

  @Autowired
  private ExternalApplicationRepository externalApplicationRepository;

  @Autowired
  private CredentialsCache credentialsCache;

  @Autowired
  private Mapper mapper;

  @Override
  public List<ExternalApplicationDTO> getAllExternalApplications() {
    return DozerUtil
        .mapList(mapper, externalApplicationRepository.findAll(), ExternalApplicationDTO.class);
  }

  @Override
  public void createExternalApplication(ExternalApplicationDTO externalApplicationDTO) {
    ExternalApplicationEntity externalApplicationEntity = new ExternalApplicationEntity();
    externalApplicationEntity.setApplicationName(externalApplicationDTO.getApplicationName());

    String authenticationToken = UUID.randomUUID().toString().replace("-", "");
    externalApplicationEntity.setAuthenticationToken(authenticationToken);
    CredentialsEntity credentialsEntity = credentialsCache
        .getCredentialsBySenderNameForCurrentUser(
            externalApplicationDTO.getCredentialsSenderName());
    externalApplicationEntity.setDefaultCredentials(credentialsEntity);

    externalApplicationRepository.saveAndFlush(externalApplicationEntity);
    credentialsCache.reloadCache();
  }

  @Override
  public void removeExternalApplication(Integer id) {
    externalApplicationRepository.delete(id);
  }

  @Override
  public ExternalApplicationEntity getByToken(String token) {
    return externalApplicationRepository.getByToken(token);
  }

  @Override
  public List<ExternalApplicationEntity> getAllApplicationEntities() {
    return externalApplicationRepository.findAll();
  }

  @Override
  public ExternalApplicationEntity getByName(String name) {
    return externalApplicationRepository.getByName(name);
  }
}
