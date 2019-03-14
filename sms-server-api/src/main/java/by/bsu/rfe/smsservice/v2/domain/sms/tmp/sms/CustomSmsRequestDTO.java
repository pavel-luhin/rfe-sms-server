package by.bsu.rfe.smsservice.v2.domain.sms.tmp.sms;

import by.bsu.rfe.smsservice.common.dto.RecipientDTO;
import by.bsu.rfe.smsservice.v2.domain.SmsType;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

public class CustomSmsRequestDTO extends BaseSmsRequestDTO {

  @NotNull
  private List<RecipientDTO> recipients;

  @NotNull
  @Size(min = 1)
  private String content;

  public CustomSmsRequestDTO(String senderName, boolean duplicateEmail, boolean skipQueue,
      SmsType smsType,
      List<RecipientDTO> recipients, String content) {
    super(senderName, duplicateEmail, skipQueue, smsType);
    this.recipients = recipients;
    this.content = content;
  }

  public List<RecipientDTO> getRecipients() {
    return recipients;
  }

  public String getContent() {
    return content;
  }

  @Override
  public boolean isDuplicateEmail() {
    return false;
  }
}
