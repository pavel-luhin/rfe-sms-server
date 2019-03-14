package by.bsu.rfe.smsservice.v2.sms.prepare;

import by.bsu.rfe.smsservice.common.dto.RecipientDTO;
import by.bsu.rfe.smsservice.common.entity.GroupEntity;
import by.bsu.rfe.smsservice.common.entity.PersonEntity;
import by.bsu.rfe.smsservice.service.RecipientService;
import by.bsu.rfe.smsservice.v2.domain.SmsResult;
import by.bsu.rfe.smsservice.v2.domain.message.DefaultMessage;
import by.bsu.rfe.smsservice.v2.domain.message.Message;
import by.bsu.rfe.smsservice.v2.domain.sms.tmp.sms.CustomSmsRequestDTO;
import by.bsu.rfe.smsservice.v2.domain.sms.tmp.sms.PreparedSmsDTO;
import by.bsu.rfe.smsservice.v2.sms.SmsService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrepareCustomSmsService implements SmsService<CustomSmsRequestDTO> {

  private final SmsService delegate;
  private final RecipientService recipientService;

  @Autowired
  public PrepareCustomSmsService(SmsService delegate, RecipientService recipientService) {
    this.delegate = delegate;
    this.recipientService = recipientService;
  }

  @Override
  public SmsResult process(CustomSmsRequestDTO sms) {
    List<Message> messages = getMessages(sms);
    PreparedSmsDTO preparedSmsDTO = new PreparedSmsDTO(
        sms.getSenderName(),
        sms.isDuplicateEmail(),
        sms.isSkipQueue(),
        sms.getSmsType(),
        messages
    );
    return delegate.process(preparedSmsDTO);
  }

  private List<Message> getMessages(CustomSmsRequestDTO smsRequestDTO) {
    Set<String> numbers = new HashSet<>();
    smsRequestDTO.getRecipients()
        .stream()
        .map(recipientService::fetchNumbers)
        .forEach(numbers::addAll);

    return numbers
        .stream()
        .map(number -> new DefaultMessage(number, smsRequestDTO.getContent()))
        .collect(Collectors.toList());
  }
}
