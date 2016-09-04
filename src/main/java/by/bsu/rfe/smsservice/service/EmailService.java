package by.bsu.rfe.smsservice.service;

import java.util.List;
import java.util.Map;

import by.bsu.rfe.smsservice.common.dto.EmailTemplateDTO;
import by.bsu.rfe.smsservice.common.entity.EmailEntity;
import by.bsu.rfe.smsservice.common.enums.RecipientType;

/**
 * Created by pluhin on 3/20/16.
 */
public interface EmailService {
    void sendPostRegistrationEmail(String email, String password);
    void processSendingEmail(Map.Entry<String, RecipientType> recipient, Map<String, String> parameters);
    void sendEmail(String address, String subject, String body);

    List<EmailTemplateDTO> getAllEmailTemplates();
    void saveEmailTemplate(EmailTemplateDTO emailTemplateDTO);
    void removeEmailTemplate(Integer id);
}
