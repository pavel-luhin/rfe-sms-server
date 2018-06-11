package by.bsu.rfe.smsservice.common.dto;

import static by.bsu.rfe.smsservice.common.constants.ValidationConstants.EMAIL_MAX_LENGTH;
import static by.bsu.rfe.smsservice.common.constants.ValidationConstants.EMAIL_REGEX;
import static by.bsu.rfe.smsservice.common.constants.ValidationConstants.MIN_LENGTH;
import static by.bsu.rfe.smsservice.common.constants.ValidationConstants.NAME_MAX_LENGTH;
import static by.bsu.rfe.smsservice.common.constants.ValidationConstants.NAME_REGEX;
import static by.bsu.rfe.smsservice.common.constants.ValidationConstants.PHONE_NUMBER_MAX_LENGTH;
import static by.bsu.rfe.smsservice.common.constants.ValidationConstants.PHONE_NUMBER_REGEX;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class PersonDTO {

  private Integer id;

  @NotNull
  @Pattern(regexp = NAME_REGEX)
  @Size(min = MIN_LENGTH, max = NAME_MAX_LENGTH)
  private String firstName;

  @NotNull
  @Pattern(regexp = NAME_REGEX)
  @Size(min = MIN_LENGTH, max = NAME_MAX_LENGTH)
  private String lastName;

  @NotNull
  @Pattern(regexp = PHONE_NUMBER_REGEX)
  @Size(min = MIN_LENGTH, max = PHONE_NUMBER_MAX_LENGTH)
  private String phoneNumber;

  @NotNull
  @Pattern(regexp = EMAIL_REGEX)
  @Size(min = MIN_LENGTH, max = EMAIL_MAX_LENGTH)
  private String email;
}
