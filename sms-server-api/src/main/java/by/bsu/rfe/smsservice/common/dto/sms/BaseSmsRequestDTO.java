package by.bsu.rfe.smsservice.common.dto.sms;

import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.dozer.Mapping;

@Getter
@Setter
public abstract class BaseSmsRequestDTO {

  @Size(min = 1)
  @Mapping("credentials.sender")
  private String senderName;
  private boolean duplicateEmail;
  private boolean skipQueue;
}
