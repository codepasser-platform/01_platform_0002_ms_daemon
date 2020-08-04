package org.codepasser.common.web.configuration.security.auth;

import org.codepasser.common.model.security.UserBasic;
import org.codepasser.common.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.lang.String.format;
import static org.codepasser.common.model.RegexPattern.REGEX_MAIL;
import static org.codepasser.common.model.RegexPattern.REGEX_PHONE;
import static org.codepasser.common.model.RegexPattern.REGEX_USER_NAME;

@Component
public class UserIdentityService implements UserDetailsService {

  @Autowired private AuthorizationService authorizationService;

  @Override
  public UserIdentity loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<UserBasic> foundUser = Optional.empty();
    if (username.matches(REGEX_MAIL)) {
      foundUser = authorizationService.loadUserByEmail(username);
    } else if (username.matches(REGEX_PHONE)) {
      foundUser = authorizationService.loadUserByPhone(username);
    } else if (username.matches(REGEX_USER_NAME)) {
      foundUser = authorizationService.loadUserByUsername(username);
    } else {
      foundUser = authorizationService.loadUserByUsername(username);
    }
    if (!foundUser.isPresent()) {
      throw new UsernameNotFoundException(format("User [%s] not found.", username));
    }
    return new UserIdentity(foundUser.get());
  }
}
