package org.codepasser.common.web.configuration.interceptor;

import static org.codepasser.common.utils.RequestUtils.getArguments;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.codepasser.common.processor.annotation.InjectLogger;
import org.slf4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

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
    logger.info(
        "User behavior interceptor>pre handler url '{}', arguments=[{}]",
        request.getRequestURL(),
        getArguments(request));
    return true;
  }

  @Override
  public void postHandle(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler,
      ModelAndView modelAndView)
      throws Exception {
    logger.debug(
        "User behavior interceptor>post handler url '{}', arguments=[{}]",
        request.getRequestURL(),
        getArguments(request));
  }

  @Override
  public void afterCompletion(
      HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
      throws Exception {
    logger.debug(
        "User behavior interceptor>after completion url '{}', arguments=[{}]",
        request.getRequestURL(),
        getArguments(request));
  }
}
