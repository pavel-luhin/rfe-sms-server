package by.bsu.rfe.smsservice.service.impl;

import static by.bsu.rfe.smsservice.bulk.ExcelUtils.getMessagesFromSheet;
import static by.bsu.rfe.smsservice.bulk.ExcelUtils.getSheetFromFile;
import static by.bsu.rfe.smsservice.util.DozerUtil.mapList;

import by.bsu.rfe.smsservice.common.dto.SMSResultDTO;
import by.bsu.rfe.smsservice.common.dto.sms.BulkSmsRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.CustomSmsRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.SmsQueueRequestDTO;
import by.bsu.rfe.smsservice.common.dto.sms.TemplateSmsRequestDTO;
import by.bsu.rfe.smsservice.common.entity.GroupEntity;
import by.bsu.rfe.smsservice.common.entity.SmsQueueEntity;
import by.bsu.rfe.smsservice.common.entity.SmsTemplateEntity;
import by.bsu.rfe.smsservice.common.enums.RecipientType;
import by.bsu.rfe.smsservice.repository.SmsQueueRepository;
import by.bsu.rfe.smsservice.security.util.SecurityUtil;
import by.bsu.rfe.smsservice.service.CredentialsService;
import by.bsu.rfe.smsservice.service.GroupService;
import by.bsu.rfe.smsservice.service.SmsQueueService;
import by.bsu.rfe.smsservice.service.SmsTemplateService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SmsQueueServiceImpl implements SmsQueueService {

  private final SmsQueueRepository smsQueueRepository;
  private final CredentialsService credentialsService;
  private final SmsTemplateService smsTemplateService;
  private final GroupService groupService;
  private final ObjectMapper objectMapper;
  private final Mapper mapper;

  public SmsQueueServiceImpl(SmsQueueRepository smsQueueRepository,
      CredentialsService credentialsService, SmsTemplateService smsTemplateService,
      GroupService groupService, ObjectMapper objectMapper, Mapper mapper) {
    this.smsQueueRepository = smsQueueRepository;
    this.credentialsService = credentialsService;
    this.smsTemplateService = smsTemplateService;
    this.groupService = groupService;
    this.objectMapper = objectMapper;
    this.mapper = mapper;
  }

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
    SMSResultDTO smsResultDTO = new SMSResultDTO();
    smsResultDTO.setInQueue(true);
    requestDTO.getRecipients().forEach(recipient -> {
      SmsQueueEntity entity = new SmsQueueEntity();
      entity.setInitiatedBy(SecurityUtil.getCurrentUsername());
      entity.setRecipientType(recipient.getRecipientType());
      entity.setRecipient(recipient.getName());
      entity.setMessage(requestDTO.getContent());
      entity.setDuplicateEmail(requestDTO.isDuplicateEmail());
      entity.setCredentials(
          credentialsService.getUserCredentialsForSenderName(requestDTO.getSenderName()));
      smsQueueRepository.save(entity);
      smsResultDTO.incrementTotalCountBy(1);
    });
    return smsResultDTO;
  }

  @Override
  public SMSResultDTO putToQueue(BulkSmsRequestDTO requestDTO) {
    SMSResultDTO smsResultDTO = new SMSResultDTO();
    smsResultDTO.setInQueue(true);

    log.debug("Start processing file with name {}", requestDTO.getFile().getOriginalFilename());
    Sheet sheet = getSheetFromFile(requestDTO.getFile());
    Map<String, String> totalMessages = getMessagesFromSheet(sheet);
    log.debug("Found {} messages", totalMessages.size());

    Map<String, List<String>> messagesByRecipients = new HashMap<>();

    totalMessages
        .forEach((recipient, message) -> {
          if (!messagesByRecipients.containsKey(message)) {
            messagesByRecipients.put(message, new ArrayList<>());
          }
          messagesByRecipients.get(message).add(recipient);
        });

    messagesByRecipients.forEach((message, value) -> {
      GroupEntity group = groupService
          .createGroupFromNumbers(value);
      SmsQueueEntity entity = new SmsQueueEntity();
      entity.setInitiatedBy(SecurityUtil.getCurrentUsername());
      entity.setDuplicateEmail(requestDTO.isDuplicateEmail());
      entity.setCredentials(
          credentialsService.getUserCredentialsForSenderName(requestDTO.getSenderName()));
      entity.setMessage(message);
      entity.setRecipient(group.getName());
      entity.setRecipientType(RecipientType.GROUP);
      smsQueueRepository.save(entity);
      smsResultDTO.incrementTotalCountBy(1);
    });

    return smsResultDTO;
  }

  @Override
  public SMSResultDTO putToQueue(TemplateSmsRequestDTO requestDTO) {
    SmsTemplateEntity templateEntity = smsTemplateService
        .findSMSTemplate(requestDTO.getTemplateName());

    SMSResultDTO smsResultDTO = new SMSResultDTO();
    smsResultDTO.setInQueue(true);
    requestDTO.getRecipients().forEach(recipient -> {
      SmsQueueEntity entity = new SmsQueueEntity();
      entity.setInitiatedBy(SecurityUtil.getCurrentUsername());
      entity.setRecipientType(recipient.getRecipientType());
      Map<String, String> parameters = requestDTO.getParameters().get(recipient);
      try {
        entity.setParametersJson(objectMapper.writeValueAsString(parameters));
      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }
      entity.setSmsType(requestDTO.getTemplateName());
      entity.setRecipient(recipient.getName());
      entity.setMessage(templateEntity.getTemplate());
      entity.setDuplicateEmail(requestDTO.isDuplicateEmail());
      entity.setCredentials(
          credentialsService.getUserCredentialsForSenderName(requestDTO.getSenderName()));
      smsQueueRepository.save(entity);
      smsResultDTO.incrementTotalCountBy(1);
    });
    return smsResultDTO;
  }
}
