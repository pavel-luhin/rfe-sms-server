package by.bsu.rfe.smsservice.service;

import by.bsu.rfe.smsservice.common.entity.SmsTemplateEntity;
import by.bsu.rfe.smsservice.common.entity.SmsTypeEntity;

/**
 * Created by pluhin on 3/20/16.
 */
public interface SmsInfoService {
    SmsTypeEntity getSMSTypeEntity(String smsTypeName);
}
