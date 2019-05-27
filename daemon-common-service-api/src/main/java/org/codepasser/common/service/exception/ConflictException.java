package org.codepasser.common.service.exception;

import org.codepasser.common.exception.Message;

/**
 * ConflictException.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
public class ConflictException extends ServiceRuntimeException {

  private static final long serialVersionUID = 3837761502401319670L;

  public ConflictException(Message message) {
    super(message);
    setCode(Error.valueOf(message.getPartial()));
  }

  public ConflictException(Error error) {
    super(error);
  }

  @Override
  protected String reasonCode() {
    return "CONFLICT";
  }

  public enum Error {
    USER_USERNAME,
    USER_PHONE,
    USER_EMAIL,
  }
}
