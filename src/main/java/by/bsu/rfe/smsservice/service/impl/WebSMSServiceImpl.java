package by.bsu.rfe.smsservice.service.impl;

import static by.bsu.rfe.smsservice.bulk.ExcelUtils.getMessagesFromSheet;
import static by.bsu.rfe.smsservice.bulk.ExcelUtils.getSheetFromFile;
import static by.bsu.rfe.smsservice.common.enums.RecipientType.NUMBER;
import static by.bsu.rfe.smsservice.common.enums.SmsServerProperty.MUTE_ENABLED;
import static by.bsu.rfe.smsservice.common.enums.SmsServerProperty.MUTE_END_TIME;
import static by.bsu.rfe.smsservice.common.enums.SmsServerProperty.MUTE_START_TIME;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import by.bsu.rfe.smsservice.builder.BalanceRequestBuilder;
import by.bsu.rfe.smsservice.builder.SendSMSRequestBuilder;
import by.bsu.rfe.smsservice.common.dto.SMSResultDTO;
import by.bsu.rfe.smsservice.common.entity.CredentialsEntity;
import by.bsu.rfe.smsservice.common.entity.SmsQueueEntity;
import by.bsu.rfe.smsservice.common.entity.SmsTemplateEntity;
import by.bsu.rfe.smsservice.common.entity.StatisticsEntity;
import by.bsu.rfe.smsservice.common.enums.RecipientType;
import by.bsu.rfe.smsservice.common.enums.SmsServerProperty;
import by.bsu.rfe.smsservice.common.request.Request;
import by.bsu.rfe.smsservice.common.sms.SmsDTO;
import by.bsu.rfe.smsservice.common.websms.WebSMSParam;
import by.bsu.rfe.smsservice.security.util.SecurityUtil;
import by.bsu.rfe.smsservice.service.CredentialsService;
import by.bsu.rfe.smsservice.service.EmailService;
import by.bsu.rfe.smsservice.service.SmsQueueService;
import by.bsu.rfe.smsservice.service.SmsServerPropertyService;
import by.bsu.rfe.smsservice.service.StatisticsService;
import by.bsu.rfe.smsservice.service.WebSMSService;

/**
 * Created by pluhin on 12/27/15.
 */
@Service
public class WebSMSServiceImpl implements WebSMSService {

    private static final Logger LOG = LoggerFactory.getLogger(WebSMSServiceImpl.class);

    private static final HttpClient httpClient = HttpClientBuilder.create().build();

    private static final String STATUS_PARAM = "status";
    private static final String STATUS_SUCCESS = "success";
    private static final String BALANCE_PARAM = "balance";

    private static final Integer MAX_BULK_SIZE = 500;

    @Autowired
    private SendSMSRequestBuilder smsRequestBuilder;
    @Autowired
    private BalanceRequestBuilder balanceRequestBuilder;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private EmailService emailService;
    @Autowired
    private StatisticsService statisticsService;
    @Autowired
    private CredentialsService credentialsService;
    @Autowired
    private SmsServerPropertyService smsServerPropertyService;
    @Autowired
    private SmsQueueService smsQueueService;

    public SMSResultDTO sendSMS(SmsDTO smsDTO) {
        String smsType = smsDTO.getSmsTemplate().getSmsType();
        LOG.info("Preparing sms with SMSType {}", smsType);
        Map<String, RecipientType> recipients = MapUtils.emptyIfNull(smsDTO.getRecipients());
        Map<String, Map<String, String>> smsParameters = MapUtils.emptyIfNull(smsDTO.getSmsParameters());

        SMSResultDTO smsResultDTO = new SMSResultDTO();

        for (Map.Entry<String, RecipientType> recipient : recipients.entrySet()) {
            SMSResultDTO oneSMSResult = prepareAndSendSMS(recipient, smsParameters.get(recipient.getKey()), smsDTO.getSmsTemplate(),
                    smsDTO.isDuplicateEmail(), smsDTO.getSmsContent(), smsDTO.getRequestSenderName());
            setTotalSmsDTO(oneSMSResult, smsResultDTO);
        }


        return smsResultDTO;
    }

