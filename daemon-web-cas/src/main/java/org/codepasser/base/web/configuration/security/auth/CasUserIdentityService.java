package org.codepasser.base.web.configuration.security.auth;

import org.codepasser.common.model.entity.inner.UserType;
import org.codepasser.common.model.security.Authority;
import org.codepasser.common.model.security.UserBasic;
import org.codepasser.common.service.AuthorizationService;
import org.codepasser.common.web.configuration.security.auth.UserIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.EnumSet;
import java.util.Optional;

import static java.lang.String.format;

@Component("cas")
public class CasUserIdentityService implements UserDetailsService {

  @Autowired private AuthorizationService authorizationService;

  @Override
  public UserIdentity loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<UserBasic> foundUser = Optional.empty();
    //    TODO sample admin user
    if ("casuser".equals(username)) {
      username = "admin";
    }
    foundUser = authorizationService.loadUserByUsername(username);
    // Create anonymous user when if you want users to have no access to 403.
    if (!foundUser.isPresent()) {
      UserBasic anonymous = new UserBasic();
      anonymous.setLocked(false);
      anonymous.setAuthorities(EnumSet.noneOf(Authority.Role.class));
      anonymous.setType(UserType.EXTERNAL);
      foundUser = Optional.of(anonymous);
    }
    if (!foundUser.isPresent()) {
      throw new UsernameNotFoundException(format("User [%s] not found.", username));
    }
    return new UserIdentity(foundUser.get());
  }
}
