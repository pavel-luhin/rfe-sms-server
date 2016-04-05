package by.bsu.rfe.smsservice.common.sms.base;

import by.bsu.rfe.smsservice.common.enums.RecipientType;

import java.io.Serializable;

/**
 * Base class for all sms
 */
public abstract class BaseSMS implements Serializable {

    private String recipient;
    private String content;

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public abstract Boolean useTemplate();

    public abstract RecipientType recipientType();
}
