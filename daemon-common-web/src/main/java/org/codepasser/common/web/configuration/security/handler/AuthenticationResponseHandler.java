package org.codepasser.common.web.configuration.security.handler;

import org.springframework.security.core.AuthenticationException;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * AuthenticationResponseHandler.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/20 : base version.
 */
public interface AuthenticationResponseHandler {
  void handle(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException;
}
