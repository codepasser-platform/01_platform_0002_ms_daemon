package org.codepasser.common.web.configuration.security.handler;

import org.codepasser.common.web.exception.AccessException;
import org.codepasser.common.web.exception.RepresentationMessage;
import org.codepasser.common.web.processor.HttpEntityProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.util.StringUtils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * RedirectAccessDeniedHandler.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/20 : base version.
 */
public class RedirectAccessDeniedHandler implements AccessDeniedHandler {
  @Autowired private HttpEntityProcessor httpEntityProcessor;

  public void handle(
      HttpServletRequest request,
      HttpServletResponse response,
      AccessDeniedException accessDeniedException)
      throws IOException, ServletException {
    if (!response.isCommitted()) {

      String accept = request.getHeader("Accept");
      String contentType = request.getHeader("Content-Type");
      if (!StringUtils.isEmpty(accept)
          && !StringUtils.isEmpty(contentType)
          && (accept.contains(APPLICATION_JSON_VALUE)
              || contentType.contains(APPLICATION_JSON_VALUE))) {
        // Put exception into request scope (perhaps of use to a view)
        request.setAttribute(WebAttributes.ACCESS_DENIED_403, accessDeniedException);
        ResponseEntity<RepresentationMessage> entity =
            ResponseEntity.status(FORBIDDEN)
                .contentType(APPLICATION_JSON)
                .body(new RepresentationMessage(new AccessException(accessDeniedException)));

        httpEntityProcessor.writeEntity(entity, response);
        return;
      }

      String _errorPage = "/error/403";
      if (!StringUtils.isEmpty(request.getContextPath()) && !"/".equals(request.getContextPath())) {
        _errorPage = request.getContextPath() + _errorPage;
      }
      response.sendRedirect(_errorPage);
    }
  }
}
