package by.bsu.rfe.smsservice.service.impl;

import by.bsu.rfe.smsservice.common.entity.SmsTemplateEntity;
import by.bsu.rfe.smsservice.common.entity.SmsTypeEntity;
import by.bsu.rfe.smsservice.repository.SmsTemplateRepository;
import by.bsu.rfe.smsservice.repository.SmsTypeRepository;
import by.bsu.rfe.smsservice.service.SmsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by pluhin on 3/20/16.
 */
@Service
public class SmsInfoServiceImpl implements SmsInfoService {
    @Autowired
    private SmsTypeRepository smsTypeRepository;
    @Autowired
    private SmsTemplateRepository smsTemplateRepository;

    @Override
    public SmsTypeEntity getSMSTypeEntity(String smsTypeName) {
        return smsTypeRepository.getSmsType(smsTypeName);
    }
}
