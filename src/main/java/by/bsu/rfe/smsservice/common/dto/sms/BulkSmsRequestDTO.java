package by.bsu.rfe.smsservice.common.dto.sms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class BulkSmsRequestDTO extends BaseSmsRequestDTO {

  private MultipartFile file;
}
