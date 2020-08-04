package org.codepasser.common.service.exception;

import org.codepasser.common.exception.AbstractRuntimeException;
import org.codepasser.common.exception.Message;

import static java.lang.String.format;

/**
 * ServiceRuntimeException.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
public abstract class ServiceRuntimeException extends AbstractRuntimeException {

  private static final long serialVersionUID = -6045759006673606027L;

  public ServiceRuntimeException(Message message) {
    super(message);
  }

  public ServiceRuntimeException(Enum<?> code) {
    super(code);
  }

  public ServiceRuntimeException(Enum<?> code, Throwable cause) {
    super(code, cause);
  }

  @Override
  protected final String messageResourceBaseName() {
    return "exception.service";
  }

  @Override
  protected final String moduleName() {
    return format("SRV_%s", reasonCode());
  }

  protected abstract String reasonCode();
}
