package by.bsu.rfe.smsservice.service;

import java.util.List;

import by.bsu.rfe.smsservice.common.entity.CredentialsEntity;

/**
 * Created by pluhin on 3/20/16.
 */
public interface CredentialsService {
    List<CredentialsEntity> getAllCredentials();
}
