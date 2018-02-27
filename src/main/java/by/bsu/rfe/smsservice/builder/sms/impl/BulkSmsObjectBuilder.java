package by.bsu.rfe.smsservice.builder.sms.impl;

import static by.bsu.rfe.smsservice.bulk.ExcelUtils.getMessagesFromSheet;
import static by.bsu.rfe.smsservice.bulk.ExcelUtils.getSheetFromFile;
import static by.bsu.rfe.smsservice.common.enums.RecipientType.NUMBER;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

import by.bsu.rfe.smsservice.builder.parameters.ParametersCollectorResolver;
import by.bsu.rfe.smsservice.builder.sms.BaseSmsObjectBuilder;
import by.bsu.rfe.smsservice.common.dto.sms.BulkSmsRequestDTO;
import by.bsu.rfe.smsservice.common.entity.SmsQueueEntity;
import by.bsu.rfe.smsservice.common.enums.RecipientType;
import by.bsu.rfe.smsservice.common.request.Request;
import by.bsu.rfe.smsservice.common.websms.WebSMSParam;
import by.bsu.rfe.smsservice.service.CredentialsService;
import by.bsu.rfe.smsservice.validator.mobilenumber.MobileNumberValidator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BulkSmsObjectBuilder extends BaseSmsObjectBuilder<BulkSmsRequestDTO> {

  @Autowired
  public BulkSmsObjectBuilder(
      ParametersCollectorResolver parametersCollectorResolver,
      CredentialsService credentialsService,
      List<MobileNumberValidator> mobileNumberValidators) {
    super(parametersCollectorResolver, credentialsService, mobileNumberValidators);
  }

  @Override
  public Request build(BulkSmsRequestDTO smsRequestDTO) {
    Request request = buildBaseRequest(smsRequestDTO);

    log.debug("Start processing file with name {}", smsRequestDTO.getFile().getOriginalFilename());
    Sheet sheet = getSheetFromFile(smsRequestDTO.getFile());
    Map<String, String> totalMessages = getMessagesFromSheet(sheet);
    log.debug("Found {} messages", totalMessages.size());

    Map<String, String> processedMessages = new HashMap<>();

    totalMessages.entrySet()
        .forEach(entry -> {
          Map.Entry<String, RecipientType> recipientTypeEntry =
              new ImmutablePair<>(entry.getKey(), NUMBER);
          Map<String, String> parameters = new HashMap<>();
          parametersCollectorResolver.resolve(NUMBER)
              .collectParameters(recipientTypeEntry, parameters);

          for (MobileNumberValidator validator : mobileNumberValidators) {
            String validNumber = validator.validate(entry.getKey());
            String message = createMessage(entry.getValue(), parameters, entry.getValue());
            processedMessages.put(validNumber, message);
          }
        });

    String finalMessage = createArrayOfMessages(processedMessages);
    request
        .addParameter(new BasicNameValuePair(WebSMSParam.MESSAGES.getRequestParam(), finalMessage));
    return request;
  }

  @Override
  public SmsQueueEntity buildQueue(BulkSmsRequestDTO smsRequestDTO) {
    SmsQueueEntity smsQueueEntity = new SmsQueueEntity();

    log.debug("Start processing file with name {}", smsRequestDTO.getFile().getOriginalFilename());
    Sheet sheet = getSheetFromFile(smsRequestDTO.getFile());
    Map<String, String> totalMessages = getMessagesFromSheet(sheet);
    log.debug("Found {} messages", totalMessages.size());

    List<String> numbers = new ArrayList<>(totalMessages.values());
    String message = "";
    if (isNotEmpty(numbers)) {
      message = totalMessages.get(numbers.get(0));
    }

    smsQueueEntity.setNumbers(String.join(",", numbers));
    smsQueueEntity.setMessage(message);

    return smsQueueEntity;
  }
}
