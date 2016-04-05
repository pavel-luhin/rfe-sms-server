package by.bsu.rfe.smsservice.service.impl;

import by.bsu.rfe.smsservice.builder.RequestBuilder;
import by.bsu.rfe.smsservice.common.entity.CredentialsEntity;
import by.bsu.rfe.smsservice.common.entity.EmailEntity;
import by.bsu.rfe.smsservice.common.entity.SmsTypeEntity;
import by.bsu.rfe.smsservice.common.entity.StatisticsEntity;
import by.bsu.rfe.smsservice.common.request.Request;
import by.bsu.rfe.smsservice.common.sms.base.BaseSMS;
import by.bsu.rfe.smsservice.common.sms.base.EmailSMS;
import by.bsu.rfe.smsservice.service.CredentialsService;
import by.bsu.rfe.smsservice.service.EmailService;
import by.bsu.rfe.smsservice.service.SmsInfoService;
import by.bsu.rfe.smsservice.service.StatisticsService;
import by.bsu.rfe.smsservice.service.WebSMSService;
import by.bsu.rfe.smsservice.util.MessageTemplateUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Map;

/**
 * Created by pluhin on 12/27/15.
 */
@Service
public class WebSMSServiceImpl implements WebSMSService {

    private static final Logger LOG = LoggerFactory.getLogger(WebSMSServiceImpl.class);

    private static final HttpClient httpClient = HttpClientBuilder.create().build();

    private static final String STATUS_PARAM = "status";
    private static final String STATUS_SUCCESS = "success";

    @Autowired
    @Qualifier("sendSMS")
    private RequestBuilder<BaseSMS> smsRequestBuilder;
    @Autowired
    private SmsInfoService smsInfoService;
    @Autowired
    private CredentialsService credentialsService;
    @Autowired
    private StatisticsService statisticsService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private ObjectMapper objectMapper;

    public void sendSMS(BaseSMS sms) {
        Request request = smsRequestBuilder.buildRequest(sms);
        LOG.info("Prepared request with parameters:");
        LOG.info("" + request.getParameters());
        StatisticsEntity statisticsEntity = new StatisticsEntity();
        statisticsEntity.setRecipientType(sms.recipientType());
        statisticsEntity.setNumber(sms.getRecipient());
        statisticsEntity.setText(sms.getContent());
        statisticsEntity.setSentDate(new Date());

        SmsTypeEntity smsTypeEntity = smsInfoService.getSMSTypeEntity(sms.getClass().getSimpleName());
        statisticsEntity.setSmsType(smsTypeEntity);

        CredentialsEntity credentialsEntity = credentialsService.getCredentials(sms.getClass().getSimpleName());
        statisticsEntity.setCredentials(credentialsEntity);

        try {
            HttpResponse response = execute(request);
            String content = getContent(response);
            statisticsEntity.setResponse(content);

            ObjectNode objectNode = objectMapper.readValue(content, ObjectNode.class);
            if (objectNode.get(STATUS_PARAM).asText().equals(STATUS_SUCCESS)) {
                LOG.debug("Sms sent successfully");
                statisticsEntity.setError(false);
            } else {
                LOG.debug("Error while sending sms");
                statisticsEntity.setError(true);
            }
            if (sms instanceof EmailSMS) {
                EmailSMS emailSMS = (EmailSMS) sms;
                EmailEntity emailEntity = emailService.getEmailEntity(sms.getClass().getSimpleName());
                if (emailEntity != null) {
                    String emailTemplate = emailEntity.getContent();
                    Map<String, String> templateParameters = MessageTemplateUtils.getParametersFromSMS(sms);

                    for (Map.Entry<String, String> parameter : templateParameters.entrySet()) {
                        emailTemplate = emailTemplate.replace(parameter.getKey(), parameter.getValue());
                    }

                    emailService.sendEmail(emailSMS.getEmail(), emailEntity.getSubject(), emailTemplate);
                } else {
                    emailService.sendEmail(emailSMS.getEmail(), emailSMS.getClass().getSimpleName(), emailSMS.getContent());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            statisticsService.saveStatistics(statisticsEntity);
        }
    }

    private HttpResponse execute(Request request) throws IOException {
        String apiEndpoint = request.apiEndpoint();

        org.apache.http.client.methods.RequestBuilder requestBuilder = org.apache.http.client.methods.RequestBuilder.create("POST");
        requestBuilder.setUri(apiEndpoint);
        requestBuilder.setEntity(new UrlEncodedFormEntity(request.getParameters(), "UTF-8"));

        HttpUriRequest httpRequest = requestBuilder.build();
        return httpClient.execute(httpRequest);
    }

    private String getContent(HttpResponse httpResponse) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
        String unicodeLine = "";
        StringBuilder unicodeContent = new StringBuilder();

        while ((unicodeLine = reader.readLine()) != null) {
            unicodeContent.append(unicodeLine);
        }

        return StringEscapeUtils.unescapeJava(unicodeContent.toString());
    }
}
