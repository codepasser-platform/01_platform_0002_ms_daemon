package org.codepasser.common.web.basement;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.codepasser.common.model.security.UserSelf;
import org.codepasser.common.service.response.AssertResponse;
import org.codepasser.common.web.configuration.security.auth.UserIdentity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SessionAuthenticatedApi.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2018/8/13 : base version.
 */
@RestController
@RequestMapping("/rest/session")
public class SessionAuthenticatedApi {

  @PreAuthorize("isAuthenticated()")
  @RequestMapping(value = "/me", method = GET, produces = APPLICATION_JSON_VALUE)
  public UserSelf me(@AuthenticationPrincipal UserIdentity user) {
    UserSelf userSelf = new UserSelf();
    if (user != null && user.getUser() != null) {
      userSelf.from(user.getUser());
    }
    return userSelf;
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @RequestMapping(value = "/admin", method = GET, produces = APPLICATION_JSON_VALUE)
  public AssertResponse roleAdmin(@AuthenticationPrincipal UserIdentity user) {
    return AssertResponse.success();
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
  @RequestMapping(value = "/user", method = GET, produces = APPLICATION_JSON_VALUE)
  public AssertResponse roleUser(@AuthenticationPrincipal UserIdentity user) {
    return AssertResponse.success();
  }
}
