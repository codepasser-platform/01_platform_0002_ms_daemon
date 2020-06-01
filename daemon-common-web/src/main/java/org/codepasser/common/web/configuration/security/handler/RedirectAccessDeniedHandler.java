package org.codepasser.common.web.configuration.security.handler;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.codepasser.common.web.exception.AccessException;
import org.codepasser.common.web.exception.RepresentationMessage;
import org.codepasser.common.web.processor.HttpEntityProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.access.AccessDeniedHandler;

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
      // Put exception into request scope (perhaps of use to a view)
      request.setAttribute(WebAttributes.ACCESS_DENIED_403, accessDeniedException);

      ResponseEntity<RepresentationMessage> entity =
          ResponseEntity.status(FORBIDDEN)
              .contentType(APPLICATION_JSON)
              .body(new RepresentationMessage(new AccessException(accessDeniedException)));

      httpEntityProcessor.writeEntity(entity, response);
    }
  }
}
