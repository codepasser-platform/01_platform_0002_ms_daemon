package org.codepasser.common.service.exception;

import org.codepasser.common.exception.Message;
/**
 * IllegalTermsException.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
public class IllegalTermsException extends ServiceRuntimeException {

  public IllegalTermsException(Message message) {
    super(message);
    setCode(Error.valueOf(message.getPartial()));
  }

  public IllegalTermsException(Error error) {
    super(error);
  }

  @Override
  protected String reasonCode() {
    return "TERMS";
  }

  public enum Error {
    IDENTIFYING_CODE,
    RESOURCE_OCCUPIED,
    RESOURCE_INCORRECT
  }
}
