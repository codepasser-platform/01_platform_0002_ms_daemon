package org.codepasser.common.web.exception;

import java.io.Serializable;
import org.codepasser.common.exception.AbstractException;
import org.codepasser.common.exception.AbstractRuntimeException;
import org.codepasser.common.exception.AdException;
import org.codepasser.common.exception.Message;
import org.springframework.security.core.AuthenticationException;

/**
 * RepresentationMessage.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
public class RepresentationMessage implements Serializable {

  private static final long serialVersionUID = -6386495208759109800L;
  private Message message;

  public RepresentationMessage(AdException exception) {
    this.message = new Message(exception);
  }

  //  Spring security
  public static RepresentationMessage from(AuthenticationException exception) {
    Throwable cause = exception.getCause();
    if (cause instanceof AbstractException) {
      return new RepresentationMessage((AbstractException) cause);
    }

    if (cause instanceof AbstractRuntimeException) {
      return new RepresentationMessage((AbstractRuntimeException) cause);
    }

    return new RepresentationMessage(new AccessException(exception));
  }

  public String getCode() {
    return message.getCode();
  }

  public String getMessage() {
    return message.getMessage();
  }

  public String getDetail() {
    return message.getDetail();
  }
}
