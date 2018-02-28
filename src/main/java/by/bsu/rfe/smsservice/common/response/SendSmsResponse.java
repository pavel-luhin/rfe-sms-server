package by.bsu.rfe.smsservice.common.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class SendSmsResponse {

  @JsonProperty("message")
  private String errorMessage;
  private boolean error;
  private List<SmsResponse> messages = new ArrayList<>();
  private String textResponse;

  @Data
  static class SmsResponse {

    private Integer smsId;
    private String customId;
    private Integer parts;
    private Double cost;
    private Double amount;
  }
}
