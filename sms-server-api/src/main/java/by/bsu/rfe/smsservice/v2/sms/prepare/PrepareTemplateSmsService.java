package by.bsu.rfe.smsservice.v2.sms.prepare;

import by.bsu.rfe.smsservice.common.entity.SmsTemplateEntity;
import by.bsu.rfe.smsservice.exception.TemplateNotFoundException;
import by.bsu.rfe.smsservice.service.SmsTemplateService;
import by.bsu.rfe.smsservice.v2.domain.SmsResult;
import by.bsu.rfe.smsservice.v2.domain.message.MessageBuilder;
import by.bsu.rfe.smsservice.v2.domain.sms.tmp.sms.TemplateSmsRequestDTO;
import by.bsu.rfe.smsservice.v2.sms.SmsService;
import java.util.Optional;

public class PrepareTemplateSmsService implements SmsService<TemplateSmsRequestDTO> {

  private final SmsService delegate;
  private final SmsTemplateService smsTemplateService;
  private final MessageBuilder messageBuilder;

  public PrepareTemplateSmsService(SmsService delegate,
      SmsTemplateService smsTemplateService, MessageBuilder messageBuilder) {
    this.delegate = delegate;
    this.smsTemplateService = smsTemplateService;
    this.messageBuilder = messageBuilder;
  }

  @Override
  public SmsResult process(TemplateSmsRequestDTO sms) {
    SmsTemplateEntity smsTemplateEntity = smsTemplateService
        .findSMSTemplate(sms.getTemplateName());

    String template = Optional.ofNullable(smsTemplateEntity)
        .map(SmsTemplateEntity::getTemplate)
        .orElseThrow(() -> new TemplateNotFoundException(
            "Sms template with name " + sms.getTemplateName() + " not found"));


    return null;
  }
}
