package by.bsu.rfe.smsservice.common.dto;

import static by.bsu.rfe.smsservice.common.constants.ValidationConstants.MIN_LENGTH;

import by.bsu.rfe.smsservice.common.constants.ValidationConstants;
import by.bsu.rfe.smsservice.common.enums.RecipientType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecipientDTO {

  private Integer id;

  @NotNull
  @Size(min = MIN_LENGTH)
  private String name;

  @NotNull
  private RecipientType recipientType;

  public RecipientDTO(String name, RecipientType recipientType) {
    this.name = name;
    this.recipientType = recipientType;
  }
}
