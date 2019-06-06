package org.codepasser.base.web.api;

import org.codepasser.common.model.security.UserSelf;
import org.codepasser.common.web.configuration.security.auth.UserIdentity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth")
public class UserEndpoint {

  @RequestMapping("/me")
  public UserSelf me(@AuthenticationPrincipal UserIdentity user) {
    UserSelf userSelf = new UserSelf();
    if (user != null && user.getUser() != null) {
      userSelf.from(user.getUser());
    }
    return userSelf;
  }
}
