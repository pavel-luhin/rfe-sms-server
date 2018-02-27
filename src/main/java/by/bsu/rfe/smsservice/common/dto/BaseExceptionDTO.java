package by.bsu.rfe.smsservice.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by pluhin on 11/26/16.
 */
@Getter
@AllArgsConstructor
public class BaseExceptionDTO {

  private Integer code;
  private String message;
}
