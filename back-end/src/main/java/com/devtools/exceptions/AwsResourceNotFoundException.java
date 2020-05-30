package com.devtools.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "AWS Resource requested not found")
public class AwsResourceNotFoundException extends RuntimeException {

  public AwsResourceNotFoundException() {
    super("AWS Resource requested not found");
  }
}
