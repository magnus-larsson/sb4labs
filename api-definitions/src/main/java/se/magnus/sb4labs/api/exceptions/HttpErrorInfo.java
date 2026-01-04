package se.magnus.sb4labs.api.exceptions;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public record HttpErrorInfo(
  ZonedDateTime timestamp,
  String path,
  int status,
  String error,
  String message
) {

  public HttpErrorInfo(HttpStatus httpStatus, String path, String message) {
    this(ZonedDateTime.now(), path, httpStatus.value(), httpStatus.getReasonPhrase(),  message);
  }
}
