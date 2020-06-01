package org.codepasser.base.web.api;

import org.codepasser.common.model.security.UserSelf;
import org.codepasser.common.web.configuration.security.auth.UserIdentity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api")
public class UserSecurity {

  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @RequestMapping(value = "/admin", produces = APPLICATION_JSON_VALUE)
  public UserSelf roleAdmin(@AuthenticationPrincipal UserIdentity user) {
    UserSelf userSelf = new UserSelf();
    if (user != null && user.getUser() != null) {
      userSelf.from(user.getUser());
    }
    return userSelf;
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
  @RequestMapping(value = "/user", produces = APPLICATION_JSON_VALUE)
  public UserSelf roleUser(@AuthenticationPrincipal UserIdentity user) {
    UserSelf userSelf = new UserSelf();
    if (user != null && user.getUser() != null) {
      userSelf.from(user.getUser());
    }
    return userSelf;
  }

  @PreAuthorize("hasAnyRole('ROLE_CLIENT')")
  @RequestMapping(value = "/client", produces = APPLICATION_JSON_VALUE)
  public UserSelf roleClient(@AuthenticationPrincipal UserIdentity user) {
    UserSelf userSelf = new UserSelf();
    if (user != null && user.getUser() != null) {
      userSelf.from(user.getUser());
    }
    return userSelf;
  }

  @RequestMapping(value = "/user1", produces = APPLICATION_JSON_VALUE)
  public UserSelf roleUser1(@AuthenticationPrincipal UserIdentity user) {
    UserSelf userSelf = new UserSelf();
    if (user != null && user.getUser() != null) {
      userSelf.from(user.getUser());
    }
    return userSelf;
  }

  @PreAuthorize("hasAnyRole('ROLE_USER')")
  @RequestMapping(value = "/user2", produces = APPLICATION_JSON_VALUE)
  public UserSelf roleUser2(@AuthenticationPrincipal UserIdentity user) {
    UserSelf userSelf = new UserSelf();
    if (user != null && user.getUser() != null) {
      userSelf.from(user.getUser());
    }
    return userSelf;
  }
}
