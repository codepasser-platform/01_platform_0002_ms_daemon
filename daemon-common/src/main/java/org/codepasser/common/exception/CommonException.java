package org.codepasser.common.exception;

/**
 * CommonException.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
public class CommonException extends AbstractException {

  private static final long serialVersionUID = -7723730312449531947L;

  public CommonException(Message message) {
    super(message);
    setCode(Error.valueOf(message.getPartial()));
  }

  public CommonException(Enum<?> code) {
    super(code);
  }

  public CommonException(Enum<?> code, Throwable cause) {
    super(code, cause);
  }

  public CommonException(Error error) {
    super(error);
  }

  @Override
  protected String messageResourceBaseName() {
    return "exception.common";
  }

  @Override
  protected String moduleName() {
    return "COM";
  }

  public enum Error {
    QR_CODE_GENERATE_FAILED,
    IDENTIFYING_CODE_CYCLE_ERROR,
  }
}
