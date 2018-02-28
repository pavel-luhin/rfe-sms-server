package by.bsu.rfe.smsservice.common.dto.sms;

import by.bsu.rfe.smsservice.common.enums.RecipientType;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomSmsRequestDTO extends BaseSmsRequestDTO {

  private Map<String, RecipientType> recipients;
  private String content;

  public CustomSmsRequestDTO() {
    this.setDuplicateEmail(false);
  }
}
