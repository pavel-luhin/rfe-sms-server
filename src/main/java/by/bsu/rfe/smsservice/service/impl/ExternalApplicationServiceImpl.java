package by.bsu.rfe.smsservice.service.impl;

import by.bsu.rfe.smsservice.common.dto.ExternalApplicationDTO;
import by.bsu.rfe.smsservice.common.entity.ExternalApplicationEntity;
import by.bsu.rfe.smsservice.repository.ExternalApplicationRepository;
import by.bsu.rfe.smsservice.service.ExternalApplicationService;
import by.bsu.rfe.smsservice.util.DozerUtil;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Created by pluhin on 9/3/16.
 */
@Service
public class ExternalApplicationServiceImpl implements ExternalApplicationService {

    @Autowired
    private ExternalApplicationRepository externalApplicationRepository;

    @Autowired
    private Mapper mapper;

    @Override
    public List<ExternalApplicationDTO> getAllExternalApplications() {
        return DozerUtil.mapList(mapper, externalApplicationRepository.findAll(), ExternalApplicationDTO.class);
    }

    @Override
    public void createExternalApplication(ExternalApplicationDTO externalApplicationDTO) {
        ExternalApplicationEntity externalApplicationEntity = new ExternalApplicationEntity();
        externalApplicationEntity.setApplicationName(externalApplicationDTO.getApplicationName());

        String authenticationToken = UUID.randomUUID().toString().replace("-", "");
        externalApplicationEntity.setAuthenticationToken(authenticationToken);
        externalApplicationRepository.saveAndFlush(externalApplicationEntity);
    }

    @Override
    public void removeExternalApplication(Integer id) {
        externalApplicationRepository.delete(id);
    }
}