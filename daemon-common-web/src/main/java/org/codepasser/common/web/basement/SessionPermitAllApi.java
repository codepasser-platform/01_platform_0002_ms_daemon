package org.codepasser.common.web.basement;

import org.codepasser.common.model.security.Authority;
import org.codepasser.common.model.security.UserSession;
import org.codepasser.common.service.exception.ServiceException;
import org.codepasser.common.web.configuration.security.auth.UserIdentity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nonnull;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * SessionPermitAllApi.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/13 : base version.
 */
@RestController
@RequestMapping("/master/session")
public class SessionPermitAllApi {

  @PreAuthorize("permitAll")
  @Nonnull
  @RequestMapping(value = "/status", method = GET, produces = APPLICATION_JSON_VALUE)
  public UserSession status(@AuthenticationPrincipal UserIdentity user) throws ServiceException {
    if (user != null && user.getUser() != null) {
      return new UserSession(Authority.Session.AUTHORIZED);
    }
    return new UserSession(Authority.Session.ANONYMOUS);
  }
}
