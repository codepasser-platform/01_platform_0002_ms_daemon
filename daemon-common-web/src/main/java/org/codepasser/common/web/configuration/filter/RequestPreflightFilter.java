package org.codepasser.common.web.configuration.filter;

import org.codepasser.common.processor.annotation.InjectLogger;
import org.codepasser.common.web.configuration.http.DaemonRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import static org.codepasser.common.utils.RequestUtils.getArguments;

/**
 * RequestPreflightFilter.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2020/7/9 : base version.
 */
public class RequestPreflightFilter implements Filter {

  @InjectLogger private Logger logger;

  private static final String REQUEST_ID = "request_id";
  private static final String X_FORWARDED_HEADER = "X-Forwarded-For";
  private final List<String> excludePatterns = new ArrayList<>();

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {}

  @Override
  public void destroy() {}

  @Override
  public void doFilter(
      ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    String path = request.getServletPath();
    // Exclude path
    if (isExclude(path)) {
      filterChain.doFilter(servletRequest, servletResponse);
      return;
    }
    // Multipart form with file upload
    String contentType = request.getHeader("Content-Type");
    if (!StringUtils.isEmpty(contentType) && contentType.startsWith("multipart/form-data")) {
      filterChain.doFilter(servletRequest, servletResponse);
      return;
    }
    String requestId = UUID.randomUUID().toString().replaceAll("-", "");
    MDC.put(REQUEST_ID, requestId);
    String proxyForwarded = request.getHeader(X_FORWARDED_HEADER);
    String remoteIp = request.getRemoteAddr();
    logger.info(
        "{\"remote_ip\":\"{}\", \"x_forwarded_for\":\"{}\", \"url\":\"{}\", \"method\":\"{}\", \"query\":\"{}\"}",
        remoteIp,
        proxyForwarded,
        request.getRequestURL(),
        request.getMethod(),
        getArguments(request));

    DaemonRequestWrapper daemonRequestWrapper = new DaemonRequestWrapper(request);
    filterChain.doFilter(daemonRequestWrapper, servletResponse);
    MDC.remove(REQUEST_ID);
  }

  public RequestPreflightFilter excludePathPatterns(String... patterns) {
    return this.excludePathPatterns(Arrays.asList(patterns));
  }

  public RequestPreflightFilter excludePathPatterns(List<String> patterns) {
    this.excludePatterns.addAll(patterns);
    return this;
  }

  private boolean isExclude(String path) {
    AntPathMatcher pathMatcher = new AntPathMatcher();
    for (String pattern : excludePatterns) {
      if (pathMatcher.match(pattern, path)) {
        return true;
      }
    }
    return false;
  }
}
