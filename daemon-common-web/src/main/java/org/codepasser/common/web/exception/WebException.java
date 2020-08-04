package org.codepasser.common.web.exception;

import org.codepasser.common.exception.AbstractException;

import static org.codepasser.common.web.exception.WebException.Error.RUNTIME_ERROR;

/**
 * WebException.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
public class WebException extends AbstractException {

  private static final long serialVersionUID = -460735736684833105L;

  public WebException(Error error, String message) {
    super(error, message);
  }

  public WebException(Error error, Throwable cause) {
    super(error, cause);
  }

  public WebException(Throwable cause) {
    super(RUNTIME_ERROR, cause);
  }

  public WebException(RuntimeException exception) {
    super(RUNTIME_ERROR, exception);
  }

  @Override
  protected String messageResourceBaseName() {
    return "exception.web";
  }

  @Override
  protected String moduleName() {
    return "WEB";
  }

  public enum Error {
    RUNTIME_ERROR
  }
}
