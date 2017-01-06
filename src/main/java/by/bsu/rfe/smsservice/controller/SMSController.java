package by.bsu.rfe.smsservice.controller;

import by.bsu.rfe.smsservice.common.entity.SmsQueueEntity;
import by.bsu.rfe.smsservice.service.SmsQueueService;
import org.omg.CORBA.portable.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import by.bsu.rfe.smsservice.common.dto.SMSResultDTO;
import by.bsu.rfe.smsservice.common.entity.SmsTemplateEntity;
import by.bsu.rfe.smsservice.common.sms.SmsDTO;
import by.bsu.rfe.smsservice.service.SmsTemplateService;
import by.bsu.rfe.smsservice.service.WebSMSService;

import java.util.List;

/**
 * Created by pluhin on 12/27/15.
 */
@Controller
public class SMSController {

    private static final String CUSTOM_SMS_URI = "/rest/sms/custom";

    private static final Logger LOGGER = LoggerFactory.getLogger(SMSController.class);

    @Autowired
    private WebSMSService webSMSService;

    @Autowired
    private SmsTemplateService smsTemplateService;

    @Autowired
    private SmsQueueService smsQueueService;

    @ResponseBody
    @RequestMapping(value = "/sms/*", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public SMSResultDTO sendSms(@RequestBody SmsDTO smsDTO, HttpServletRequest request) {
        smsDTO.setSmsTemplate(getTemplate(request.getRequestURI()));
        return webSMSService.sendSMS(smsDTO);
    }

    private SmsTemplateEntity getTemplate(String requestUri) {
        SmsTemplateEntity smsTemplate = smsTemplateService.getByRequestUri(requestUri);

        if (smsTemplate != null) {
            return smsTemplate;
        } else {
            LOGGER.error("Invalid send SMS URI path {}. Could not detect correct SMS type.", requestUri);
            throw new IllegalArgumentException("Invalid send SMS URI path. Could not detect correct SMS type.");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/bulkSMS", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SMSResultDTO bulkSendSMS(@RequestParam MultipartFile file, @RequestParam Boolean sameContentForAll, @RequestParam(required = false) String requestSenderName) {
        return webSMSService.bulkSendSMS(file, getTemplate(CUSTOM_SMS_URI), sameContentForAll, requestSenderName);
    }

    @ResponseBody
    @RequestMapping(value = "/sms/template", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SmsTemplateEntity> getSMSTemplate() {
        return smsTemplateService.getAllSmsTemplates();
    }

    @ResponseBody
    @RequestMapping(value = "/sms/queue", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SmsQueueEntity> getAllSmsFromQueue() {
        return smsQueueService.getAllSmsFromQueue();
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/sms/queue", method = RequestMethod.DELETE)
    public void removeSmsFromQueue(@RequestParam("id") Integer id) {
        smsQueueService.removeFromQueue(id);
    }
}
