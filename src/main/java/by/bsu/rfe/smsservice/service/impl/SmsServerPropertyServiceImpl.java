package by.bsu.rfe.smsservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import by.bsu.rfe.smsservice.common.entity.SmsServerPropertyEntity;
import by.bsu.rfe.smsservice.common.enums.SmsServerProperty;
import by.bsu.rfe.smsservice.repository.SmsServerPropertyRepository;
import by.bsu.rfe.smsservice.service.SmsServerPropertyService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pluhin on 1/4/17.
 */
@Service
public class SmsServerPropertyServiceImpl implements SmsServerPropertyService {

    @Autowired
    private SmsServerPropertyRepository smsServerPropertyRepository;

    @Override
    public String findPropertyValue(SmsServerProperty smsServerProperty) {
        return returnPropertyValue(smsServerPropertyRepository.findOne(smsServerProperty));
    }

    @Override
    public List<SmsServerPropertyEntity> getAllProperties() {
        return smsServerPropertyRepository.findAll();
    }

    @Override
    public void saveAllProperties(List<SmsServerPropertyEntity> properties) {
        smsServerPropertyRepository.save(properties);
    }

    private String returnPropertyValue(SmsServerPropertyEntity smsServerPropertyEntity) {
        return smsServerPropertyEntity.getValue();
    }
}
