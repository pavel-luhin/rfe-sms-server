package by.bsu.rfe.smsservice.service;

import java.util.List;

import by.bsu.rfe.smsservice.common.entity.SmsTemplateEntity;

/**
 * Created by pluhin on 6/21/16.
 */
public interface SmsTemplateService {
    SmsTemplateEntity getByRequestUri(String requestUri);
    List<SmsTemplateEntity> getSmsTemplates();
    void removeSMSTemplate(Integer id);
    SmsTemplateEntity addSMSTemplate(SmsTemplateEntity smsTemplateEntity);
    List<SmsTemplateEntity> findSMSTemplate();
}
