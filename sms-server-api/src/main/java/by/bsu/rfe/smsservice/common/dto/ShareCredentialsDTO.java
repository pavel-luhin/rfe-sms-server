package by.bsu.rfe.smsservice.common.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ShareCredentialsDTO {

  @NotNull
  private Integer userId;

  @NotNull
  private Integer credentialsId;
}
