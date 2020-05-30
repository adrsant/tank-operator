package com.devtools.alloy.http.handlers;

import java.util.HashMap;
import java.util.Map;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class CustomExceptionHandler {

  @ResponseBody
  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Map<String, String> onMethodArgumentNotValidException(
      ConstraintViolationException exception) {
    Map<String, String> errors = new HashMap<>();

    var fieldErrors = exception.getConstraintViolations();
    fieldErrors.forEach(e -> errors.put(e.getPropertyPath().toString(), e.getMessage()));

    return errors;
  }
}
