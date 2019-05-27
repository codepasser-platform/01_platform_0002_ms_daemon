package org.codepasser.common.exception;

/**
 * AdException.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/6 : base version.
 */
public interface AdException {
  String getCodeValue();

  String getMessage();

  String getLocalizedMessage();

  <C extends Enum<C>> C getCode();
}
