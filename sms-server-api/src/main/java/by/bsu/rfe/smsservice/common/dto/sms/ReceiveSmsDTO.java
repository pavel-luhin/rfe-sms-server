package by.bsu.rfe.smsservice.common.dto.sms;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public class ReceiveSmsDTO {

  private String smsId;
  private String sender;
  private LocalDateTime sentDate;
  private String receiver;
  private String text;
  private Double cost;

  @JsonCreator
  public ReceiveSmsDTO(
      @JsonProperty("sms_id") String smsId,
      @JsonProperty("from_number") String sender,
      @JsonProperty("confirm_date") LocalDateTime sentDate,
      @JsonProperty("receiver") String receiver,
      @JsonProperty("text") String text,
      @JsonProperty("cost") Double cost) {
    this.smsId = smsId;
    this.sender = sender;
    this.sentDate = sentDate;
    this.receiver = receiver;
    this.text = text;
    this.cost = cost;
  }

  public String getSmsId() {
    return smsId;
  }

  public String getSender() {
    return sender;
  }

  public LocalDateTime getSentDate() {
    return sentDate;
  }

  public String getReceiver() {
    return receiver;
  }

  public String getText() {
    return text;
  }

  public Double getCost() {
    return cost;
  }
}
