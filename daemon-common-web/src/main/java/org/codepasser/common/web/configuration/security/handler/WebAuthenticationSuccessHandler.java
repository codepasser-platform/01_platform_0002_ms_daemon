package org.codepasser.common.web.configuration.security.handler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.codepasser.common.exception.AbstractRuntimeException;
import org.codepasser.common.model.security.Authority;
import org.codepasser.common.model.security.UserTokenSession;
import org.codepasser.common.web.configuration.security.auth.UserIdentity;
import org.codepasser.common.web.exception.RepresentationMessage;
import org.codepasser.common.web.processor.HttpEntityProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class WebAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler
    implements AjaxAuthenticationHandler<WebAuthenticationSuccessHandler> {

  @Autowired private HttpEntityProcessor httpEntityProcessor;

  private String ajaxParam = DEFAULT_AJAX_PARAM;

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws ServletException, IOException {
    try {
      UserIdentity userDetails = (UserIdentity) authentication.getPrincipal();
      // Successful login and subsequent operation reservation

      if (!Boolean.valueOf(request.getParameter(ajaxParam))) {
        // If. redirect previous url
        super.onAuthenticationSuccess(request, response, authentication);
        return;
      }
      String sessionId = request.getSession().getId();
      // Else. write user info into response.
      ResponseEntity<UserTokenSession> entity =
          ResponseEntity.status(OK)
              .contentType(APPLICATION_JSON)
              .body(new UserTokenSession(Authority.Session.AUTHORIZED, sessionId));
      httpEntityProcessor.writeEntity(entity, response);
    } catch (AbstractRuntimeException e) {
      ResponseEntity<RepresentationMessage> entity =
          ResponseEntity.status(INTERNAL_SERVER_ERROR)
              .contentType(APPLICATION_JSON)
              .body(new RepresentationMessage(e));
      httpEntityProcessor.writeEntity(entity, response);
    }
  }

  public WebAuthenticationSuccessHandler ajaxParam(String name) {
    this.ajaxParam = name;
    return this;
  }

  public WebAuthenticationSuccessHandler targetUrlParam(String name) {
    super.setTargetUrlParameter(name);
    return this;
  }
}