    @Override
    public SMSResultDTO bulkSendSMS(MultipartFile file, SmsTemplateEntity smsTemplate, Boolean sameContentForAll, String requestSenderName) {
        LOG.info("Preparing bulk send sms for file: {}", file.getOriginalFilename());
        Sheet sheet = getSheetFromFile(file);
        Map<String, String> totalMessages = getMessagesFromSheet(sheet);
        LOG.info("Retrieved {} messages", totalMessages.size());

        SMSResultDTO totalSMSResult = new SMSResultDTO();

        Boolean oneMoreTime = true;

        while (oneMoreTime) {
            StatisticsEntity statisticsEntity = new StatisticsEntity();
            oneMoreTime = false;
            Map<String, String> messages = null;
            if (totalMessages.size() <= MAX_BULK_SIZE) {
                messages = totalMessages;
            } else {
                messages = new HashMap<>();
                int count = 1;
                Iterator<Map.Entry<String, String>> entryIterator = totalMessages.entrySet().iterator();
                while (count <= MAX_BULK_SIZE) {
                    oneMoreTime = true;
                    Map.Entry<String, String> entry = entryIterator.next();
                    messages.put(entry.getKey(), entry.getValue());
                    entryIterator.remove();
                    count++;
                }
            }

            CredentialsEntity credentialsEntity = null;
            if (StringUtils.isEmpty(requestSenderName)) {
                credentialsEntity = credentialsService.getDefaultCredentialsForCurrentUser();
            } else {
                credentialsEntity = credentialsService.getCredentialsForSenderName(requestSenderName);
            }

            Request request = smsRequestBuilder.buildBulkRequest(messages, requestSenderName);

            sendSMSAndSaveStatistics(request, credentialsEntity,
                    "//Bulk sending statistics currently doesn't support displaying recipient-specific parameters//. Sent sms count" + messages.size(),
                    new MutablePair<>("BULK", NUMBER), smsTemplate, totalSMSResult);
        }

        return totalSMSResult;
    }

    @Override
    public Double getBalance(String username, String password) {
        Request request = balanceRequestBuilder.buildRequest(username, password);
        try {
            HttpResponse response = execute(request);
            String content = getContent(response);
            return Double.valueOf(getParameterFromResponse(content, BALANCE_PARAM));
        } catch (IOException e) {
            LOG.error("Something went wrong while retrieving balance for {}", username);
        }

        return null;
    }

    private SMSResultDTO prepareAndSendSMS(Map.Entry<String, RecipientType> recipient, Map<String, String> smsParameters, SmsTemplateEntity smsTemplate,
            Boolean duplicateEmail, String smsContent, String requestSenderName) {
        SMSResultDTO smsResultDTO = new SMSResultDTO();
        Request request = null;

        if (StringUtils.isNotEmpty(smsTemplate.getTemplate())) {
            request = smsRequestBuilder.buildRequest(recipient, smsParameters, smsTemplate.getTemplate(), requestSenderName);
        } else {
            request = smsRequestBuilder.buildRequest(recipient, smsParameters, smsContent, requestSenderName);
        }

        LOG.info("Prepared sms with parameters {}", request.getParameters());

        CredentialsEntity credentialsEntity = null;
        if (StringUtils.isEmpty(requestSenderName)) {
            credentialsEntity = credentialsService.getDefaultCredentialsForCurrentUser();
        } else {
            credentialsEntity = credentialsService.getCredentialsForSenderName(requestSenderName);
        }

        String text = null;

        for (NameValuePair parameter : request.getParameters()) {
            if (parameter.getName().equals(WebSMSParam.MESSAGE.getRequestParam())) {
                text = parameter.getValue();
                break;
            }
        }

        sendSMSAndSaveStatistics(request, credentialsEntity, text, recipient, smsTemplate, smsResultDTO);

        if (duplicateEmail && !isMuted()) {
            emailService.processSendingEmail(recipient, smsParameters, smsTemplate.getSmsType());
        }

        return smsResultDTO;
    }

