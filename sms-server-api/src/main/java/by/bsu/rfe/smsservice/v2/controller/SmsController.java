package by.bsu.rfe.smsservice.v2.controller;

import static by.bsu.rfe.smsservice.common.constants.AuthorityConstants.ROLE_APPLICATION;
import static by.bsu.rfe.smsservice.common.constants.AuthorityConstants.ROLE_USER;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.ResponseEntity.ok;

import by.bsu.rfe.smsservice.common.dto.SMSResultDTO;
import by.bsu.rfe.smsservice.common.dto.sms.BulkSmsRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.CustomSmsRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.TemplateSmsRequestDTO;
import by.bsu.rfe.smsservice.v2.domain.SmsResult;
import by.bsu.rfe.smsservice.v2.domain.recipient.DefaultRecipient;
import by.bsu.rfe.smsservice.v2.domain.recipient.Recipient;
import by.bsu.rfe.smsservice.v2.domain.sms.DefaultSms;
import by.bsu.rfe.smsservice.v2.domain.sms.Sms;
import by.bsu.rfe.smsservice.v2.domain.sms.SmsRequest;
import by.bsu.rfe.smsservice.v2.sms.SmsService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/v2/sms")
@Secured({ROLE_USER, ROLE_APPLICATION})
public class SmsController {

  private final SmsService smsService;

  public SmsController(SmsService smsService) {
    this.smsService = smsService;
  }

  @PostMapping(value = "/sms/send/custom", consumes = APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<SMSResultDTO> sendCustomSms(
      @RequestBody @Valid CustomSmsRequestDTO requestDTO) {
    return ok(sendSmsService.sendCustom(requestDTO));
  }

  @Secured({ROLE_USER, ROLE_APPLICATION})
  @PostMapping(value = "/sms/send/template", consumes = APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<SMSResultDTO> sendTemplateSms(
      @RequestBody @Valid TemplateSmsRequestDTO requestDTO) {
    return ok(sendSmsService.sendTemplate(requestDTO));
  }

  @PostMapping(value = "/sms/send/bulk", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<SMSResultDTO> sendBulkSms(@Valid BulkSmsRequestDTO requestDTO) {
    return ok(sendSmsService.sendBulk(requestDTO));
  }
}
