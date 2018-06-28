package by.bsu.rfe.smsservice.common.dto;

import by.bsu.rfe.smsservice.common.enums.RecipientType;
import java.util.Date;
import lombok.Data;
import org.dozer.Mapping;

@Data
public class StatisticsDTO {

  private Boolean error;
  @Mapping("sender")
  private String senderName;
  private String recipient;
  private String text;
  private String response;
  private String smsType;
  private RecipientType recipientType;
  private Date sentDate;
  private String initiatedBy;
}
