package org.codepasser.common.web.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieHttpSessionIdResolver;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.google.common.base.Strings.isNullOrEmpty;
import static org.codepasser.common.web.configuration.security.session.SessionInterface.SESSION_AUTH_COOKIE_NAME;
import static org.codepasser.common.web.configuration.security.session.SessionInterface.SESSION_AUTH_TOKEN_NAME;
import static org.codepasser.common.web.configuration.security.session.SessionInterface.SESSION_KEY_PREFIX;
import static org.codepasser.common.web.configuration.security.session.SessionInterface.SESSION_MAX_INACTIVE_INTERVAL;
import static org.springframework.session.FlushMode.ON_SAVE;

/**
 * HttpSessionConfiguration.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/20 : base version.
 */
@Configuration
@EnableRedisHttpSession(
    maxInactiveIntervalInSeconds = SESSION_MAX_INACTIVE_INTERVAL,
    redisNamespace = SESSION_KEY_PREFIX,
    flushMode = ON_SAVE)
public class HttpSessionConfiguration {

  @Bean
  public HttpSessionIdResolver httpSessionStrategy(WebMvcConfiguration.WebSettings webSettings) {
    return new WebSessionIdResolver(
        webSettings.getCookieDomainNamePattern(), webSettings.getCookiePath());
  }

  @SuppressWarnings("WeakerAccess")
  public static final class WebSessionIdResolver implements HttpSessionIdResolver {

    private HeaderHttpSessionIdResolver headerHttpSessionIdResolver;
    private CookieHttpSessionIdResolver cookieHttpSessionIdResolver;

    public WebSessionIdResolver(String cookieDomainNamePattern, String cookiePath) {
      this.headerHttpSessionIdResolver = new HeaderHttpSessionIdResolver(SESSION_AUTH_TOKEN_NAME);
      this.cookieHttpSessionIdResolver = new CookieHttpSessionIdResolver();
      DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
      cookieSerializer.setCookieName(SESSION_AUTH_COOKIE_NAME);
      if (!isNullOrEmpty(cookieDomainNamePattern)) {
        cookieSerializer.setDomainNamePattern(cookieDomainNamePattern);
      }
      if (!isNullOrEmpty(cookiePath)) {
        cookieSerializer.setCookiePath(cookiePath);
      }
      this.cookieHttpSessionIdResolver.setCookieSerializer(cookieSerializer);
    }

    @Override
    public List<String> resolveSessionIds(HttpServletRequest httpServletRequest) {

      List<String> matchingHeaderValues =
          this.headerHttpSessionIdResolver.resolveSessionIds(httpServletRequest);

      if (!matchingHeaderValues.isEmpty()) {
        return matchingHeaderValues;
      }

      List<String> matchingCookieValues =
          this.cookieHttpSessionIdResolver.resolveSessionIds(httpServletRequest);
      if (!matchingCookieValues.isEmpty()) {
        return matchingCookieValues;
      }

      return Collections.emptyList();
    }

    @Override
    public void setSessionId(
        HttpServletRequest httpServletRequest,
        HttpServletResponse httpServletResponse,
        String sessionId) {
      this.headerHttpSessionIdResolver.setSessionId(
          httpServletRequest, httpServletResponse, sessionId);
      this.cookieHttpSessionIdResolver.setSessionId(
          httpServletRequest, httpServletResponse, sessionId);
    }

    @Override
    public void expireSession(
        HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
      this.headerHttpSessionIdResolver.expireSession(httpServletRequest, httpServletResponse);
      this.cookieHttpSessionIdResolver.expireSession(httpServletRequest, httpServletResponse);
    }
  }
}
