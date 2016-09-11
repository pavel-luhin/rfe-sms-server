package by.bsu.rfe.smsservice.service.impl;

import static by.bsu.rfe.smsservice.builder.SendSMSRequestBuilder.createMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.dozer.Mapper;
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
    @Value("${email.username}")
    private String emailUsername;
    @Value("${email.password}")
    private String emailPassword;
    @Value("${email.host}")
    private String emailHost;
    @Value("${email.port}")
    private String emailPort;
    @Value("${email.authentication.enable}")
    private Boolean emailEnableAuthentication;
    @Value("${email.starttls.enable}")
    private String emailStartTLSEnable;

    private static final String REGISTER_USER_SMS_TYPE = "RegisterUserSMS";

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
        String address = processRecipients(recipient);

        if (StringUtils.isEmpty(address)) {
            return;
        }

        EmailEntity emailEntity = emailRepository.findBySMSType(smsType);
        String message = createMessage(emailEntity.getContent(), parameters, emailEntity.getContent());
        sendEmail(address, emailEntity.getSubject(), message);
    }

    @Override
    public void sendEmail(String address, String subject, String body) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", emailEnableAuthentication);
        properties.put("mail.smtp.starttls.enable", emailStartTLSEnable);
        properties.put("mail.smtp.host", emailHost);
        properties.put("mail.smtp.port", emailPort);

        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(emailUsername, emailPassword);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailUsername));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(address));
            message.setSubject(subject);
            message.setContent(body, "text/html; charset=utf-8");

            Transport.send(message);
        } catch (MessagingException e) {
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

    private String processRecipients(Map.Entry<String, RecipientType> recipient) {
        if (recipient.getValue() == null) {
            return recipient.getKey();
        }

        if (recipient.getValue() == RecipientType.NUMBER) {
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
