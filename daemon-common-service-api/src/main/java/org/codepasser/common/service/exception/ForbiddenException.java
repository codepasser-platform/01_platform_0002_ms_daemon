package org.codepasser.common.service.exception;

import org.codepasser.common.exception.Message;
/**
 * ForbiddenException.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
public class ForbiddenException extends ServiceRuntimeException {

  private static final long serialVersionUID = 2399610140036369185L;

  public ForbiddenException(Message message) {
    super(message);
    setCode(Error.valueOf(message.getPartial()));
  }

  public ForbiddenException(Error error) {
    super(error);
  }

  @Override
  protected String reasonCode() {
    return "FORBIDDEN";
  }

  public enum Error {
    PERMISSIONS
  }
}
