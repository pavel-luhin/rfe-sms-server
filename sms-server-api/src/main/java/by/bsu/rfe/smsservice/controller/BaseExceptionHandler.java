package by.bsu.rfe.smsservice.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.status;

import by.bsu.rfe.smsservice.common.dto.error.BaseExceptionDTO;
import by.bsu.rfe.smsservice.common.dto.error.FieldErrorDTO;
import by.bsu.rfe.smsservice.exception.CredentialsNotFoundException;
import by.bsu.rfe.smsservice.exception.TemplateNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class BaseExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<Object> handleBadCredentials(BadCredentialsException e)
      throws JsonProcessingException {
    return status(UNAUTHORIZED)
        .body(new BaseExceptionDTO(UNAUTHORIZED.value(), getMessage(e)));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e) {
    return badRequest().body(new BaseExceptionDTO(BAD_REQUEST.value(), getMessage(e)));
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    BindingResult result = ex.getBindingResult();
    BaseExceptionDTO exceptionDTO =
        new BaseExceptionDTO(UNPROCESSABLE_ENTITY.value(), "Validation error");

    result.getFieldErrors()
        .forEach(field -> {
          exceptionDTO.getFieldErrors()
              .add(new FieldErrorDTO(field.getField(), field.getDefaultMessage()));
        });

    return status(UNPROCESSABLE_ENTITY).body(exceptionDTO);
  }

  @ExceptionHandler(CredentialsNotFoundException.class)
  public ResponseEntity<Object> handleCredentialsNotFoundException(
      CredentialsNotFoundException ex) {
    return status(BAD_REQUEST).body(
        new BaseExceptionDTO(BAD_REQUEST.value(), ex.getMessage()));
  }

  @ExceptionHandler(TemplateNotFoundException.class)
  public ResponseEntity<Object> handleTemplateNotFoundException(
      TemplateNotFoundException ex) {
    return status(BAD_REQUEST).body(new BaseExceptionDTO(BAD_REQUEST.value(), ex.getMessage()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleException(Exception e) throws JsonProcessingException {
    log.error("Unrecognized exception catch {}", e.getMessage());
    log.error("", e);
    return status(INTERNAL_SERVER_ERROR)
        .body(new BaseExceptionDTO(INTERNAL_SERVER_ERROR.value(), getMessage(e)));
  }

  @ExceptionHandler({AccessDeniedException.class})
  public ResponseEntity<Object> handleAccessDeniedException(Exception ex) {
    return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), FORBIDDEN);
  }

  private String getMessage(Throwable throwable) {
    Throwable root = ExceptionUtils.getRootCause(throwable);
    root = root == null ? throwable : root;
    return root.getMessage();
  }

}
