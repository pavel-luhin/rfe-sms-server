package by.bsu.rfe.smsservice.common.dto;

import static by.bsu.rfe.smsservice.common.constants.ValidationConstants.EMAIL_MAX_LENGTH;
import static by.bsu.rfe.smsservice.common.constants.ValidationConstants.EMAIL_REGEX;
import static by.bsu.rfe.smsservice.common.constants.ValidationConstants.MIN_LENGTH;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CredentialsDTO extends CreatedDetails {

  private Integer id;

  @NotNull
  @Size(min = MIN_LENGTH)
  private String apiKey;

  @NotNull
  @Pattern(regexp = EMAIL_REGEX)
  @Size(min = MIN_LENGTH, max = EMAIL_MAX_LENGTH)
  private String username;

  @NotNull
  @Size(min = MIN_LENGTH)
  private String sender;

  private Double balance;
}
