package by.bsu.rfe.smsservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import by.bsu.rfe.smsservice.builder.SendSMSRequestBuilder;
import by.bsu.rfe.smsservice.common.dto.SMSResultDTO;
import by.bsu.rfe.smsservice.common.entity.StatisticsEntity;
import by.bsu.rfe.smsservice.common.enums.RecipientType;
import by.bsu.rfe.smsservice.common.request.Request;
import by.bsu.rfe.smsservice.common.sms.SmsDTO;
import by.bsu.rfe.smsservice.common.websms.WebSMSParam;
import by.bsu.rfe.smsservice.service.EmailService;
import by.bsu.rfe.smsservice.service.StatisticsService;
import by.bsu.rfe.smsservice.service.WebSMSService;
import by.bsu.rfe.smsservice.util.CredentialsUtils;

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
    private SendSMSRequestBuilder smsRequestBuilder;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private EmailService emailService;
    @Autowired
    private StatisticsService statisticsService;

    public SMSResultDTO sendSMS(SmsDTO smsDTO) {
        String smsType = smsDTO.getSmsTemplate().getSmsType();
        LOG.info("Preparing sms with SMSType {}", smsType);
        Map<String, RecipientType> recipients = MapUtils.emptyIfNull(smsDTO.getRecipients());
        Map<String, Map<String, String>> smsParameters = smsDTO.getSmsParameters();

        SMSResultDTO smsResultDTO = new SMSResultDTO();

        List<StatisticsEntity> statistics = new ArrayList<>(recipients.size());

        for (Map.Entry<String, RecipientType> recipient : recipients.entrySet()) {
            smsResultDTO.incrementTotalCount();
            Request request = null;

            if (StringUtils.isNotEmpty(smsDTO.getSmsContent())) {
                request = smsRequestBuilder.buildRequest(recipient, smsParameters.get(recipient.getKey()), smsDTO.getSmsContent(), smsType);
            } else {
                request = smsRequestBuilder.buildRequest(recipient, smsParameters.get(recipient.getKey()), smsDTO.getSmsTemplate().getTemplate(), smsType);
            }

            LOG.info("Prepared sms with parameters {}", request.getParameters());

            try {
                HttpResponse response = execute(request);
                String content = getContent(response);

                StatisticsEntity statisticsEntity = new StatisticsEntity();
                statistics.add(statisticsEntity);

                for (NameValuePair parameter : request.getParameters()) {
                    if (parameter.getName().equals(WebSMSParam.MESSAGE.getRequestParam())) {
                        statisticsEntity.setText(parameter.getValue());
                        break;
                    }
                }

                statisticsEntity.setCredentials(CredentialsUtils.getUserCredentialsForSMSType(smsType));
                statisticsEntity.setSmsTemplate(smsDTO.getSmsTemplate());
                statisticsEntity.setRecipientType(recipient.getValue());
                statisticsEntity.setSentDate(new Date());
                statisticsEntity.setResponse(content);
                statisticsEntity.setRecipient(recipient.getKey());

                ObjectNode objectNode = objectMapper.readValue(content, ObjectNode.class);
                if (objectNode.get(STATUS_PARAM).asText().equals(STATUS_SUCCESS)) {
                    LOG.info("Sms sent successfully");
                    statisticsEntity.setError(false);
                } else {
                    LOG.error("Error while sending sms");
                    LOG.error("Detail response is:");
                    LOG.error("{}", content);
                    smsResultDTO.setLastError(content);
                    smsResultDTO.incrementErrorCount();
                    statisticsEntity.setError(true);
                }

                statisticsService.saveStatistics(statisticsEntity);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (smsDTO.isDuplicateEmail()) {
                emailService.processSendingEmail(recipient, smsParameters.get(recipient.getKey()));
            }
        }

        return smsResultDTO;
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
