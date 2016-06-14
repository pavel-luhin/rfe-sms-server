package by.bsu.rfe.smsservice.controller;

import by.bsu.rfe.smsservice.common.dto.SMSResultDTO;
import by.bsu.rfe.smsservice.common.sms.CustomSMS;
import by.bsu.rfe.smsservice.common.sms.InterviewSMS;
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

/**
 * Created by pluhin on 12/27/15.
 */
@Controller
@RequestMapping(value = "/sms")
public class SMSController {

    @Autowired
    private WebSMSService webSMSService;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/interview", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void interview(@RequestBody InterviewSMS sms) {
        webSMSService.sendSMS(sms);
    }

    @ResponseBody
    @RequestMapping(value = "/custom", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public SMSResultDTO sendCustom(@RequestBody CustomSMS customSMS) {
        return webSMSService.sendSMS(customSMS);
    }
}
