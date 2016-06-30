package by.bsu.rfe.smsservice.common.sms;

import java.util.Map;

import by.bsu.rfe.smsservice.common.entity.SmsTemplateEntity;
import by.bsu.rfe.smsservice.common.enums.RecipientType;

/**
 * Created by pluhin on 6/21/16.
 */
public class SmsDTO {
    private Map<String, RecipientType> recipients;

    private Map<String, Map<String, String>> smsParameters;
    private boolean duplicateEmail;

    private String smsContent;

    private SmsTemplateEntity smsTemplate;

    public Map<String, RecipientType> getRecipients() {
        return recipients;
    }

    public void setRecipients(Map<String, RecipientType> recipients) {
        this.recipients = recipients;
    }

    public Map<String, Map<String, String>> getSmsParameters() {
        return smsParameters;
    }

    public void setSmsParameters(Map<String, Map<String, String>> smsParameters) {
        this.smsParameters = smsParameters;
    }

    public boolean isDuplicateEmail() {
        return duplicateEmail;
    }

    public void setDuplicateEmail(boolean duplicateEmail) {
        this.duplicateEmail = duplicateEmail;
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }

    public SmsTemplateEntity getSmsTemplate() {
        return smsTemplate;
    }

    public void setSmsTemplate(SmsTemplateEntity smsTemplate) {
        this.smsTemplate = smsTemplate;
    }
}
