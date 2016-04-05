package by.bsu.rfe.smsservice.common.sms;

import by.bsu.rfe.smsservice.common.enums.RecipientType;
import by.bsu.rfe.smsservice.common.sms.base.BaseSMS;
import by.bsu.rfe.smsservice.common.sms.base.EmailSMS;

/**
 * Created by pluhin on 3/20/16.
 */
public class CustomSMS extends BaseSMS {

    private RecipientType recipientType;

    @Override
    public Boolean useTemplate() {
        return false;
    }

    @Override
    public RecipientType recipientType() {
        return recipientType;
    }

    public void setRecipientType(RecipientType recipientType) {
        this.recipientType = recipientType;
    }
}
