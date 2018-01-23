package by.bsu.rfe.smsservice.controller;

import by.bsu.rfe.smsservice.common.dto.BaseExceptionDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by pluhin on 11/26/16.
 */
@ControllerAdvice
public class BaseExceptionHandler extends ResponseEntityExceptionHandler {

  @Autowired
  private ObjectMapper objectMapper;

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleException(Exception e) throws JsonProcessingException {
    BaseExceptionDTO baseExceptionDTO = new BaseExceptionDTO();
    baseExceptionDTO.setCode(500);
    baseExceptionDTO.setMessage(getMessage(e));

    return new ResponseEntity<>(objectMapper.writeValueAsString(baseExceptionDTO),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<Object> handleBadCredentials(BadCredentialsException e)
      throws JsonProcessingException {
    BaseExceptionDTO baseExceptionDTO = new BaseExceptionDTO();
    baseExceptionDTO.setCode(401);
    baseExceptionDTO.setMessage(getMessage(e));

    return new ResponseEntity<>(objectMapper.writeValueAsString(baseExceptionDTO),
        HttpStatus.UNAUTHORIZED);
  }

  private String getMessage(Throwable throwable) {
    Throwable root = ExceptionUtils.getRootCause(throwable);
    root = root == null ? throwable : root;
    return root.getMessage();
  }

}
