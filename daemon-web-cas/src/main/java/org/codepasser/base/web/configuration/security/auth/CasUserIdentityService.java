package org.codepasser.base.web.configuration.security.auth;

import static java.lang.String.format;

import java.util.Optional;
import org.codepasser.common.model.security.UserBasic;
import org.codepasser.common.service.AuthorizationService;
import org.codepasser.common.web.configuration.security.auth.UserIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

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
    if (!foundUser.isPresent()) {
      throw new UsernameNotFoundException(format("User [%s] not found.", username));
    }
    return new UserIdentity(foundUser.get());
  }
}
