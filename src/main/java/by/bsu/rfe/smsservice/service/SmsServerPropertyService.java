package by.bsu.rfe.smsservice.service;

import by.bsu.rfe.smsservice.common.entity.SmsServerPropertyEntity;
import by.bsu.rfe.smsservice.common.enums.SmsServerProperty;

import java.util.List;
import java.util.Map;

/**
 * Created by pluhin on 1/4/17.
 */
public interface SmsServerPropertyService {
    String findPropertyValue(SmsServerProperty smsServerProperty);
    Map<String, Map<String, String>> getAllProperties();
    void saveAllProperties(Map<String, Map<String, String>> properties);
}
