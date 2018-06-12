package by.bsu.rfe.smsservice.common.dto;

import static by.bsu.rfe.smsservice.common.constants.ValidationConstants.MIN_LENGTH;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dozer.Mapping;

@Data
@EqualsAndHashCode(callSuper = false)
public class ExternalApplicationDTO extends CreatedDetails {

  private Integer id;

  @NotNull
  @Size(min = MIN_LENGTH)
  private String applicationName;

  @NotNull
  @Size(min = MIN_LENGTH)
  private String authenticationToken;

  @NotNull
  @Size(min = MIN_LENGTH)
  @Mapping("defaultCredentials.sender")
  private String credentialsSenderName;
}
