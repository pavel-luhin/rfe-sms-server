package by.bsu.rfe.smsservice.builder;

import by.bsu.rfe.smsservice.cache.credentials.CredentialsCache;
import by.bsu.rfe.smsservice.common.entity.CredentialsEntity;
import by.bsu.rfe.smsservice.common.entity.GroupEntity;
import by.bsu.rfe.smsservice.common.entity.PersonEntity;
import by.bsu.rfe.smsservice.common.enums.RecipientType;
import by.bsu.rfe.smsservice.common.request.Request;
import by.bsu.rfe.smsservice.common.websms.WebSMSRest;
import by.bsu.rfe.smsservice.security.util.SecurityUtil;
import by.bsu.rfe.smsservice.service.CredentialsService;
import by.bsu.rfe.smsservice.service.RecipientService;
import by.bsu.rfe.smsservice.service.SmsInfoService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static by.bsu.rfe.smsservice.common.websms.WebSMSParam.*;

/**
 * Created by pluhin on 12/27/15.
 */
@Component("sendSMS")
public class SendSMSRequestBuilder {

    @Autowired
    private CredentialsCache credentialsCache;
    @Autowired
    private RecipientService recipientService;

    @Value("${sms.test}")
    private Integer test;

    public Request buildRequest(Map.Entry<String, RecipientType> recipient, Map<String, String> parameters, String smsContent, String smsType) {
        if (recipient.getValue() != RecipientType.NUMBER) {
            collectAdditionalParameters(recipient, parameters);
        }

        Request request = new Request();

        CredentialsEntity credentials = credentialsCache.getCredentialsForSMSTypeOrDefault(smsType);

        request.setApiEndpoint(WebSMSRest.SEND_MESSAGE.getApiEndpoint());
        request.addParameter(new BasicNameValuePair(USER.getRequestParam(), credentials.getUsername()));
        request.addParameter(new BasicNameValuePair(APIKEY.getRequestParam(), credentials.getApiKey()));
        request.addParameter(new BasicNameValuePair(SENDER.getRequestParam(), credentials.getSender()));
        request.addParameter(new BasicNameValuePair(TEST.getRequestParam(), test.toString()));
        if (recipient.getValue() == RecipientType.NUMBER) {
            request.addParameter(new BasicNameValuePair(RECIPIENTS.getRequestParam(), recipient.getKey()));
        } else if (recipient.getValue() == RecipientType.GROUP) {
            GroupEntity groupEntity = recipientService.getGroup(Integer.valueOf(recipient.getKey()));
            request.addParameter(new BasicNameValuePair(RECIPIENTS.getRequestParam(), getAllRecipientsFromGroup(groupEntity)));
        } else {
            //process adding registered recipient
        }
        request.addParameter(new BasicNameValuePair(MESSAGE.getRequestParam(), smsContent));

        return request;
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

    private void collectAdditionalParameters(Map.Entry<String, RecipientType> recipient, Map<String, String> parameters) {

    }

    private CredentialsEntity getCredentialsForSMS(String smsType, List<CredentialsEntity> userCredentials) {
        if (CollectionUtils.isNotEmpty(userCredentials)) {
            for (CredentialsEntity credentialsEntity : userCredentials) {
                if (credentialsEntity.getSmsType().equals(smsType)) {
                    return credentialsEntity;
                }
            }
        }
        return null;
    }
}
