package by.bsu.rfe.smsservice.service;

import by.bsu.rfe.smsservice.common.dto.result.ResultDTO;
import by.bsu.rfe.smsservice.common.dto.sms.BulkSmsRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.CustomSmsRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.SmsQueueRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.TemplateSmsRequestDTO;

public interface SendMessageService<R extends ResultDTO> {

  R sendCustom(CustomSmsRequestDTO requestDTO);

  R sendTemplate(TemplateSmsRequestDTO requestDTO);

  R sendBulk(BulkSmsRequestDTO requestDTO);

  R sendSmsFromQueue(SmsQueueRequestDTO smsQueueRequestDTO);
}
