package org.codepasser.common.web.configuration.security.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * AuthenticationResponseEntryPoint.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/20 : base version.
 */
public class AuthenticationResponseEntryPoint extends LoginUrlAuthenticationEntryPoint {

  private AuthenticationResponseRegister authenticationResponseRegister;

  /**
   * AuthenticationResponseEntryPoint.
   *
   * @param loginFormUrl URL where the login page can be found. Should either be relative to the
   *     web-app context path (include a leading {@code /}) or an absolute URL.
   */
  public AuthenticationResponseEntryPoint(String loginFormUrl) {
    super(loginFormUrl);
  }

  public AuthenticationResponseRegister apiMatcher(RequestMatcher matcher) {
    authenticationResponseRegister = new AuthenticationResponseRegister(matcher);
    return authenticationResponseRegister;
  }

  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException, ServletException {
    if (authenticationResponseRegister != null
        && authenticationResponseRegister.getMatcher().matches(request)) {
      AuthenticationResponseHandler authenticationResponseHandler =
          authenticationResponseRegister.getAuthenticationResponseHandler();
      authenticationResponseHandler.handle(request, response, authException);
    } else {
      super.commence(request, response, authException);
    }
  }

  public class AuthenticationResponseRegister {

    private RequestMatcher matcher;
    private AuthenticationResponseHandler authenticationResponseHandler;

    private AuthenticationResponseRegister(RequestMatcher matcher) {
      this.matcher = matcher;
    }

    public AuthenticationResponseEntryPoint authenticationResponseHandler(
        AuthenticationResponseHandler responseHandler) {
      this.authenticationResponseHandler = responseHandler;
      return AuthenticationResponseEntryPoint.this;
    }

    private RequestMatcher getMatcher() {
      return matcher;
    }

    private AuthenticationResponseHandler getAuthenticationResponseHandler() {
      return authenticationResponseHandler;
    }
  }
}
