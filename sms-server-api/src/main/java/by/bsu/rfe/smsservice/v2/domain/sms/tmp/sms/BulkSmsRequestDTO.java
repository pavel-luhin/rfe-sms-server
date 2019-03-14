package by.bsu.rfe.smsservice.v2.domain.sms.tmp.sms;

import by.bsu.rfe.smsservice.common.entity.GroupEntity;
import by.bsu.rfe.smsservice.v2.domain.SmsType;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

public class BulkSmsRequestDTO extends BaseSmsRequestDTO {

  @NotNull
  private MultipartFile file;
  private GroupEntity createdGroup;
  private String message;

  public BulkSmsRequestDTO(String senderName, boolean duplicateEmail, boolean skipQueue,
      SmsType smsType, MultipartFile file,
      GroupEntity createdGroup, String message) {
    super(senderName, duplicateEmail, skipQueue, smsType);
    this.file = file;
    this.createdGroup = createdGroup;
    this.message = message;
  }

  public MultipartFile getFile() {
    return file;
  }

  public GroupEntity getCreatedGroup() {
    return createdGroup;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public boolean isDuplicateEmail() {
    return false;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BulkSmsRequestDTO that = (BulkSmsRequestDTO) o;
    return Objects.equals(file, that.file) &&
        Objects.equals(createdGroup, that.createdGroup) &&
        Objects.equals(message, that.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(file, createdGroup, message);
  }

  @Override
  public String toString() {
    return "BulkSmsRequestDTO{" +
        "file=" + file +
        ", createdGroup=" + createdGroup +
        ", message='" + message + '\'' +
        '}';
  }
}
