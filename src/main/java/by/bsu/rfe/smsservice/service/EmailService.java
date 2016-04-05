package by.bsu.rfe.smsservice.service;

import by.bsu.rfe.smsservice.common.entity.EmailEntity;

/**
 * Created by pluhin on 3/20/16.
 */
public interface EmailService {
    EmailEntity getEmailEntity(String smsType);
    void sendEmail(String address, String subject, String body);
}
