package by.bsu.rfe.smsservice.service.impl;

import static by.bsu.rfe.smsservice.common.dto.SMSResultDTO.success;
import static by.bsu.rfe.smsservice.util.DozerUtil.mapList;

import by.bsu.rfe.smsservice.builder.RequestBuilderHolder;
import by.bsu.rfe.smsservice.common.dto.SMSResultDTO;
import by.bsu.rfe.smsservice.common.dto.sms.BulkSmsRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.CustomSmsRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.SmsQueueRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.TemplateSmsRequestDTO;
import by.bsu.rfe.smsservice.common.entity.SmsQueueEntity;
import by.bsu.rfe.smsservice.repository.SmsQueueRepository;
import by.bsu.rfe.smsservice.service.SmsQueueService;
import java.util.List;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by pluhin on 1/4/17.
 */
@Service
public class SmsQueueServiceImpl implements SmsQueueService {

  @Autowired
  private SmsQueueRepository smsQueueRepository;

  @Autowired
  private RequestBuilderHolder requestBuilderHolder;

  @Autowired
  private Mapper mapper;

  @Override
  public void addToQueue(SmsQueueEntity smsQueueEntity) {
    smsQueueRepository.saveAndFlush(smsQueueEntity);
  }

  @Override
  public List<SmsQueueRequestDTO> getAllSmsFromQueue() {
    List<SmsQueueEntity> entities = smsQueueRepository.findAll();

    return mapList(mapper, entities, SmsQueueRequestDTO.class);
  }

  @Override
  public void removeFromQueue(Integer id) {
    smsQueueRepository.delete(id);
  }

  @Override
  public SMSResultDTO putToQueue(CustomSmsRequestDTO requestDTO) {
    SmsQueueEntity entity = requestBuilderHolder.getCustomSmsRequestBuilder().buildQueue(requestDTO);
    smsQueueRepository.save(entity);
    return success(1);
  }

  @Override
  public SMSResultDTO putToQueue(BulkSmsRequestDTO requestDTO) {
    SmsQueueEntity entity = requestBuilderHolder.getBulkSmsRequestBuilder().buildQueue(requestDTO);
    smsQueueRepository.save(entity);
    return success(1);
  }

  @Override
  public SMSResultDTO putToQueue(TemplateSmsRequestDTO requestDTO) {
    SmsQueueEntity entity = requestBuilderHolder.getTemplateSmsRequestBuilder().buildQueue(requestDTO);
    smsQueueRepository.save(entity);
    return success(1);
  }
}
