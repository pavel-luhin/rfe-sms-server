package by.bsu.rfe.smsservice.service;

import by.bsu.rfe.smsservice.common.entity.SmsTemplateEntity;

/**
 * Created by pluhin on 6/21/16.
 */
public interface SmsTemplateService {
    SmsTemplateEntity getByRequestUri(String requestUri);
}
