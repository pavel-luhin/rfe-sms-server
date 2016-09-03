package by.bsu.rfe.smsservice.service.impl;

import by.bsu.rfe.smsservice.cache.credentials.CredentialsCache;
import by.bsu.rfe.smsservice.common.dto.CredentialsDTO;
import by.bsu.rfe.smsservice.common.entity.CredentialsEntity;
import by.bsu.rfe.smsservice.repository.CredentialsRepository;
import by.bsu.rfe.smsservice.security.util.SecurityUtil;
import by.bsu.rfe.smsservice.service.CredentialsService;
import by.bsu.rfe.smsservice.util.DozerUtil;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by pluhin on 3/20/16.
 */
@Service
public class CredentialsServiceImpl implements CredentialsService {

    @Autowired
    private CredentialsRepository credentialsRepository;
    @Autowired
    private CredentialsCache credentialsCache;
    @Autowired
    private Mapper mapper;

    @Override
    public List<CredentialsEntity> getAllCredentials() {
        return credentialsRepository.findAll();
    }

    @Override
    public CredentialsEntity getCredentialsForSmsTypeOrDefault(String smsType) {
        String username = SecurityUtil.getCurrentUsername();
        CredentialsEntity credentialsEntity = credentialsRepository.getCredentials(username, smsType);
        if (credentialsEntity == null) {
            credentialsEntity = credentialsRepository.getDefaultCredentials(username);
        }
        return credentialsEntity;
    }

    @Override
    public CredentialsEntity getCredentialsForSenderName(String senderName) {
        String username = SecurityUtil.getCurrentUsername();
        return credentialsRepository.getCredentialsForSenderName(username, senderName);
    }

    @Override
    public void addNewCredentials(CredentialsEntity credentialsEntity) {
        credentialsRepository.saveAndFlush(credentialsEntity);
    }

    @Override
    public List<CredentialsDTO> getUserCredentials(String username) {
        List<CredentialsEntity> credentialsEntities = null;
        if (credentialsCache.isCacheEnabled()) {
            credentialsEntities = credentialsCache.getAllUserCredentals(username);
        } else {
            credentialsEntities = credentialsRepository.getAllUserCredentials(username);
        }

        return DozerUtil.mapList(mapper, credentialsEntities, CredentialsDTO.class);
    }

    @Override
    public void removeCredentials(Integer id) {
        credentialsRepository.delete(id);
        credentialsCache.reloadCache();
    }
}
