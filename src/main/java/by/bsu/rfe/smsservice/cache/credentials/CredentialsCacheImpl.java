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
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import by.bsu.rfe.smsservice.common.entity.CredentialsEntity;
import by.bsu.rfe.smsservice.common.entity.UserEntity;
import by.bsu.rfe.smsservice.security.util.SecurityUtil;
import by.bsu.rfe.smsservice.service.CredentialsService;

/**
 * Created by pluhin on 6/30/16.
 */
@Component
public class CredentialsCacheImpl implements CredentialsCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(CredentialsCacheImpl.class);

    @Value("${credentials.cache.enabled:false}")
    private Boolean enableCredentialsCache;

    @Autowired
    private CredentialsService credentialsService;

    private Map<String, List<CredentialsEntity>> credentialsByUsername;
    private Map<String, CredentialsEntity> defaultCredentialsByUsername;

    @PostConstruct
    public void startCache() {
        if (enableCredentialsCache) {
            initCache();
        } else {
            LOGGER.info("CREDENTIALS CACHE IS DISABLED");
        }
    }

    private void initCache() {
        LOGGER.info("INITIALIZING CREDENTIALS CACHE");
        int count = 0;
        long startTime = System.currentTimeMillis();

        credentialsByUsername = new ConcurrentHashMap<>();
        defaultCredentialsByUsername = new ConcurrentHashMap<>();

        List<CredentialsEntity> allCredentials = credentialsService.getAllCredentials();

        for (CredentialsEntity credentials : allCredentials) {
            LOGGER.info("CACHE: LOADED CREDENTIALS WITH SENDER NAME: {}", credentials.getSender());
            Set<UserEntity> usersAllowedToUse = credentials.getUsers();
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
    public List<CredentialsEntity> getAllUserCredentals(String username) {
        return credentialsByUsername.get(username);
    }

    @Override
    public List<CredentialsEntity> getAllCurrentUserCredentals() {
        String username = SecurityUtil.getCurrentUsername();
        return credentialsByUsername.get(username);
    }

    @Override
    public CredentialsEntity getDefaultCredentialsForUser(String username) {
        return defaultCredentialsByUsername.get(username);
    }

    @Override
    public CredentialsEntity getDefaultCredentialsForCurrentUser() {
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

    @Override
    public Set<String> getSenderNamesForCurrentUser() {
        List<CredentialsEntity> credentialsEntities = ListUtils.emptyIfNull(getAllCurrentUserCredentals());
        Set<String> senderNames = credentialsEntities.stream().map(credentialsEntity -> credentialsEntity.getSender()).collect(Collectors.toSet());
        return senderNames;
    }

    @Override
    public CredentialsEntity getCredentialsBySenderNameForCurrentUser(String senderName) {
        List<CredentialsEntity> userCredentials = getAllCurrentUserCredentals();

        for (CredentialsEntity credentialsEntity : userCredentials) {
            if (credentialsEntity.getSender().equals(senderName)) {
                return credentialsEntity;
            }
        }

        return null;
    }
}
