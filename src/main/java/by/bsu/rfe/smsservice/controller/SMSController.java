package by.bsu.rfe.smsservice.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

import by.bsu.rfe.smsservice.common.dto.SMSResultDTO;
import by.bsu.rfe.smsservice.common.entity.SmsQueueEntity;
import by.bsu.rfe.smsservice.common.entity.SmsTemplateEntity;
import by.bsu.rfe.smsservice.common.sms.SmsDTO;
import by.bsu.rfe.smsservice.service.SmsQueueService;
import by.bsu.rfe.smsservice.service.SmsTemplateService;
import by.bsu.rfe.smsservice.service.WebSMSService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by pluhin on 12/27/15.
 */
@Slf4j
@Controller
@RequestMapping(produces = APPLICATION_JSON_UTF8_VALUE)
public class SMSController {

  private static final String CUSTOM_SMS_URI = "/rest/sms/custom";

  private WebSMSService webSMSService;
  private SmsTemplateService smsTemplateService;
  private SmsQueueService smsQueueService;

  @Autowired
  public SMSController(WebSMSService webSMSService,
      SmsTemplateService smsTemplateService,
      SmsQueueService smsQueueService) {
    this.webSMSService = webSMSService;
    this.smsTemplateService = smsTemplateService;
    this.smsQueueService = smsQueueService;
  }

  @PostMapping(value = "/sms/*", consumes = APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<SMSResultDTO> sendSms(@RequestBody SmsDTO smsDTO,
      HttpServletRequest request) {
    smsDTO.setSmsTemplate(getTemplate(request.getRequestURI()));
    return ok(webSMSService.sendSMS(smsDTO));
  }

  private SmsTemplateEntity getTemplate(String requestUri) {
    SmsTemplateEntity smsTemplate = smsTemplateService.getByRequestUri(requestUri);

    if (smsTemplate != null) {
      return smsTemplate;
    } else {
      log.error("Invalid send SMS URI path {}. Could not detect correct SMS type.", requestUri);
      throw new IllegalArgumentException(
          "Invalid send SMS URI path. Could not detect correct SMS type.");
    }
  }

  @PostMapping(value = "/bulkSMS", consumes = APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<SMSResultDTO> bulkSendSMS(@RequestParam MultipartFile file,
      @RequestParam Boolean sameContentForAll,
      @RequestParam(required = false) String requestSenderName) {
    SMSResultDTO resultDTO = webSMSService
        .bulkSendSMS(file, getTemplate(CUSTOM_SMS_URI), sameContentForAll, requestSenderName);
    return ok(resultDTO);
  }

  @GetMapping("/sms/template")
  public ResponseEntity<List<SmsTemplateEntity>> getSMSTemplate() {
    return ok(smsTemplateService.getAllSmsTemplates());
  }

  @GetMapping("/sms/queue")
  public ResponseEntity<List<SmsQueueEntity>> getAllSmsFromQueue() {
    return ok(smsQueueService.getAllSmsFromQueue());
  }

  @DeleteMapping("/sms/queue")
  public ResponseEntity removeSmsFromQueue(@RequestParam("id") Integer id) {
    smsQueueService.removeFromQueue(id);
    return noContent().build();
  }
}
