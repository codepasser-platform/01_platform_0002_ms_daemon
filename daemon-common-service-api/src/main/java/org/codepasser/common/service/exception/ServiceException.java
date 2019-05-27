package org.codepasser.common.service.exception;

import static java.lang.String.format;

import org.codepasser.common.exception.AbstractException;
import org.codepasser.common.exception.Message;
/**
 * ServiceException.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
public abstract class ServiceException extends AbstractException {

  private static final long serialVersionUID = -6045759006673606027L;

  public ServiceException(Message message) {
    super(message);
  }

  public ServiceException(Enum<?> code) {
    super(code);
  }

  public ServiceException(Enum<?> code, Throwable cause) {
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
