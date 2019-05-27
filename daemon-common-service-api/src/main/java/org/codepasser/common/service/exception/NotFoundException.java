package org.codepasser.common.service.exception;

import org.codepasser.common.exception.Message;
/**
 * NotFoundException.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
public class NotFoundException extends ServiceRuntimeException {

  private static final long serialVersionUID = -2909833311527921881L;

  public NotFoundException(Message message) {
    super(message);
    setCode(Error.valueOf(message.getPartial()));
  }

  public NotFoundException(Error error) {
    super(error);
  }

  @Override
  protected String reasonCode() {
    return "NOT_FOUND";
  }

  public enum Error {
    DATA,
    AREA,
    ORG,
    USER
  }
}
