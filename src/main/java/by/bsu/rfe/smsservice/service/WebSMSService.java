package by.bsu.rfe.smsservice.service;

import org.springframework.web.multipart.MultipartFile;

import by.bsu.rfe.smsservice.common.dto.SMSResultDTO;
import by.bsu.rfe.smsservice.common.entity.SmsTemplateEntity;
import by.bsu.rfe.smsservice.common.sms.SmsDTO;

/**
 * Created by pluhin on 12/27/15.
 */
public interface WebSMSService {
    SMSResultDTO sendSMS(SmsDTO smsDTO);
    SMSResultDTO bulkSendSMS(MultipartFile file, SmsTemplateEntity smsTemplate, Boolean sameContentForAll, String requestSenderName);
}
