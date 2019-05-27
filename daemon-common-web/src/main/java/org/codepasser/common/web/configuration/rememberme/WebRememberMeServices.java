package org.codepasser.common.web.configuration.rememberme;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.lang.reflect.Method;
import javax.annotation.Nullable;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.util.ReflectionUtils;

public class WebRememberMeServices extends PersistentTokenBasedRememberMeServices {

  protected static final int TOKEN_VALIDITY_ONE_YEAR_SECONDS = 365 * 24 * 60 * 60;

  protected static final int TOKEN_VALIDITY_ONE_WEEK_SECONDS = 7 * 24 * 60 * 60;

  private final Method setHttpOnlyMethod;
  private String cookieDomain;
  private int cookieValidity = TOKEN_VALIDITY_ONE_WEEK_SECONDS;

  public WebRememberMeServices(
      String key,
      UserDetailsService userDetailsService,
      PersistentTokenRepository tokenRepository,
      @Nullable String cookieDomain) {
    super(key, userDetailsService, tokenRepository);
    this.setCookieName(key);
    this.setHttpOnlyMethod = ReflectionUtils.findMethod(Cookie.class, "setHttpOnly", boolean.class);
    this.cookieDomain = cookieDomain;
  }

  @Override
  protected void setCookie(
      String[] tokens, int maxAge, HttpServletRequest request, HttpServletResponse response) {
    String cookieValue = this.encodeCookie(tokens);
    Cookie cookie = new Cookie(this.getCookieName(), cookieValue);
    cookie.setMaxAge(this.cookieValidity);
    cookie.setPath(this.getCookiePath(request));

    // TODO SSO security domain
    if (!isNullOrEmpty(this.cookieDomain)) {
      // cookie.setDomain(this.cookieDomain);
    }
    if (maxAge < 1) {
      cookie.setVersion(1);
    }

    if (this.setHttpOnlyMethod != null) {
      ReflectionUtils.invokeMethod(this.setHttpOnlyMethod, cookie, Boolean.TRUE);
    } else if (this.logger.isDebugEnabled()) {
      this.logger.debug(
          "Note: Cookie will not be marked as HttpOnly because you are not using Servlet 3.0 (Cookie#setHttpOnly(boolean) was not found).");
    }

    response.addCookie(cookie);
  }

  private String getCookiePath(HttpServletRequest request) {
    String contextPath = request.getContextPath();
    return contextPath.length() > 0 ? contextPath : "/";
  }
}
