package by.bsu.rfe.smsservice.cache.credentials;

import org.apache.commons.collections4.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import by.bsu.rfe.smsservice.cache.SmsServerCache;
import by.bsu.rfe.smsservice.common.entity.CredentialsEntity;
import by.bsu.rfe.smsservice.common.entity.UserEntity;
import by.bsu.rfe.smsservice.security.util.SecurityUtil;
import by.bsu.rfe.smsservice.service.CredentialsService;

/**
 * Created by pluhin on 6/30/16.
 */
@Component
public class CredentialsCacheImpl implements SmsServerCache, CredentialsCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(CredentialsCacheImpl.class);

    @Value("${credentials.cache.enabled:false}")
    private Boolean enableCredentialsCache;

    @Autowired
    private CredentialsService credentialsService;

    private Map<String, List<CredentialsEntity>> credentialsByUsername = new ConcurrentHashMap<>();
    private Map<String, CredentialsEntity> defaultCredentialsByUsername = new ConcurrentHashMap<>();

    @PostConstruct
    public void startCache() {
        if (enableCredentialsCache) {
            initCache();
        } else {
            LOGGER.info("CREDENTIALS CACHE IS DISABLED");
        }
    }

    public void initCache() {
        LOGGER.info("INITIALIZING CREDENTIALS CACHE");
        int count = 0;
        long startTime = System.currentTimeMillis();

        List<CredentialsEntity> allCredentials = credentialsService.getAllCredentials();

        for (CredentialsEntity credentials : allCredentials) {
            LOGGER.info("CACHE: LOADED CREDENTIALS FOR SMSTYPE: {}", credentials.getSmsType());
            List<UserEntity> usersAllowedToUse = credentials.getUsers();
            for (UserEntity userEntity : usersAllowedToUse) {
                putToAllCache(userEntity, credentials);
                putToDefaultCache(userEntity);
            }
            count++;
        }

        LOGGER.info("CREDENTIALS CACHE INITIALIZED IN {} ms", System.currentTimeMillis() - startTime);
        LOGGER.info("TOTAL COUNT: {}", count);
    }

    @Override
    public CredentialsEntity getCredentialsForSMSTypeOrDefault(String smsType) {
        String username = SecurityUtil.getCurrentUsername();
        List<CredentialsEntity> userCredentials = credentialsByUsername.get(username);

        for (CredentialsEntity credentials : ListUtils.emptyIfNull(userCredentials)) {
            if (credentials.getSmsType().equals(smsType)) {
                return credentials;
            }
        }

        return getDefaultCredentialsForUser();
    }

    @Override
    public List<CredentialsEntity> getAllUserCredentals() {
        String username = SecurityUtil.getCurrentUsername();
        return credentialsByUsername.get(username);
    }

    @Override
    public CredentialsEntity getDefaultCredentialsForUser() {
        String username = SecurityUtil.getCurrentUsername();
        return defaultCredentialsByUsername.get(username);
    }

    private void putToAllCache(UserEntity userEntity, CredentialsEntity credentialsEntity) {
        if (credentialsByUsername.containsKey(userEntity.getUsername())) {
            credentialsByUsername.get(userEntity.getUsername()).add(credentialsEntity);
        } else {
            List<CredentialsEntity> credentialsEntities = new ArrayList<>();
            credentialsEntities.add(credentialsEntity);
            credentialsByUsername.put(userEntity.getUsername(), credentialsEntities);
        }
    }

    private void putToDefaultCache(UserEntity userEntity) {
        if (userEntity.getDefaultUserCredentials() != null) {
            defaultCredentialsByUsername.put(userEntity.getUsername(), userEntity.getDefaultUserCredentials());
        }
    }

    @Override
    public void reloadCache() {
        startCache();
    }

    public Boolean isCacheEnabled() {
        return enableCredentialsCache;
    }
}