    private SMSResultDTO sendSMSAndSaveStatistics(Request request, CredentialsEntity credentialsEntity, String text,
                                                  Map.Entry<String, RecipientType> recipient, SmsTemplateEntity smsTemplate,
                                                  SMSResultDTO smsResultDTO) {
        if (isMuted()) {
            SmsQueueEntity smsQueueEntity = new SmsQueueEntity();
            smsQueueEntity.setCredentials(credentialsEntity);
            smsQueueEntity.setMessage(text);
            smsQueueEntity.setRecipient(recipient.getKey());
            smsQueueEntity.setRecipientType(recipient.getValue());
            smsQueueEntity.setSmsType(smsTemplate);
            smsQueueService.addToQueue(smsQueueEntity);
            smsResultDTO.setInQueue(true);
            return smsResultDTO;
        }

        smsResultDTO.incrementTotalCount();

        try {
            HttpResponse response = execute(request);
            String content = getContent(response);

            StatisticsEntity statisticsEntity = new StatisticsEntity();
            statisticsEntity.setText(text);
            statisticsEntity.setSender(credentialsEntity.getSender());
            statisticsEntity.setSmsType(smsTemplate.getSmsType());
            statisticsEntity.setRecipientType(recipient.getValue());
            statisticsEntity.setSentDate(new Date());
            statisticsEntity.setResponse(content);
            statisticsEntity.setRecipient(recipient.getKey());
            statisticsEntity.setInitiatedBy(SecurityUtil.getCurrentUsername());

            if (isSuccess(content)) {
                LOG.info("SMS sent successfully");
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

        return smsResultDTO;
    }

    private boolean isMuted() {
        String muteEnabled = smsServerPropertyService.findPropertyValue(MUTE_ENABLED);

        if (Boolean.valueOf(muteEnabled)) {
            LocalTime muteStartTime = LocalTime.parse(smsServerPropertyService.findPropertyValue(MUTE_START_TIME));
            LocalTime muteEndTime = LocalTime.parse(smsServerPropertyService.findPropertyValue(MUTE_END_TIME));

            LocalTime localTime = LocalTime.now();

            if (localTime.isAfter(muteStartTime) && localTime.isBefore(muteEndTime)) {
                return true;
            }
        }

        return false;
    }

    private void setTotalSmsDTO(SMSResultDTO resultPerSMS, SMSResultDTO totalResult) {
        if (StringUtils.isEmpty(resultPerSMS.getLastError())) {
            totalResult.incrementTotalCount();
        } else {
            totalResult.incrementTotalCount();
            totalResult.incrementErrorCount();
            totalResult.setLastError(resultPerSMS.getLastError());
        }
        totalResult.setInQueue(resultPerSMS.isInQueue());
    }

    private static HttpResponse execute(Request request) throws IOException {
        String apiEndpoint = request.apiEndpoint();

        org.apache.http.client.methods.RequestBuilder requestBuilder = org.apache.http.client.methods.RequestBuilder.create("POST");
        requestBuilder.setUri(apiEndpoint);
        requestBuilder.setEntity(new UrlEncodedFormEntity(request.getParameters(), "UTF-8"));

        HttpUriRequest httpRequest = requestBuilder.build();
        return httpClient.execute(httpRequest);
    }

    private static String getContent(HttpResponse httpResponse) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
        String unicodeLine = "";
        StringBuilder unicodeContent = new StringBuilder();

        while ((unicodeLine = reader.readLine()) != null) {
            unicodeContent.append(unicodeLine);
        }

        return StringEscapeUtils.unescapeJava(unicodeContent.toString());
    }

    private String getParameterFromResponse(String response, String parameter) {
        ObjectNode objectNode = null;
        try {
            objectNode = objectMapper.readValue(response, ObjectNode.class);
            return objectNode.get(parameter).asText();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Boolean isSuccess(String response) {
        return STATUS_SUCCESS.equals(getParameterFromResponse(response, STATUS_PARAM));
    }
}
