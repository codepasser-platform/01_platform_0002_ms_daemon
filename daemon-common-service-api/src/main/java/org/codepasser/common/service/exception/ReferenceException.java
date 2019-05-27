package org.codepasser.common.service.exception;

import org.codepasser.common.exception.Message;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
/**
 * ReferenceException.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
public class ReferenceException extends ServiceRuntimeException {

  private static final long serialVersionUID = -1196230020798256751L;

  public ReferenceException(Message message) {
    super(message);
    setCode(Error.valueOf(message.getPartial()));
  }

  public ReferenceException(JpaObjectRetrievalFailureException e) {
    super(Error.FAILED, e);
  }

  public ReferenceException(Error error) {
    super(error);
  }

  // WEB_VALIDATE_REFERENCE_FAILED
  @Override
  protected String reasonCode() {
    return "REFERENCE";
  }

  public enum Error {
    FAILED
  }
}
