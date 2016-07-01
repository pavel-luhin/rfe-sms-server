package by.bsu.rfe.smsservice.cache.credentials;

import java.util.List;

import by.bsu.rfe.smsservice.common.entity.CredentialsEntity;

/**
 * Created by pluhin on 6/30/16.
 */
public interface CredentialsCache {
    CredentialsEntity getCredentialsForSMSTypeOrDefault(String smsType);
    List<CredentialsEntity> getAllUserCredentals();
    CredentialsEntity getDefaultCredentialsForUser();
    Boolean isCacheEnabled();
}
