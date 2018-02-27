package by.bsu.rfe.smsservice.service.impl;

import static by.bsu.rfe.smsservice.common.enums.SmsServerProperty.MUTE_ENABLED;
import static by.bsu.rfe.smsservice.common.enums.SmsServerProperty.MUTE_END_TIME;
import static by.bsu.rfe.smsservice.common.enums.SmsServerProperty.MUTE_START_TIME;

import by.bsu.rfe.smsservice.builder.sms.SmsObjectBuilderHolder;
import by.bsu.rfe.smsservice.common.dto.SMSResultDTO;
import by.bsu.rfe.smsservice.common.dto.sms.BaseSmsRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.BulkSmsRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.CustomSmsRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.TemplateSmsRequestDTO;
import by.bsu.rfe.smsservice.common.request.Request;
import by.bsu.rfe.smsservice.service.EmailService;
import by.bsu.rfe.smsservice.service.SendSmsService;
import by.bsu.rfe.smsservice.service.SmsQueueService;
import by.bsu.rfe.smsservice.service.SmsServerPropertyService;
import by.bsu.rfe.smsservice.service.StatisticsService;
import by.bsu.rfe.smsservice.service.WebSmsService;
import java.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendSmsServiceImpl implements SendSmsService {

  private SmsObjectBuilderHolder requestBuilderHolder;
  private WebSmsService webSmsService;
  private EmailService emailService;
  private StatisticsService statisticsService;
  private SmsServerPropertyService smsServerPropertyService;
  private SmsQueueService smsQueueService;

  @Autowired
  public SendSmsServiceImpl(
      SmsObjectBuilderHolder requestBuilderHolder,
      WebSmsService webSmsService, EmailService emailService,
      StatisticsService statisticsService,
      SmsServerPropertyService smsServerPropertyService,
      SmsQueueService smsQueueService) {
    this.requestBuilderHolder = requestBuilderHolder;
    this.webSmsService = webSmsService;
    this.emailService = emailService;
    this.statisticsService = statisticsService;
    this.smsServerPropertyService = smsServerPropertyService;
    this.smsQueueService = smsQueueService;
  }

  @Override
  public SMSResultDTO sendCustom(CustomSmsRequestDTO requestDTO) {
    if (isMuteModeActive()) {
      return smsQueueService.putToQueue(requestDTO);
    }
    Request request = requestBuilderHolder.getCustomSmsRequestBuilder().build(requestDTO);
    webSmsService.execute(request);
    runPostSendingHooks(requestDTO);
    return null;
  }

  @Override
  public SMSResultDTO sendTemplate(TemplateSmsRequestDTO requestDTO) {
    if (isMuteModeActive()) {
      return smsQueueService.putToQueue(requestDTO);
    }
    Request request = requestBuilderHolder.getTemplateSmsRequestBuilder().build(requestDTO);
    webSmsService.execute(request);
    runPostSendingHooks(requestDTO);
    return null;
  }

  @Override
  public SMSResultDTO sendBulk(BulkSmsRequestDTO requestDTO) {
    if (isMuteModeActive()) {
      return smsQueueService.putToQueue(requestDTO);
    }
    Request request = requestBuilderHolder.getBulkSmsRequestBuilder().build(requestDTO);
    webSmsService.execute(request);
    runPostSendingHooks(requestDTO);
    return null;
  }

  private boolean isMuteModeActive() {
    Boolean isMuteEnabled = Boolean.valueOf(smsServerPropertyService.findPropertyValue(MUTE_ENABLED));

    if (isMuteEnabled) {
      LocalTime muteStartTime = LocalTime.parse(smsServerPropertyService.findPropertyValue(MUTE_START_TIME));
      LocalTime muteEndTime = LocalTime.parse(smsServerPropertyService.findPropertyValue(MUTE_END_TIME));

      LocalTime localTime = LocalTime.now();

      if (localTime.isAfter(muteStartTime) && localTime.isBefore(muteEndTime)) {
        return true;
      }
    }

    return false;
  }

  private void runPostSendingHooks(BaseSmsRequestDTO requestDTO) {

  }
}
