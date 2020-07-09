package org.codepasser.common.service.conifguration.interceptor;

import org.codepasser.common.processor.annotation.InjectLogger;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

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

  private static final String REQUEST_ID = "request_id";
  private static final String X_FORWARDED_HEADER = "X-Forwarded-For";

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    String xForwardedForHeader = request.getHeader(X_FORWARDED_HEADER);
    String remoteIp = request.getRemoteAddr();
    String requestId = UUID.randomUUID().toString().replaceAll("-", "");
    MDC.put(REQUEST_ID, requestId);
    logger.info(
        "{\"remote_ip\":\"{}\", \"x_forwarded_for\":\"{}\", \"request_url\":\"{}\", \"request_args\": {}}",
        remoteIp,
        xForwardedForHeader,
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
    MDC.remove(REQUEST_ID);
  }

  @Override
  public void afterCompletion(
      HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
      throws Exception {}
}
