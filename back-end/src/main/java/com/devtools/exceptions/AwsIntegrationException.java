package com.devtools.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "AWS integration error")
public class AwsIntegrationException extends RuntimeException {

  public AwsIntegrationException() {
    super("AWS integration error");
  }
}
