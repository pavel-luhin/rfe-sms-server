package by.bsu.rfe.smsservice.service.impl;

import by.bsu.rfe.smsservice.common.entity.CredentialsEntity;
import by.bsu.rfe.smsservice.repository.CredentialsRepository;
import by.bsu.rfe.smsservice.service.CredentialsService;
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

    @Override
    public List<CredentialsEntity> getAllCredentials() {
        return credentialsRepository.findAll();
    }
}
