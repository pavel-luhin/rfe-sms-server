package by.bsu.rfe.smsservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import by.bsu.rfe.smsservice.common.entity.SmsTemplateEntity;
import by.bsu.rfe.smsservice.repository.SmsTemplateRepository;
import by.bsu.rfe.smsservice.service.SmsTemplateService;

/**
 * Created by pluhin on 6/21/16.
 */
@Service
public class SmsTemplateServiceImpl implements SmsTemplateService {

    @Autowired
    private SmsTemplateRepository smsTemplateRepository;

    @Override
    public SmsTemplateEntity getByRequestUri(String requestUri) {
        return smsTemplateRepository.getByURIPath(requestUri);
    }

    @Override
    public List<SmsTemplateEntity> getSmsTemplates() {
        return smsTemplateRepository.findAll();
    }

    @Override
    public void removeSMSTemplate(Integer id) {
        smsTemplateRepository.delete(id);
    }

    @Override
    public SmsTemplateEntity addSMSTemplate(SmsTemplateEntity smsTemplateEntity) {
        return smsTemplateRepository.saveAndFlush(smsTemplateEntity);
    }

    @Override
    public List<SmsTemplateEntity> findSMSTemplate() {
        return smsTemplateRepository.findAll();
    }
}
