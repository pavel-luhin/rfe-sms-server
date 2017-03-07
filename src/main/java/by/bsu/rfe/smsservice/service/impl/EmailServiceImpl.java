package by.bsu.rfe.smsservice.service.impl;

import static by.bsu.rfe.smsservice.builder.SendSMSRequestBuilder.createMessage;
import static by.bsu.rfe.smsservice.common.enums.SmsServerProperty.*;
import static by.bsu.rfe.smsservice.common.enums.SmsServerProperty.SmsServerPropertyGroup.EMAIL;

import by.bsu.rfe.smsservice.common.enums.SmsServerProperty;
import by.bsu.rfe.smsservice.service.SmsServerPropertyService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import by.bsu.rfe.smsservice.common.dto.EmailTemplateDTO;
import by.bsu.rfe.smsservice.common.entity.EmailEntity;
import by.bsu.rfe.smsservice.common.entity.GroupEntity;
import by.bsu.rfe.smsservice.common.entity.PersonEntity;
import by.bsu.rfe.smsservice.common.entity.SmsTemplateEntity;
import by.bsu.rfe.smsservice.common.enums.RecipientType;
import by.bsu.rfe.smsservice.repository.EmailRepository;
import by.bsu.rfe.smsservice.service.EmailService;
import by.bsu.rfe.smsservice.service.RecipientService;
import by.bsu.rfe.smsservice.service.SmsTemplateService;
import by.bsu.rfe.smsservice.util.DozerUtil;

/**
 * Created by pluhin on 3/20/16.
 */
@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

    private static final String REGISTER_USER_SMS_TYPE = "RegisterUserSMS";
    private static final String EMAIL_PARAMETERS_KEY = "email_address";

    @Autowired
    private SmsServerPropertyService smsServerPropertyService;
    @Autowired
    private EmailRepository emailRepository;
    @Autowired
    private SmsTemplateService smsTemplateService;
    @Autowired
    private RecipientService recipientService;
    @Autowired
    private Mapper mapper;

    @Override
    public void sendPostRegistrationEmail(String email, String password) {
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("${SERVER.URL}", System.getProperty("server.url"));
        parameterMap.put("${EMAIL}", email);
        parameterMap.put("${PASSWORD}", password);
        parameterMap.put("${USERNAME}", email.split("@")[0]);

        Pair<String, RecipientType> recipient = new MutablePair<>(email, null);
        processSendingEmail(recipient, parameterMap, REGISTER_USER_SMS_TYPE);
    }

    @Override
    public void processSendingEmail(Map.Entry<String, RecipientType> recipient, Map<String, String> parameters, String smsType) {
        String address = processRecipients(recipient, parameters);

        LOGGER.info("Fetching recipients for email for sms type {}", smsType);
        if (StringUtils.isEmpty(address)) {
            return;
        }

        EmailEntity emailEntity = emailRepository.findBySMSType(smsType);
        String message = createMessage(emailEntity.getContent(), parameters, emailEntity.getContent());
        sendEmail(address, emailEntity.getSubject(), message);
    }

    @Override
    public void sendEmail(String address, String subject, String body) {
        if (!Boolean.valueOf(smsServerPropertyService.findPropertyValue(EMAIL_ENABLED))) {
            return;
        }

        Map<String, String> emailProperties = smsServerPropertyService.findGroupProperties(EMAIL);

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", emailProperties.get(AUTHENTICATION_ENABLED.getDisplayValue()));
        properties.put("mail.smtp.starttls.enable", emailProperties.get(TLS_ENABLED.getDisplayValue()));
        properties.put("mail.smtp.host", emailProperties.get(SMTP_HOST.getDisplayValue()));
        properties.put("mail.smtp.port", emailProperties.get(SMTP_PORT.getDisplayValue()));

        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                emailProperties.get(USERNAME.getDisplayValue()),
                                emailProperties.get(PASSWORD.getDisplayValue()));
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailProperties.get(USERNAME.getDisplayValue())));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(address));
            message.setSubject(subject);
            message.setContent(body, "text/html; charset=utf-8");

            Transport.send(message);
            LOGGER.info("Email to {} address successfully sent", address);
        } catch (MessagingException e) {
            LOGGER.error("Could not send email. Exception message is: {}", e.getMessage());
            LOGGER.error("{}", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<EmailTemplateDTO> getAllEmailTemplates() {
        return DozerUtil.mapList(mapper, emailRepository.findAll(), EmailTemplateDTO.class);
    }

    @Override
    public void saveEmailTemplate(EmailTemplateDTO emailTemplateDTO) {
        EmailEntity emailEntity = mapper.map(emailTemplateDTO, EmailEntity.class);
        SmsTemplateEntity smsTemplateEntity = smsTemplateService.findSMSTemplate(emailTemplateDTO.getSmsType());
        emailEntity.setSmsTemplate(smsTemplateEntity);
        emailRepository.saveAndFlush(emailEntity);
    }

    @Override
    public void removeEmailTemplate(Integer id) {
        emailRepository.delete(id);
    }

    private String processRecipients(Map.Entry<String, RecipientType> recipient, Map<String, String> parameters) {
        if (recipient.getValue() == null) {
            return recipient.getKey();
        }

        if (recipient.getValue() == RecipientType.NUMBER) {

            for (Map.Entry<String, String> parameter : parameters.entrySet()) {
                if (parameter.getKey().equals(EMAIL_PARAMETERS_KEY)) {
                    return parameter.getValue();
                }
            }

            LOGGER.error("Could not find email address for recipient {}", recipient.getKey());
            LOGGER.error("Parameters are: {}", parameters);

            return null;
        } else if (recipient.getValue() == RecipientType.PERSON) {
            PersonEntity personEntity = recipientService.getPerson(recipient.getKey().split("-"));
            return personEntity.getEmail();
        } else {
            GroupEntity groupEntity = recipientService.getGroupByName(recipient.getKey());

            StringBuilder emailBuilder = new StringBuilder();

            for (PersonEntity personEntity : groupEntity.getPersons()) {
                emailBuilder.append(personEntity.getEmail());
                emailBuilder.append(",");
            }

            emailBuilder.deleteCharAt(emailBuilder.length() - 1);
            return emailBuilder.toString();
        }
    }
}
