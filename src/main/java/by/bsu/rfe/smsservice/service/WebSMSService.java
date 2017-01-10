package by.bsu.rfe.smsservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import by.bsu.rfe.smsservice.common.dto.SMSResultDTO;
import by.bsu.rfe.smsservice.common.entity.SmsQueueEntity;
import by.bsu.rfe.smsservice.common.entity.SmsTemplateEntity;
import by.bsu.rfe.smsservice.common.sms.SmsDTO;

/**
 * Created by pluhin on 12/27/15.
 */
public interface WebSMSService {
    SMSResultDTO sendSMS(SmsDTO smsDTO);
    SMSResultDTO bulkSendSMS(MultipartFile file, SmsTemplateEntity smsTemplate, Boolean sameContentForAll, String requestSenderName);
    SMSResultDTO sendSmsFromQueue(List<SmsQueueEntity> smsQueueEntities);
    Double getBalance(String username, String password);
}
