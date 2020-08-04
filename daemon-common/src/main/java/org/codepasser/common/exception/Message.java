package org.codepasser.common.exception;

import com.google.common.base.Strings;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Message.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
public class Message implements Serializable {

  private static final long serialVersionUID = 1743921407136288307L;
  private boolean wrappedInRuntimeException;
  private String type;
  private String partial;
  private String code;
  private String message;
  private String detail;

  private Message(
      boolean wrappedInRuntimeException, String type, String code, String message, String detail) {
    this.wrappedInRuntimeException = wrappedInRuntimeException;
    this.type = type;
    this.code = code;
    this.message = message;
    this.detail = detail;
  }

  public Message(AdException exception) {
    init((Throwable) exception);
  }

  public Message(AbstractRuntimeException exception) {

    this.partial = exception.getCode() != null ? exception.getCode().name() : null;
    this.code = exception.getCodeValue();
    this.message = getExceptionMessage(exception);
    this.type = exception.getClass().getTypeName();
    this.detail = exception.getMessage();

    if (exception instanceof WrappedRuntimeException) {
      this.wrappedInRuntimeException = true;
      WrappedRuntimeException wrappedException = (WrappedRuntimeException) exception;
      init(wrappedException.getCause());
    }
  }

  @JsonCreator
  public static Message from(
      @JsonProperty("wrappedInRuntimeException") boolean wrappedInRuntimeException,
      @JsonProperty("type") String type,
      @JsonProperty("code") String code,
      @JsonProperty("message") String message,
      @JsonProperty("detail") String detail) {
    return new Message(wrappedInRuntimeException, type, code, message, detail);
  }

  private void init(Throwable exception) {
    if (exception != null) {
      this.type = exception.getClass().getTypeName();
      if (exception instanceof AdException) {
        AdException adException = (AdException) exception;
        this.partial = adException.getCode().name();
        this.code = adException.getCodeValue();
        this.message = getExceptionMessage(exception);
        this.detail = exception.getMessage();
      }
    }
  }

  public boolean isWrappedInRuntimeException() {
    return wrappedInRuntimeException;
  }

  public String getType() {
    return type;
  }

  public String getPartial() {
    return partial;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public String getDetail() {
    return detail;
  }

  private String getExceptionMessage(Throwable throwable) {
    String localizedMessage = throwable.getLocalizedMessage();
    if (Strings.isNullOrEmpty(localizedMessage)) {
      Throwable cause = throwable.getCause();
      if (cause != null) {
        return getExceptionMessage(cause);
      }
    }

    return localizedMessage;
  }
}
