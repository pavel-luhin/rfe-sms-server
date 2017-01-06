package by.bsu.rfe.smsservice.common.entity;

import javax.persistence.*;

import by.bsu.rfe.smsservice.common.enums.RecipientType;

/**
 * Created by pluhin on 1/4/17.
 */
@Entity
@Table(name = "sms_queue")
public class SmsQueueEntity extends CreationDetails {

    @Column(name = "recipient")
    private String recipient;

    @Column(name = "recipient_type")
    @Enumerated(EnumType.STRING)
    private RecipientType recipientType;

    @Column(name = "message")
    private String message;

    @OneToOne
    @JoinColumn(name = "credentials_id")
    private CredentialsEntity credentials;

    @OneToOne
    @JoinColumn(name = "sms_type_id")
    private SmsTemplateEntity smsType;

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public RecipientType getRecipientType() {
        return recipientType;
    }

    public void setRecipientType(RecipientType recipientType) {
        this.recipientType = recipientType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CredentialsEntity getCredentials() {
        return credentials;
    }

    public void setCredentials(CredentialsEntity credentials) {
        this.credentials = credentials;
    }

    public SmsTemplateEntity getSmsType() {
        return smsType;
    }

    public void setSmsType(SmsTemplateEntity smsType) {
        this.smsType = smsType;
    }
}
