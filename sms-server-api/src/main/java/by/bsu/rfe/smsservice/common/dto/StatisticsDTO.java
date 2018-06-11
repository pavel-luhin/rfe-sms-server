package by.bsu.rfe.smsservice.common.dto;

import by.bsu.rfe.smsservice.common.enums.RecipientType;
import java.util.Date;
import lombok.Data;

@Data
public class StatisticsDTO {

  private Boolean error;
  private String senderName;
  private String recipient;
  private String text;
  private String response;
  private String smsType;
  private RecipientType recipientType;
  private Date sentDate;
  private String initiatedBy;
}
