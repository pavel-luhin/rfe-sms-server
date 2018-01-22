package by.bsu.rfe.smsservice.service;

import by.bsu.rfe.smsservice.common.entity.SmsQueueEntity;

import java.util.List;

/**
 * Created by pluhin on 1/4/17.
 */
public interface SmsQueueService {
    void addToQueue(SmsQueueEntity smsQueueEntity);
    List<SmsQueueEntity> getAllSmsFromQueue();
    void removeFromQueue(Integer id);
}
