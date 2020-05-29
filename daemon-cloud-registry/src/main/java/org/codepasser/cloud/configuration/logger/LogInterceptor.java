package org.codepasser.cloud.configuration.logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.PropertyNamingStrategy.SNAKE_CASE;

/**
 * LogInterceptor.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2020/5/28 : base version.
 */
public class LogInterceptor implements HandlerInterceptor {

  private static final String REQUEST_ID = "request_id";
  private static final String REMOTE_IP = "remote_ip";
  private static final String X_FORWARDED_HEADER = "X-Forwarded-For";
  private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);
  private static final ObjectMapper objectMapper = new ObjectMapper();

  static {
    objectMapper.setSerializationInclusion(NON_NULL);
    objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
    objectMapper.setPropertyNamingStrategy(SNAKE_CASE);
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o)
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
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      Object o,
      ModelAndView modelAndView)
      throws Exception {
    MDC.remove(REQUEST_ID);
  }

  @Override
  public void afterCompletion(
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      Object o,
      Exception e)
      throws Exception {}

  private String getArguments(HttpServletRequest request) {
    String arguments = null;
    try {
      arguments = objectMapper.writeValueAsString(request.getParameterMap());
    } catch (Exception ex) {
      // watch ignore
    }
    return arguments;
  }
}
