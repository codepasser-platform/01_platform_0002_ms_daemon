package org.codepasser.common.service.exception;

import org.codepasser.common.exception.Message;

/**
 * IllegalArgumentException.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
public class IllegalArgumentException extends ServiceRuntimeException {

  private static final long serialVersionUID = -6299563608663320053L;

  public IllegalArgumentException(Message message) {
    super(message);
    setCode(Error.valueOf(message.getPartial()));
  }

  public IllegalArgumentException(Error error) {
    super(error);
  }

  @Override
  protected String reasonCode() {
    return "ARG";
  }

  public enum Error {
    INVALID_IDENTIFYING_CODE
  }
}
