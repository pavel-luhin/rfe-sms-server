package by.bsu.rfe.smsservice.common.dto;

import static by.bsu.rfe.smsservice.common.constants.ValidationConstants.MIN_LENGTH;

import by.bsu.rfe.smsservice.common.constants.ValidationConstants;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EmailTemplateDTO extends CreatedDetails {

  private Integer id;

  @NotNull
  @Size(min = MIN_LENGTH)
  private String subject;

  @NotNull
  @Size(min = MIN_LENGTH)
  private String content;

  @NotNull
  @Size(min = MIN_LENGTH)
  private String smsType;
}
