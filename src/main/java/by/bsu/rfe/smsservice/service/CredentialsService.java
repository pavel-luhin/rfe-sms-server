package by.bsu.rfe.smsservice.service;

import by.bsu.rfe.smsservice.common.entity.CredentialsEntity;

/**
 * Created by pluhin on 3/20/16.
 */
public interface CredentialsService {
    CredentialsEntity getCredentials(String smsType);
}
