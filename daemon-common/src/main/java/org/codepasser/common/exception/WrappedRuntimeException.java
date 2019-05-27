package org.codepasser.common.exception;

import com.google.common.base.Strings;

/**
 * WrappedRuntimeException.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
public class WrappedRuntimeException extends AbstractRuntimeException {

  private static final long serialVersionUID = -2610815130918289992L;
  private AdException exception;
  private Message message;

  public WrappedRuntimeException(Message message) {
    super(message);
    this.message = message;
    if (!Strings.isNullOrEmpty(message.getPartial())) {
      setCode(Error.valueOf(message.getPartial()));
    }
  }

  public WrappedRuntimeException(RuntimeException exception) {
    super(Error.RUNTIME, exception);
  }

  public WrappedRuntimeException(AdException exception) {
    this.exception = exception;
  }

  public WrappedRuntimeException(AbstractException exception) {
    this.exception = exception;
  }

  @Override
  public String getMessage() {
    return this.exception != null
        ? this.exception.getMessage()
        : (this.message != null ? this.message.getDetail() : super.getMessage());
  }

  @Override
  public String getLocalizedMessage() {
    return this.exception != null
        ? this.exception.getLocalizedMessage()
        : super.getLocalizedMessage();
  }

  @Override
  public <C extends Enum<C>> C getCode() {
    return this.exception != null ? this.exception.getCode() : super.getCode();
  }

  @Override
  public String getCodeValue() {
    return this.exception != null ? this.exception.getCodeValue() : super.getCodeValue();
  }

  @Override
  public synchronized Throwable getCause() {
    return this.exception != null ? (Throwable) exception : super.getCause();
  }

  @Override
  protected String messageResourceBaseName() {
    return "exception.service";
  }

  @Override
  protected String moduleName() {
    return "RT";
  }

  public enum Error {
    RUNTIME
  }
}
