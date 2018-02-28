package by.bsu.rfe.smsservice.common.dto.sms;

import by.bsu.rfe.smsservice.common.entity.GroupEntity;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BulkSmsRequestDTO extends BaseSmsRequestDTO {

  private MultipartFile file;
  private GroupEntity createdGroup;
  private String message;


  public BulkSmsRequestDTO() {
    this.setDuplicateEmail(false);
  }
}
