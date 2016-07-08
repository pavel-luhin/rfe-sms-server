package by.bsu.rfe.smsservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import by.bsu.rfe.smsservice.common.dto.SMSResultDTO;
import by.bsu.rfe.smsservice.common.entity.SmsTemplateEntity;
import by.bsu.rfe.smsservice.common.sms.SmsDTO;
import by.bsu.rfe.smsservice.service.SmsTemplateService;
import by.bsu.rfe.smsservice.service.WebSMSService;

/**
 * Created by pluhin on 12/27/15.
 */
@Controller
@RequestMapping(value = "/sms")
public class SMSController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SMSController.class);

    @Autowired
    private WebSMSService webSMSService;

    @Autowired
    private SmsTemplateService smsTemplateService;

    @ResponseBody
    @RequestMapping(value = "/*", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public SMSResultDTO sendSms(@RequestBody SmsDTO smsDTO, HttpServletRequest request) {
        setTemplate(smsDTO, request.getRequestURI());
        return webSMSService.sendSMS(smsDTO);
    }

    private void setTemplate(SmsDTO smsDTO, String requestUri) {
        SmsTemplateEntity smsTemplate = smsTemplateService.getByRequestUri(requestUri);

        if (smsTemplate != null) {
            smsDTO.setSmsTemplate(smsTemplate);
        } else {
            LOGGER.error("Invalid send SMS URI path {}. Could not detect correct SMS type.", requestUri);
            throw new IllegalArgumentException("Invalid send SMS URI path. Could not detect correct SMS type.");
        }
    }
}
