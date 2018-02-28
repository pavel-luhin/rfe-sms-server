package by.bsu.rfe.smsservice.service.impl;

import static by.bsu.rfe.smsservice.common.dto.SMSResultDTO.fromResponse;
import static by.bsu.rfe.smsservice.util.MuteUtil.isMuted;

import by.bsu.rfe.smsservice.builder.RequestBuilderHolder;
import by.bsu.rfe.smsservice.common.dto.SMSResultDTO;
import by.bsu.rfe.smsservice.common.dto.sms.BaseSmsRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.BulkSmsRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.CustomSmsRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.SmsQueueRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.TemplateSmsRequestDTO;
import by.bsu.rfe.smsservice.common.request.Request;
import by.bsu.rfe.smsservice.common.response.SendSmsResponse;
import by.bsu.rfe.smsservice.service.EmailService;
import by.bsu.rfe.smsservice.service.SendSmsService;
import by.bsu.rfe.smsservice.service.SmsQueueService;
import by.bsu.rfe.smsservice.service.StatisticsService;
import by.bsu.rfe.smsservice.service.WebSmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendSmsServiceImpl implements SendSmsService {

  private RequestBuilderHolder requestBuilderHolder;
  private WebSmsService webSmsService;
  private EmailService emailService;
  private StatisticsService statisticsService;
  private SmsQueueService smsQueueService;

  @Autowired
  public SendSmsServiceImpl(
      RequestBuilderHolder requestBuilderHolder,
      WebSmsService webSmsService, EmailService emailService,
      StatisticsService statisticsService,
      SmsQueueService smsQueueService) {
    this.requestBuilderHolder = requestBuilderHolder;
    this.webSmsService = webSmsService;
    this.emailService = emailService;
    this.statisticsService = statisticsService;
    this.smsQueueService = smsQueueService;
  }

  @Override
  public SMSResultDTO sendCustom(CustomSmsRequestDTO requestDTO) {
    if (isMuted()) {
      return smsQueueService.putToQueue(requestDTO);
    }
    Request request = requestBuilderHolder.getCustomSmsRequestBuilder().build(requestDTO);
    SendSmsResponse sendSmsResponse = webSmsService.sendSms(request);
    runPostSendingHooks(requestDTO);
    return fromResponse(sendSmsResponse);
  }

  @Override
  public SMSResultDTO sendTemplate(TemplateSmsRequestDTO requestDTO) {
    if (isMuted()) {
      return smsQueueService.putToQueue(requestDTO);
    }
    Request request = requestBuilderHolder.getTemplateSmsRequestBuilder().build(requestDTO);
    SendSmsResponse sendSmsResponse = webSmsService.sendSms(request);
    runPostSendingHooks(requestDTO);
    return fromResponse(sendSmsResponse);
  }

  @Override
  public SMSResultDTO sendBulk(BulkSmsRequestDTO requestDTO) {
    if (isMuted()) {
      return smsQueueService.putToQueue(requestDTO);
    }
    Request request = requestBuilderHolder.getBulkSmsRequestBuilder().build(requestDTO);
    SendSmsResponse sendSmsResponse = webSmsService.sendSms(request);
    runPostSendingHooks(requestDTO);
    return fromResponse(sendSmsResponse);
  }

  @Override
  public SMSResultDTO sendSmsFromQueue(SmsQueueRequestDTO smsQueueRequestDTO) {
    Request request = requestBuilderHolder.getQueueSmsRequestBuilder().build(smsQueueRequestDTO);
    SendSmsResponse sendSmsResponse = webSmsService.sendSms(request);
    runPostSendingHooks(smsQueueRequestDTO);
    return fromResponse(sendSmsResponse);
  }

  private void runPostSendingHooks(BaseSmsRequestDTO requestDTO) {
    if (requestDTO.isDuplicateEmail()) {

    }
  }
}
