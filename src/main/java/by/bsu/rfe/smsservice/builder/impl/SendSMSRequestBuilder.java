package by.bsu.rfe.smsservice.builder.impl;

import by.bsu.rfe.smsservice.builder.RequestBuilder;
import by.bsu.rfe.smsservice.common.entity.CredentialsEntity;
import by.bsu.rfe.smsservice.common.entity.GroupEntity;
import by.bsu.rfe.smsservice.common.entity.PersonEntity;
import by.bsu.rfe.smsservice.common.enums.RecipientType;
import by.bsu.rfe.smsservice.common.request.Request;
import by.bsu.rfe.smsservice.common.sms.base.BaseSMS;
import by.bsu.rfe.smsservice.common.websms.WebSMSRest;
import by.bsu.rfe.smsservice.service.CredentialsService;
import by.bsu.rfe.smsservice.service.RecipientService;
import by.bsu.rfe.smsservice.service.SmsInfoService;
import by.bsu.rfe.smsservice.util.MessageTemplateUtils;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

import static by.bsu.rfe.smsservice.common.websms.WebSMSParam.*;

/**
 * Created by pluhin on 12/27/15.
 */
@Component("sendSMS")
public class SendSMSRequestBuilder implements RequestBuilder<BaseSMS> {
    @Autowired
    private SmsInfoService smsInfoService;
    @Autowired
    private CredentialsService credentialsService;
    @Autowired
    private RecipientService recipientService;

    @Value("${sms.test}")
    private Integer test;

    public Request buildRequest(BaseSMS baseSMS) {
        prepareTemplate(baseSMS);
        CredentialsEntity credentials = credentialsService.getCredentials(baseSMS.getClass().getSimpleName());

        Request request = new Request();

        request.setApiEndpoint(WebSMSRest.SEND_MESSAGE.getApiEndpoint());
        request.addParameter(new BasicNameValuePair(USER.getRequestParam(), credentials.getUsername()));
        request.addParameter(new BasicNameValuePair(APIKEY.getRequestParam(), credentials.getApiKey()));
        request.addParameter(new BasicNameValuePair(SENDER.getRequestParam(), credentials.getSender()));
        request.addParameter(new BasicNameValuePair(TEST.getRequestParam(), test.toString()));
        if (baseSMS.recipientType() == RecipientType.PERSON) {
            request.addParameter(new BasicNameValuePair(RECIPIENTS.getRequestParam(), baseSMS.getRecipient()));
        } else {
            GroupEntity groupEntity = recipientService.getGroup(Integer.valueOf(baseSMS.getRecipient()));
            request.addParameter(new BasicNameValuePair(RECIPIENTS.getRequestParam(), getAllRecipientsFromGroup(groupEntity)));
        }
        request.addParameter(new BasicNameValuePair(MESSAGE.getRequestParam(), baseSMS.getContent()));

        return request;
    }

    private void prepareTemplate(BaseSMS baseSMS)  {
        if (baseSMS.useTemplate()) {
            String smsTemplate = smsInfoService.getSMSTemplate(baseSMS.getClass().getSimpleName()).getTemplate();
            Map<String, String> parameters = MessageTemplateUtils.getParametersFromSMS(baseSMS);

            for (Map.Entry<String, String> parameter : parameters.entrySet()) {
                smsTemplate = smsTemplate.replace(parameter.getKey(), parameter.getValue());
            }

            baseSMS.setContent(smsTemplate);
        }
    }

    private String getAllRecipientsFromGroup(GroupEntity group) {
        StringBuilder stringBuilder = new StringBuilder();
        for (PersonEntity person : group.getPersons()) {
            stringBuilder.append(person.getPhoneNumber());
            stringBuilder.append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }
}
