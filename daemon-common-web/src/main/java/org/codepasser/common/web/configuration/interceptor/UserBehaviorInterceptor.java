package org.codepasser.common.web.configuration.interceptor;

import org.codepasser.common.processor.annotation.InjectLogger;
import org.codepasser.common.web.configuration.http.DaemonRequestWrapper;
import org.slf4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.codepasser.common.utils.RequestUtils.getArguments;

/**
 * UserBehaviorInterceptor.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2019/3/19 : base version.
 */
public class UserBehaviorInterceptor implements HandlerInterceptor {

  @InjectLogger private Logger logger;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    if (request instanceof DaemonRequestWrapper) {
      logger.info(
          "{\"uri\":\"{}\", \"method\":\"{}\", \"query\":\"{}\", \"body\":\"{}\"}",
          request.getRequestURI(),
          request.getMethod(),
          getArguments(request),
          ((DaemonRequestWrapper) request).getBody());
    } else {
      logger.info(
          "{\"uri\":\"{}\", \"method\":\"{}\", \"query\":\"{}\"}",
          request.getRequestURI(),
          request.getMethod(),
          getArguments(request));
    }
    return true;
  }

  @Override
  public void postHandle(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler,
      ModelAndView modelAndView)
      throws Exception {}

  @Override
  public void afterCompletion(
      HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
      throws Exception {}
}
