package by.bsu.rfe.smsservice.service;

import by.bsu.rfe.smsservice.common.sms.base.BaseSMS;

/**
 * Created by pluhin on 12/27/15.
 */
public interface WebSMSService {
    void sendSMS(BaseSMS sms);
}
