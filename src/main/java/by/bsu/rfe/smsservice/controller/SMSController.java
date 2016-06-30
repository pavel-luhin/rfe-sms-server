package by.bsu.rfe.smsservice.controller;

import by.bsu.rfe.smsservice.common.dto.SMSResultDTO;
import by.bsu.rfe.smsservice.common.entity.SmsTemplateEntity;
import by.bsu.rfe.smsservice.common.sms.SmsDTO;
import by.bsu.rfe.smsservice.service.SmsTemplateService;
import by.bsu.rfe.smsservice.service.WebSMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by pluhin on 12/27/15.
 */
@Controller
@RequestMapping(value = "/sms")
public class SMSController {

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
            throw new IllegalArgumentException("Invalid send SMS URI path. Could not detect correct SMS type.");
        }
    }
}
