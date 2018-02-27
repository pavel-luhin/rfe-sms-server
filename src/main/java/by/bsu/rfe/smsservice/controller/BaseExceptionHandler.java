package by.bsu.rfe.smsservice.controller;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.ResponseEntity.status;

import by.bsu.rfe.smsservice.common.dto.BaseExceptionDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleException(Exception e) throws JsonProcessingException {
    return status(INTERNAL_SERVER_ERROR)
        .body(new BaseExceptionDTO(INTERNAL_SERVER_ERROR.value(), getMessage(e)));
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<Object> handleBadCredentials(BadCredentialsException e)
      throws JsonProcessingException {
    return status(UNAUTHORIZED)
        .body(new BaseExceptionDTO(UNAUTHORIZED.value(), getMessage(e)));
  }

  private String getMessage(Throwable throwable) {
    Throwable root = ExceptionUtils.getRootCause(throwable);
    root = root == null ? throwable : root;
    return root.getMessage();
  }

}
