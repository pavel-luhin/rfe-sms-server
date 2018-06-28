package by.bsu.rfe.smsservice.common.dto;

import static by.bsu.rfe.smsservice.common.constants.ValidationConstants.MIN_LENGTH;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordDTO {

  @NotNull
  @Size(min = MIN_LENGTH)
  private String oldPassword;

  @NotNull
  @Size(min = MIN_LENGTH)
  private String newPassword;
}
