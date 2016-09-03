package by.bsu.rfe.smsservice.cache.credentials;

import java.util.List;
import java.util.Set;

import by.bsu.rfe.smsservice.cache.SmsServerCache;
import by.bsu.rfe.smsservice.common.entity.CredentialsEntity;

/**
 * Created by pluhin on 6/30/16.
 */
public interface CredentialsCache extends SmsServerCache {
    CredentialsEntity getCredentialsForSMSTypeOrDefault(String smsType);
    List<CredentialsEntity> getAllUserCredentals(String username);
    List<CredentialsEntity> getAllCurrentUserCredentals();
    CredentialsEntity getDefaultCredentialsForUser(String username);
    CredentialsEntity getDefaultCredentialsForCurrentUser();
    Boolean isCacheEnabled();
    Set<String> getSenderNamesForCurrentUser();
    CredentialsEntity getCredentialsBySenderName(String senderName);
}
