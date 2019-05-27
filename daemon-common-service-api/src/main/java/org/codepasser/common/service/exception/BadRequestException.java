package org.codepasser.common.service.exception;

import static org.codepasser.common.service.exception.BadRequestException.Error.FAILED;

import org.codepasser.common.exception.AbstractRuntimeException;
import org.codepasser.common.exception.Message;
import org.springframework.http.converter.HttpMessageNotReadableException;
/**
 * BadRequestException.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
public class BadRequestException extends AbstractRuntimeException {

  private static final long serialVersionUID = 5388350327248338539L;

  public BadRequestException(Message message) {
    super(message);
    setCode(Error.valueOf(message.getPartial()));
  }

  public BadRequestException(Error error) {
    super(error);
  }

  public BadRequestException(IllegalStateException exception) {
    super(FAILED, exception.getMessage());
  }

  public BadRequestException(HttpMessageNotReadableException exception) {
    super(FAILED, exception.getMessage());
  }

  @Override
  protected String messageResourceBaseName() {
    return "exception.service";
  }

  @Override
  protected String moduleName() {
    return "SRV_BAD_REQ";
  }

  public enum Error {
    FAILED
  }
}
