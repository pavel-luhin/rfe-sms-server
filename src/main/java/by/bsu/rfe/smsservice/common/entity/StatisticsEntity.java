package by.bsu.rfe.smsservice.common.entity;

import by.bsu.rfe.smsservice.common.enums.RecipientType;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by pluhin on 12/27/15.
 */
@Entity
@Table(name = "statistics")
public class StatisticsEntity extends AbstractPersistable<Integer> {
    private Boolean error;
    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "cred_fk"))
    private CredentialsEntity credentials;
    private String number;
    private String text;
    private String response;
    @Enumerated(EnumType.STRING)
    @Column(name = "recipient_type")
    private RecipientType recipientType;
    @Column(name = "sent_date")
    private Date sentDate;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public CredentialsEntity getCredentials() {
        return credentials;
    }

    public void setCredentials(CredentialsEntity credentials) {
        this.credentials = credentials;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public RecipientType getRecipientType() {
        return recipientType;
    }

    public void setRecipientType(RecipientType recipientType) {
        this.recipientType = recipientType;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }
}
