package by.bsu.rfe.smsservice.service.impl;

import static by.bsu.rfe.smsservice.common.dto.SMSResultDTO.success;

import by.bsu.rfe.smsservice.builder.sms.SmsObjectBuilderHolder;
import by.bsu.rfe.smsservice.common.dto.SMSResultDTO;
import by.bsu.rfe.smsservice.common.dto.sms.BulkSmsRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.CustomSmsRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.TemplateSmsRequestDTO;
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

  @Autowired
  private SmsObjectBuilderHolder smsObjectBuilderHolder;

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

  @Override
  public SMSResultDTO putToQueue(CustomSmsRequestDTO requestDTO) {
    SmsQueueEntity entity = smsObjectBuilderHolder.getCustomSmsRequestBuilder().buildQueue(requestDTO);
    smsQueueRepository.save(entity);
    return success(1);
  }

  @Override
  public SMSResultDTO putToQueue(BulkSmsRequestDTO requestDTO) {
    SmsQueueEntity entity = smsObjectBuilderHolder.getBulkSmsRequestBuilder().buildQueue(requestDTO);
    smsQueueRepository.save(entity);
    return success(1);
  }

  @Override
  public SMSResultDTO putToQueue(TemplateSmsRequestDTO requestDTO) {
    SmsQueueEntity entity = smsObjectBuilderHolder.getTemplateSmsRequestBuilder().buildQueue(requestDTO);
    smsQueueRepository.save(entity);
    return success(1);
  }
}
