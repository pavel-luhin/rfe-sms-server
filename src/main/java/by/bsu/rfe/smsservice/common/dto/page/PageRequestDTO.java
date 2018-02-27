package by.bsu.rfe.smsservice.common.dto.page;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageRequestDTO {

  private int offset;
  private int skip;
  private String sortField;
  private String sortDirection;
}
