package org.codepasser.common.web.configuration.security.handler;

import static org.codepasser.common.web.configuration.security.handler.AjaxAuthenticationHandler.DEFAULT_AJAX_PARAM;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.codepasser.common.model.security.Authority;
import org.codepasser.common.model.security.UserSession;
import org.codepasser.common.web.processor.HttpEntityProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

public class WebLogoutSuccessHandler extends AbstractAuthenticationTargetUrlRequestHandler
    implements LogoutSuccessHandler {

  @Autowired private HttpEntityProcessor httpEntityProcessor;

  public void onLogoutSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {

    if (!Boolean.valueOf(request.getParameter(DEFAULT_AJAX_PARAM))) {
      super.handle(request, response, authentication);
      return;
    }

    ResponseEntity<UserSession> entity =
        ResponseEntity.status(OK)
            .contentType(APPLICATION_JSON)
            .body(new UserSession(Authority.Session.ANONYMOUS));
    httpEntityProcessor.writeEntity(entity, response);
  }
}
