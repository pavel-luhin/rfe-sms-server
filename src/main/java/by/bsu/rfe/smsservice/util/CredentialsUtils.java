package by.bsu.rfe.smsservice.util;

import by.bsu.rfe.smsservice.cache.credentials.CredentialsCache;
import by.bsu.rfe.smsservice.common.SpringContextHolder;
import by.bsu.rfe.smsservice.common.entity.CredentialsEntity;
import by.bsu.rfe.smsservice.service.CredentialsService;

/**
 * Created by pluhin on 7/10/16.
 */
public class CredentialsUtils {

    public static CredentialsEntity getUserCredentialsForSMSType(String smsType) {
        CredentialsCache credentialsCache = SpringContextHolder.getBean(CredentialsCache.class);
        if (credentialsCache.isCacheEnabled()) {
            return credentialsCache.getCredentialsForSMSTypeOrDefault(smsType);
        } else {
            CredentialsService credentialsService = SpringContextHolder.getBean(CredentialsService.class);
            return credentialsService.getCredentialsForSmsTypeOrDefault(smsType);
        }
    }

    public static CredentialsEntity getCredentialsForSenderName(String senderName) {
        CredentialsCache credentialsCache = SpringContextHolder.getBean(CredentialsCache.class);
        if (credentialsCache.isCacheEnabled()) {
            return credentialsCache.getCredentialsBySenderName(senderName);
        } else {
            CredentialsService credentialsService = SpringContextHolder.getBean(CredentialsService.class);
            return credentialsService.getCredentialsForSenderName(senderName);
        }
    }

}
