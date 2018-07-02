package by.bsu.rfe.smsservice.service.impl;

import by.bsu.rfe.smsservice.common.dto.result.ViberResultDTO;
import by.bsu.rfe.smsservice.common.dto.sms.BulkSmsRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.CustomSmsRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.SmsQueueRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.TemplateSmsRequestDTO;
import by.bsu.rfe.smsservice.service.SendMessageService;
import org.springframework.stereotype.Service;

@Service
public class SendViberMessageService implements SendMessageService<ViberResultDTO> {

  @Override
  public ViberResultDTO sendCustom(CustomSmsRequestDTO requestDTO) {
    return null;
  }

  @Override
  public ViberResultDTO sendTemplate(TemplateSmsRequestDTO requestDTO) {
    return null;
  }

  @Override
  public ViberResultDTO sendBulk(BulkSmsRequestDTO requestDTO) {
    return null;
  }

  @Override
  public ViberResultDTO sendSmsFromQueue(SmsQueueRequestDTO smsQueueRequestDTO) {
    return null;
  }
}
