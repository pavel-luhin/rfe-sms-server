package by.bsu.rfe.smsservice.service.impl;

import by.bsu.rfe.smsservice.common.dto.result.ResultDTO;
import by.bsu.rfe.smsservice.common.dto.sms.BulkSmsRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.CustomSmsRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.SmsQueueRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.TemplateSmsRequestDTO;
import by.bsu.rfe.smsservice.service.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageProcessingService implements SendMessageService<ResultDTO> {

  private SendMessageService sendSmsMessageService;
  private SendMessageService sendViberMessageService;

  @Autowired
  public MessageProcessingService(
      SendMessageService sendSmsMessageService,
      SendMessageService sendViberMessageService) {
    this.sendSmsMessageService = sendSmsMessageService;
    this.sendViberMessageService = sendViberMessageService;
  }

  @Override
  public ResultDTO sendCustom(CustomSmsRequestDTO requestDTO) {
    return sendSmsMessageService.sendCustom(requestDTO);
  }

  @Override
  public ResultDTO sendTemplate(TemplateSmsRequestDTO requestDTO) {
    return null;
  }

  @Override
  public ResultDTO sendBulk(BulkSmsRequestDTO requestDTO) {
    return null;
  }

  @Override
  public ResultDTO sendSmsFromQueue(SmsQueueRequestDTO smsQueueRequestDTO) {
    return null;
  }
}
