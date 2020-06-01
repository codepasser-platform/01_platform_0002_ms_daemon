package org.codepasser.common.web.configuration.security.handler;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.codepasser.common.web.exception.RepresentationMessage;
import org.codepasser.common.web.processor.HttpEntityProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;

public class ApiAuthenticationResponseHandler implements AuthenticationResponseHandler {

  @Autowired private HttpEntityProcessor httpEntityProcessor;

  @Override
  public void handle(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException {
    ResponseEntity<RepresentationMessage> entity =
        ResponseEntity.status(FORBIDDEN)
            .contentType(APPLICATION_JSON)
            .body(RepresentationMessage.from(authException));
    httpEntityProcessor.writeEntity(entity, response);
  }
}
