package by.bsu.rfe.smsservice.service.impl;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import by.bsu.rfe.smsservice.cache.credentials.CredentialsCache;
import by.bsu.rfe.smsservice.common.dto.CredentialsDTO;
import by.bsu.rfe.smsservice.common.dto.ShareCredentialsDTO;
import by.bsu.rfe.smsservice.common.entity.CredentialsEntity;
import by.bsu.rfe.smsservice.common.entity.UserEntity;
import by.bsu.rfe.smsservice.repository.CredentialsRepository;
import by.bsu.rfe.smsservice.security.util.SecurityUtil;
import by.bsu.rfe.smsservice.service.CredentialsService;
import by.bsu.rfe.smsservice.service.ExternalApplicationService;
import by.bsu.rfe.smsservice.service.UserService;
import by.bsu.rfe.smsservice.util.DozerUtil;

/**
 * Created by pluhin on 3/20/16.
 */
@Service
public class CredentialsServiceImpl implements CredentialsService {

    @Autowired
    private CredentialsRepository credentialsRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ExternalApplicationService externalApplicationService;
    @Autowired
    private CredentialsCache credentialsCache;
    @Autowired
    private Mapper mapper;

    @Override
    public List<CredentialsEntity> getAllCredentials() {
        return credentialsRepository.findAll();
    }

    @Override
    public CredentialsEntity getDefaultCredentialsForCurrentUser() {
        if (credentialsCache.isCacheEnabled()) {
            return credentialsCache.getDefaultCredentialsForCurrentUser();
        } else {
            String username = SecurityUtil.getCurrentUsername();
            UserEntity userEntity = userService.findByUsername(username);

            if (userEntity != null) {
                return userEntity.getDefaultUserCredentials();
            } else {
                return externalApplicationService.getByName(username).getDefaultCredentials();
            }
        }
    }

    @Override
    public CredentialsEntity getCredentialsForSenderName(String senderName) {
        if (credentialsCache.isCacheEnabled()) {
            return credentialsCache.getCredentialsBySenderNameForCurrentUser(senderName);
        } else {
            String username = SecurityUtil.getCurrentUsername();
            return credentialsRepository.getCredentialsForSenderName(username, senderName);
        }
    }

    @Override
    public void saveCredentials(CredentialsEntity credentialsEntity) {
        credentialsRepository.saveAndFlush(credentialsEntity);
        credentialsCache.reloadCache();
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

    @Override
    public void shareCredentials(ShareCredentialsDTO shareCredentialsDTO) {
        UserEntity userEntity = userService.findById(shareCredentialsDTO.getUserId());
        CredentialsEntity credentialsEntity = credentialsRepository.findOne(shareCredentialsDTO.getCredentialsId());

        credentialsEntity.getUsers().add(userEntity);
        credentialsRepository.saveAndFlush(credentialsEntity);
        credentialsCache.reloadCache();
    }

    @Override
    public CredentialsEntity getCredentialsById(Integer id) {
        return credentialsRepository.findOne(id);
    }
}
