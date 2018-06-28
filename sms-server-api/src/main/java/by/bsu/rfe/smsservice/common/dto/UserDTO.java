package by.bsu.rfe.smsservice.common.dto;

import static by.bsu.rfe.smsservice.common.constants.ValidationConstants.EMAIL_MAX_LENGTH;
import static by.bsu.rfe.smsservice.common.constants.ValidationConstants.EMAIL_REGEX;
import static by.bsu.rfe.smsservice.common.constants.ValidationConstants.MIN_LENGTH;

import by.bsu.rfe.smsservice.common.constants.ValidationConstants;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserDTO extends CreatedDetails {

  private Integer id;

  @Pattern(regexp = EMAIL_REGEX)
  @Size(min = MIN_LENGTH, max = EMAIL_MAX_LENGTH)
  private String username;
}
