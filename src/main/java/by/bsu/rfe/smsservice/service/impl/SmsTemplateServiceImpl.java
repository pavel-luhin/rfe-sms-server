package by.bsu.rfe.smsservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import by.bsu.rfe.smsservice.common.dto.EmailTemplateDTO;
import by.bsu.rfe.smsservice.common.entity.SmsTemplateEntity;
import by.bsu.rfe.smsservice.repository.SmsTemplateRepository;
import by.bsu.rfe.smsservice.service.EmailService;
import by.bsu.rfe.smsservice.service.SmsTemplateService;

/**
 * Created by pluhin on 6/21/16.
 */
@Service
public class SmsTemplateServiceImpl implements SmsTemplateService {

    @Autowired
    private SmsTemplateRepository smsTemplateRepository;

    @Autowired
    private EmailService emailService;

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
    public SmsTemplateEntity findSMSTemplate(String smsType) {
        return smsTemplateRepository.findSMSTemplate(smsType);
    }

    @Override
    public List<SmsTemplateEntity> getAllSmsTemplates() {
        return smsTemplateRepository.findAll();
    }

    @Override
    public List<String> getAvailableSmsTypesForEmailTemplate() {
        List<EmailTemplateDTO> emailTemplates = emailService.getAllEmailTemplates();
        List<String> smsTypes = emailTemplates.stream().map(EmailTemplateDTO::getSmsType).collect(Collectors.toList());

        List<SmsTemplateEntity> smsTemplateEntities = smsTemplateRepository.getAllSmsTemplatesExcept(smsTypes);

        return smsTemplateEntities.stream().map(SmsTemplateEntity::getSmsType).collect(Collectors.toList());
    }
}
