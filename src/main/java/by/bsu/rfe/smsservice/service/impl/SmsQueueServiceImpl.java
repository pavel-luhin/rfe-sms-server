package by.bsu.rfe.smsservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import by.bsu.rfe.smsservice.common.entity.SmsQueueEntity;
import by.bsu.rfe.smsservice.repository.SmsQueueRepository;
import by.bsu.rfe.smsservice.service.SmsQueueService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by pluhin on 1/4/17.
 */
@Service
public class SmsQueueServiceImpl implements SmsQueueService {

    @Autowired
    private SmsQueueRepository smsQueueRepository;

    @Override
    public void addToQueue(SmsQueueEntity smsQueueEntity) {
        smsQueueRepository.saveAndFlush(smsQueueEntity);
    }

    @Override
    public List<SmsQueueEntity> getAllSmsFromQueue() {
        return smsQueueRepository.findAll();
    }

    @Override
    public void removeFromQueue(Integer id) {
        smsQueueRepository.delete(id);
    }
}
